package ru.test.project.account.balance.service.server;

import java.time.ZoneOffset;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

    static {
        System.setProperty("user.timezone", "UTC");
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC.getId()));
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
