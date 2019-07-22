package com.squasage.alarm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.squasage.alarm.model.SystemConfiguration;
import com.squasage.alarm.repository.SystemConfigurationRepository;

@Service
public class ConfigurationService {

	private static final Logger LOG = LoggerFactory.getLogger(ConfigurationService.class);

	private final SystemConfiguration config;
	private final SystemConfigurationRepository systemConfigurationRepository;

	public ConfigurationService(SystemConfigurationRepository systemConfigurationRepository) {
		super();
		this.systemConfigurationRepository = systemConfigurationRepository;
		this.config = systemConfigurationRepository.findOneById(1l).orElse(SystemConfiguration.newInstance());
	}

	public void storeConnectedBridge(String ipAddress, String username) {
		this.config.setUsername(username);
		this.config.setLastConnectedIp(ipAddress);

		LOG.debug("Storing username and bridge ip for next connect: {}, {}", username, ipAddress);

		systemConfigurationRepository.save(this.config);
	}

	public SystemConfiguration getConfig() {
		return config;
	}

}
