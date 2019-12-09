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
import ru.test.project.account.balance.service.server.annotation.TestAnnotation;
import ru.test.project.account.balance.service.server.service.impl.StatisticServiceImpl;
import ru.test.project.account.balance.service.server.util.TestModelHelper;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SpringBootTest(classes = {StatisticServiceImpl.class})
@TestAnnotation
@RunWith(SpringRunner.class)
public class StatisticServiceTest {

    @Autowired
    private StatisticService statisticService;

    @MockBean
    private TimeRoundService timeRoundService;

    @MockBean
    private StatisticMapService statisticMapService;
    private ConcurrentMap<Long, Long> map;

    @After
    public void tearDown() {
        verifyNoMoreInteractions(timeRoundService, statisticMapService);
    }

    @Before
    public void setUp() {
        map = new ConcurrentHashMap<>();
        map.put(TestModelHelper.getLong(), TestModelHelper.getLong());
        map.put(TestModelHelper.getLong(), TestModelHelper.getLong());
        map.put(TestModelHelper.getLong(), TestModelHelper.getLong());
    }

    @Test
    public void testGetCountOfGetAmountBySecond() {
        given(statisticMapService.getMapOfGetAmount()).willReturn(map);
        Map<Instant, Long> expectedMap = getStatisticMap(map);

        Map<Instant, Long> actualMap = statisticService.getCountOfGetAmountBySecond();
        Assert.assertEquals(expectedMap, actualMap);
        then(statisticMapService).should().getMapOfGetAmount();
    }

    @Test
    public void testGetCountOfAddAmountBySecond() {
        given(statisticMapService.getMapOfAddAmount()).willReturn(map);
        Map<Instant, Long> expectedMap = getStatisticMap(map);

        Map<Instant, Long> actualMap = statisticService.getCountOfAddAmountBySecond();
        Assert.assertEquals(expectedMap, actualMap);
        then(statisticMapService).should().getMapOfAddAmount();
    }

    @Test
    public void testIncrementGetAmountCount() {
        statisticService.incrementGetAmountCount();
        then(statisticMapService).should().incrementGetAmountCount();
    }

    @Test
    public void testIncrementAddAmountCount() {
        statisticService.incrementAddAmountCount();
        then(statisticMapService).should().incrementAddAmountCount();
    }

    @Test
    public void testGetAllCountOfGetAmount() {
        given(statisticMapService.getMapOfGetAmount()).willReturn(map);
        Long expectedCount = map.values().stream().mapToLong(Long::longValue).sum();

        Long actualCount = statisticService.getAllCountOfGetAmount();
        Assert.assertEquals(expectedCount, actualCount);
        then(statisticMapService).should().getMapOfGetAmount();
    }

    @Test
    public void testGetAllCountOfAddAmount() {
        given(statisticMapService.getMapOfAddAmount()).willReturn(map);
        Long expectedCount = map.values().stream().mapToLong(Long::longValue).sum();

        Long actualCount = statisticService.getAllCountOfAddAmount();
        Assert.assertEquals(expectedCount, actualCount);
        then(statisticMapService).should().getMapOfAddAmount();
    }

    @Test
    public void testClearMaps() {
        statisticService.clearMaps();
        then(statisticMapService).should().clearMaps();
    }

    /**
     * Get statistic map with Instant key and sorted by key
     *
     * @param map - statistic map with Long key
     * @return statistic map with Instant key and sorted by key
     */
    private Map<Instant, Long> getStatisticMap(Map<Long, Long> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap((longEntry) -> Instant.ofEpochMilli(longEntry.getKey()),
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
