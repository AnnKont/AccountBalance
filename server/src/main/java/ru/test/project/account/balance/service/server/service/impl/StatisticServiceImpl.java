package ru.test.project.account.balance.service.server.service.impl;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ru.test.project.account.balance.service.server.service.StatisticMapService;
import ru.test.project.account.balance.service.server.service.StatisticService;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of {@link StatisticService}
 */
@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final StatisticMapService statisticMapService;

    @Override
    public Map<Instant, Long> getCountOfGetAmountBySecond() {
        return getStatisticMap(statisticMapService.getMapOfGetAmount());
    }

    @Override
    public Long getAllCountOfGetAmount() {
        return statisticMapService.getMapOfGetAmount().values().stream().mapToLong(Long::longValue).sum();
    }

    @Override
    public Map<Instant, Long> getCountOfAddAmountBySecond() {
        return getStatisticMap(statisticMapService.getMapOfAddAmount());
    }

    @Override
    public Long getAllCountOfAddAmount() {
        return statisticMapService.getMapOfAddAmount().values().stream().mapToLong(Long::longValue).sum();
    }

    @Override
    public void incrementGetAmountCount() {
        statisticMapService.incrementGetAmountCount();
    }

    @Override
    public void incrementAddAmountCount() {
        statisticMapService.incrementAddAmountCount();
    }

    @Override
    public void clearMaps() {
        statisticMapService.clearMaps();
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
