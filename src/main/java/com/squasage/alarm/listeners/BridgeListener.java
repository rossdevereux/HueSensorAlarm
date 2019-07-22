package com.squasage.alarm.listeners;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueParsingError;
import com.squasage.alarm.model.EventType;
import com.squasage.alarm.service.ConfigurationService;
import com.squasage.alarm.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BridgeListener implements PHSDKListener {

    private static final Logger LOG = LoggerFactory.getLogger(BridgeListener.class);

    private final PHHueSDK phHueSDK;
    private final ConfigurationService configurationService;
    private final EventService eventService;

    public BridgeListener(PHHueSDK phHueSDK, ConfigurationService configurationService, EventService eventService) {
        this.phHueSDK = phHueSDK;
        this.configurationService = configurationService;
        this.eventService = eventService;
    }

    @Override
    public void onAccessPointsFound(List<PHAccessPoint> accessPoints) {
        LOG.info("Access points found: {}", accessPoints);
        // TODO List and let user choose
        if (!accessPoints.isEmpty()) {
            phHueSDK.connect(accessPoints.get(0));
        }
    }

    @Override
    public void onAuthenticationRequired(PHAccessPoint accessPoint) {
        LOG.warn("Authentication required, go push the button!");
        phHueSDK.startPushlinkAuthentication(accessPoint);
        eventService.publishEvent(EventType.BRIDGE_CONNECTION_ERROR, "hub", "Authentication required, push link button");
    }

    @Override
    public void onBridgeConnected(PHBridge bridge, String username) {
        LOG.info("Bridge connected with username {}: {}", username, bridge);

        eventService.publishEvent(EventType.BRIDGE_CONNECTED);

        // TODO Store username and bridge info to reconnect
        configurationService.storeConnectedBridge(bridge.getResourceCache()
                .getBridgeConfiguration()
                .getIpAddress(), username);

        phHueSDK.setSelectedBridge(bridge);
        phHueSDK.enableHeartbeat(bridge, PHHueSDK.HB_INTERVAL);
        phHueSDK.getLastHeartbeat()
                .put(bridge.getResourceCache()
                        .getBridgeConfiguration()
                        .getIpAddress(), System.currentTimeMillis());
    }

    @Override
    public void onCacheUpdated(List<Integer> ids, PHBridge bridge) {
        LOG.info("On CacheUpdated {} {}", ids, bridge);
    }

    @Override
    public void onConnectionLost(PHAccessPoint accessPoint) {
        LOG.error("Connection Lost: {}", accessPoint);
        if (!phHueSDK.getDisconnectedAccessPoint()
                .contains(accessPoint)) {
            phHueSDK.getDisconnectedAccessPoint()
                    .add(accessPoint);
        }
    }

    @Override
    public void onConnectionResumed(PHBridge bridge) {
        LOG.info("Connection Resumed: {}", bridge);

        String ipAddress = bridge.getResourceCache()
                .getBridgeConfiguration()
                .getIpAddress();
        phHueSDK.getLastHeartbeat()
                .put(ipAddress, System.currentTimeMillis());

        phHueSDK.getDisconnectedAccessPoint()
                .stream()
                .filter((a) -> a.getIpAddress()
                        .equals(ipAddress))
                .forEach((a) -> phHueSDK.getDisconnectedAccessPoint()
                        .remove(a));
    }

    @Override
    public void onError(int code, final String message) {
        if (code == PHMessageType.PUSHLINK_BUTTON_NOT_PRESSED) {
            LOG.warn("Push the link button to connect hub");
        } else {
            LOG.error("An Error has occurred with code {} and message {}", code, message);
            eventService.publishEvent(EventType.BRIDGE_CONNECTION_ERROR, null, message);
        }
    }

    @Override
    public void onParsingErrors(List<PHHueParsingError> errors) {
        errors.stream()
                .forEach((error) -> LOG.error("Parsing Error: {}", error.getMessage()));
    }

}
