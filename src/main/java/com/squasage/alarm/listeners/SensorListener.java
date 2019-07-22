package com.squasage.alarm.listeners;

import java.util.List;
import java.util.Map;

import com.philips.lighting.hue.listener.PHSensorListener;
import com.philips.lighting.model.PHBridgeResource;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.sensor.PHSensor;

public class SensorListener implements PHSensorListener {

	@Override
	public void onError(int arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStateUpdate(Map<String, String> arg0, List<PHHueError> arg1) {
		// TODO Check state of alarm system and trigger if in correct state

	}

	@Override
	public void onSuccess() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReceivingSensorDetails(PHSensor arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorSearchFinished() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorsReceived(List<PHBridgeResource> arg0) {
		// TODO Auto-generated method stub

	}

}
