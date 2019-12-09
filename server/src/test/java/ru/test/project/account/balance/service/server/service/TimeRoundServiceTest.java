package ru.test.project.account.balance.service.server.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.test.project.account.balance.service.server.annotation.TestAnnotation;
import ru.test.project.account.balance.service.server.service.impl.TimeRoundServiceImpl;

import java.time.Instant;

@SpringBootTest(classes = {TimeRoundServiceImpl.class})
@TestAnnotation
@RunWith(SpringRunner.class)
public class TimeRoundServiceTest {

    private static final Long SECOND_MILLIS = 1000L;
    @Autowired
    private TimeRoundService timeRoundService;

    @Test
    public void testRoundToSecond() {
        Instant now = Instant.now();
        long actual = timeRoundService.roundToSecond(now);
        Assert.assertEquals((now.toEpochMilli() / SECOND_MILLIS) * SECOND_MILLIS, actual);
    }
}
