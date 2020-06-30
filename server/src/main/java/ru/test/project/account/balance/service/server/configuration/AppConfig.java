package ru.test.project.account.balance.service.server.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * General configuration
 */
@Configuration
@EnableCaching
@EnableScheduling
@EnableAspectJAutoProxy
public class AppConfig {
}
