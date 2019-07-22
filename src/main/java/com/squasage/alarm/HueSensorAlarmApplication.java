package com.squasage.alarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableJpaRepositories
@EnableScheduling
@SpringBootApplication
public class HueSensorAlarmApplication {

	public static void main(String[] args) {
		SpringApplication.run(HueSensorAlarmApplication.class, args);
	}

}
