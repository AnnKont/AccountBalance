package ru.test.project.account.balance.service.server.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * General configuration
 */
@Configuration
@EnableCaching
@EnableScheduling
@PropertySource("classpath:application.properties")
public class AppConfig {
}
