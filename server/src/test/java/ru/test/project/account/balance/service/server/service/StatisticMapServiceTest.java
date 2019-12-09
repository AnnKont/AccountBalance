package ru.test.project.account.balance.service.server.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import ru.test.project.account.balance.service.server.annotation.TestAnnotation;
import ru.test.project.account.balance.service.server.service.impl.StatisticMapServiceImpl;
import ru.test.project.account.balance.service.server.util.TestModelHelper;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SpringBootTest(classes = {StatisticMapServiceImpl.class})
@TestAnnotation
@RunWith(SpringRunner.class)
public class StatisticMapServiceTest {

    private static final Long SECOND_MILLIS = 1000L;
    private final Instant NOW = Instant.now();
    private final long addAmountKey = TestModelHelper.getLongFromInstantRound(NOW, SECOND_MILLIS);
    private final long getAmountKey = TestModelHelper.getLongFromInstantRound(NOW.plusSeconds(1l), SECOND_MILLIS);
    private final long addAmountCount = TestModelHelper.getLong();
    private final long getAmountCount = TestModelHelper.getLong();
    @Autowired
    private StatisticMapService statisticMapService;
    @MockBean
    private TimeRoundService timeRoundService;
    private ConcurrentMap<Long, Long> getAmountMap;
    private ConcurrentMap<Long, Long> addAmountMap;

    @Before
    public void setUp() {
        getAmountMap = new ConcurrentHashMap<>();
        getAmountMap.put(getAmountKey, getAmountCount);
        addAmountMap = new ConcurrentHashMap<>();
        addAmountMap.put(addAmountKey, addAmountCount);
        ReflectionTestUtils.setField(statisticMapService, "getAmountMap", getAmountMap);
        ReflectionTestUtils.setField(statisticMapService, "addAmountMap", addAmountMap);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(timeRoundService);
    }

    @Test
    public void testGetMapOfGetAmount() {
        Map<Long, Long> actual = statisticMapService.getMapOfGetAmount();
        Assert.assertEquals(getAmountMap, actual);
    }

    @Test
    public void testGetMapOfAddAmount() {
        Map<Long, Long> actual = statisticMapService.getMapOfAddAmount();
        Assert.assertEquals(addAmountMap, actual);
    }

    @Test
    public void testAddToMapOfGetAmountInSameSecond() {
        given(timeRoundService.roundToSecond(any(Instant.class))).willReturn(getAmountKey);
        statisticMapService.incrementGetAmountCount();
        Map<Long, Long> actual = statisticMapService.getMapOfGetAmount();
        Assert.assertEquals(getAmountCount + 1, actual.get(getAmountKey).longValue());
        then(timeRoundService).should().roundToSecond(any(Instant.class));
    }

    @Test
    public void testAddToMapOfGetAmountInAnotherSecond() {
        given(timeRoundService.roundToSecond(any(Instant.class))).willReturn(getAmountKey + SECOND_MILLIS);
        statisticMapService.incrementGetAmountCount();
        Map<Long, Long> actual = statisticMapService.getMapOfGetAmount();
        Assert.assertEquals(getAmountCount, actual.get(getAmountKey).longValue());
        Assert.assertEquals(1, actual.get(getAmountKey + SECOND_MILLIS).longValue());
        then(timeRoundService).should().roundToSecond(any(Instant.class));
    }

    @Test
    public void testAddToMapOfAddAmountInSameSecond() {
        given(timeRoundService.roundToSecond(any(Instant.class))).willReturn(addAmountKey);
        statisticMapService.incrementAddAmountCount();
        Map<Long, Long> actual = statisticMapService.getMapOfAddAmount();
        Assert.assertEquals(addAmountCount + 1, actual.get(addAmountKey).longValue());
        then(timeRoundService).should().roundToSecond(any(Instant.class));
    }

    @Test
    public void testAddToMapOfAddAmountInAnotherSecond() {
        given(timeRoundService.roundToSecond(any(Instant.class))).willReturn(addAmountKey + SECOND_MILLIS);
        statisticMapService.incrementAddAmountCount();
        Map<Long, Long> actual = statisticMapService.getMapOfAddAmount();
        Assert.assertEquals(addAmountCount, actual.get(addAmountKey).longValue());
        Assert.assertEquals(1, actual.get(addAmountKey + SECOND_MILLIS).longValue());
        then(timeRoundService).should().roundToSecond(any(Instant.class));
    }

    @Test
    public void testClearMaps() {
        statisticMapService.clearMaps();
        Map<Long, Long> actualGetAmountMap = statisticMapService.getMapOfGetAmount();
        Assert.assertEquals(0, actualGetAmountMap.size());
        Map<Long, Long> actualAddAmountMap = statisticMapService.getMapOfAddAmount();
        Assert.assertEquals(0, actualAddAmountMap.size());
    }
}
