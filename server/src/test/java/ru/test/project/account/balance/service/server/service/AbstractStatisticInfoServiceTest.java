package ru.test.project.account.balance.service.server.service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import ru.test.project.account.balance.service.server.annotation.TestAnnotation;
import ru.test.project.account.balance.service.server.models.StatisticInfo;


@TestAnnotation
@RunWith(PowerMockRunner.class)
@PrepareForTest(System.class)
@PowerMockRunnerDelegate(SpringRunner.class)
public abstract class AbstractStatisticInfoServiceTest {

    private static final long CURRENT_TIME = System.currentTimeMillis();
    private static final int MILLI = 1000;
    private static final int SECOND = 60;

    @Autowired
    protected StatisticInfoService statisticInfoService;

    @Before
    public void setUp() {
        long currentTime = System.currentTimeMillis();
        ReflectionTestUtils.setField(statisticInfoService, "currentSecond", new AtomicLong(currentTime));
        ReflectionTestUtils.setField(statisticInfoService, "currentMinute", new AtomicLong(currentTime / MILLI));
        ReflectionTestUtils.setField(statisticInfoService, "currentHour", new AtomicLong(currentTime / SECOND));
        ReflectionTestUtils.setField(statisticInfoService, "secondCount", new AtomicInteger());
        ReflectionTestUtils.setField(statisticInfoService, "minuteCount", new AtomicInteger());
        ReflectionTestUtils.setField(statisticInfoService, "hourCount", new AtomicInteger());
    }

    @Test
    public void testGetEmptyStatistic() {
        StatisticInfo statisticInfo = statisticInfoService.get();
        Assert.assertEquals(0, statisticInfo.getCountInSecond());
        Assert.assertEquals(0, statisticInfo.getCountInMinute());
        Assert.assertEquals(0, statisticInfo.getCountInHour());
    }

    @Test
    public void testGetStatisticBySameSecond() {
        PowerMockito.mockStatic(System.class);
        PowerMockito.when(System.currentTimeMillis()).thenReturn(CURRENT_TIME);

        statisticInfoService.increment();
        StatisticInfo statisticInfo = statisticInfoService.get();
        Assert.assertEquals(1, statisticInfo.getCountInSecond());
        Assert.assertEquals(1, statisticInfo.getCountInMinute());
        Assert.assertEquals(1, statisticInfo.getCountInHour());
    }

    @Test
    public void testGetStatisticBySameMinute() {
        statisticInfoService.increment();

        PowerMockito.mockStatic(System.class);
        PowerMockito.when(System.currentTimeMillis()).thenReturn(CURRENT_TIME + MILLI + 1);

        statisticInfoService.increment();

        StatisticInfo statisticInfo = statisticInfoService.get();
        Assert.assertEquals(2, statisticInfo.getCountInMinute());
        Assert.assertEquals(2, statisticInfo.getCountInHour());
    }

    @Test
    public void testGetStatisticBySameHour() {
        statisticInfoService.increment();

        PowerMockito.mockStatic(System.class);
        PowerMockito.when(System.currentTimeMillis()).thenReturn(CURRENT_TIME + MILLI * (SECOND + 1));

        statisticInfoService.increment();

        StatisticInfo statisticInfo = statisticInfoService.get();
        Assert.assertEquals(2, statisticInfo.getCountInHour());
    }
}
