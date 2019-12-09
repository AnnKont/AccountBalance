package ru.test.project.account.balance.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.test.project.account.balance.service.client.service.ClientService;

@SpringBootApplication
public class ClientApplication implements CommandLineRunner {

    @Autowired
    private ClientService clientService;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    public void run(String... args) {
        clientService.start();
    }
}
