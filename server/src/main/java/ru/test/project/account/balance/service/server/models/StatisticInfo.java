package ru.test.project.account.balance.service.server.models;

import lombok.Value;

@Value
public class StatisticInfo {

    int countInSecond;

    int countInMinute;

    int countInHour;
}