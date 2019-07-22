package com.squasage.alarm.service;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.squasage.alarm.listeners.BridgeListener;

@Service
public class BridgeService {

	private static final Logger LOG = LoggerFactory.getLogger(BridgeService.class);

	private final PHHueSDK phHueSDK;
	private final PHSDKListener listener;
	private final ConfigurationService configurationService;
	private final EventService eventService;

	public BridgeService(ConfigurationService configurationService, EventService eventService) {
		this.phHueSDK = PHHueSDK.getInstance();
		this.configurationService = configurationService;
		this.eventService = eventService;
		this.listener = new BridgeListener(phHueSDK, configurationService, eventService);

		// Register the PHSDKListener to receive callbacks from the bridge.
		phHueSDK.getNotificationManager()
				.registerSDKListener(listener);

		if (!connectToLastKnownAccessPoint())
			doBridgeSearch();
	}

	public void doBridgeSearch() {
		PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
		sm.search(true, true);
	}

	public boolean connectToLastKnownAccessPoint() {
		String username = configurationService.getConfig()
				.getUsername();
		String lastIpAddress = configurationService.getConfig()
				.getLastConnectedIp();

		if (username == null || lastIpAddress == null) {
			LOG.info("No previous bridge stored to connect to");
			return false;
		}

		PHAccessPoint accessPoint = new PHAccessPoint();
		accessPoint.setIpAddress(lastIpAddress);
		accessPoint.setUsername(username);
		phHueSDK.connect(accessPoint);

		return true;
	}

	public PHBridgeResourcesCache getBridgeResourceCache() {
		return phHueSDK.getSelectedBridge()
				.getResourceCache();
	}

	@PreDestroy
	public void onDestroy() {
		if (listener != null) {
			phHueSDK.getNotificationManager()
					.unregisterSDKListener(listener);
		}
		phHueSDK.disableAllHeartbeat();
	}

	public PHHueSDK getPhHueSDK() {
		return phHueSDK;
	}

	public PHSDKListener getListener() {
		return listener;
	}

	public boolean isConnected() {
		return phHueSDK.getSelectedBridge() != null;
	}

}
