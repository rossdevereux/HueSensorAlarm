package com.squasage.alarm;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.squasage.alarm.service.SensorService;

@Component
public class ScheduledTasks {

	private final SensorService sensorService;

	public ScheduledTasks(SensorService sensorService) {
		super();
		this.sensorService = sensorService;
	}

	@Scheduled(cron = "*/5 * * * * *")
	public void checkSensorStatus() {
		sensorService.printCurrentPresence();
	}

}
