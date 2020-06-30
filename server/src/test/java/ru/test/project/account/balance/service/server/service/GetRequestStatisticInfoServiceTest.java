package ru.test.project.account.balance.service.server.service;

import org.springframework.boot.test.context.SpringBootTest;

import ru.test.project.account.balance.service.server.service.impl.GetRequestStatisticInfoServiceImpl;

@SpringBootTest(classes = {GetRequestStatisticInfoServiceImpl.class})
public class GetRequestStatisticInfoServiceTest extends AbstractStatisticInfoServiceTest {
}