package ru.test.project.account.balance.service.client.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.client.RestTemplate;

/**
 * General configuration
 */
@Configuration
@EnableCaching
@PropertySources(value = {@PropertySource(ignoreResourceNotFound = true, value = "file:${configPath}"),
        @PropertySource("classpath:application.properties")})
public class AppConfig {

    @Bean
    public RestTemplate accountBalanceService() {
        return new RestTemplate();
    }
}
