package com.squasage.alarm.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.philips.lighting.model.sensor.PHPresenceSensor;
import com.philips.lighting.model.sensor.PHSensor;

@Service
public class SensorService {

	private static final Logger LOG = LoggerFactory.getLogger(SensorService.class);

	private final BridgeService bridgeService;
	private final ConfigurationService configurationService;

	public SensorService(BridgeService bridgeService, ConfigurationService configurationService) {
		super();
		this.bridgeService = bridgeService;
		this.configurationService = configurationService;
	}

	private List<PHSensor> getSensors() {
		return bridgeService.getBridgeResourceCache()
				.getAllSensors();
	}

	public Map<PHPresenceSensor, Boolean> getSensorStatus() {
		return getSensors().stream()
				.filter((s) -> s instanceof PHPresenceSensor)
				.collect(Collectors.toMap((s) -> ((PHPresenceSensor) s), (s) -> ((PHPresenceSensor) s).getState().getPresence()));
	}

	public void printCurrentPresence() {
		if (bridgeService.isConnected()) {
			Map<PHPresenceSensor, Boolean> sensorStatus = getSensorStatus();
			sensorStatus.forEach((sensor, presence) -> {
				if (presence)
				{
					LOG.info("Presence detected in areas: {}", sensor.getName());
					sensor.getState().setPresence(false);
				}
			});
		}
	}

}
