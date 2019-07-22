package com.squasage.alarm.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.squasage.alarm.model.SystemConfiguration;

@Repository
public interface SystemConfigurationRepository extends CrudRepository<SystemConfiguration, Long> {
	
	public Optional<SystemConfiguration> findOneById(Long id);

}
