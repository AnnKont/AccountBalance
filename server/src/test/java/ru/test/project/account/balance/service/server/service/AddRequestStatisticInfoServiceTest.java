package ru.test.project.account.balance.service.server.service;

import org.springframework.boot.test.context.SpringBootTest;

import ru.test.project.account.balance.service.server.service.impl.AddRequestStatisticInfoServiceImpl;

@SpringBootTest(classes = {AddRequestStatisticInfoServiceImpl.class})
public class AddRequestStatisticInfoServiceTest extends AbstractStatisticInfoServiceTest {
}