package ru.test.project.account.balance.service.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.test.project.account.balance.service.server.service.StatisticMapService;
import ru.test.project.account.balance.service.server.service.TimeRoundService;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Implementation of {@link StatisticMapService}
 */
@Service
public class StatisticMapServiceImpl implements StatisticMapService {

    /**
     * Variable for synchronized
     */
    private final Boolean LOCK = true;
    @Autowired
    private TimeRoundService timeRoundService;
    /**
     * Map for count getAmount by seconds
     */
    private ConcurrentMap<Long, Long> getAmountMap = new ConcurrentHashMap<>();
    /**
     * Map for count addAmount by seconds
     */
    private ConcurrentMap<Long, Long> addAmountMap = new ConcurrentHashMap<>();

    @Override
    @Cacheable(cacheNames = "getAmountMap")
    public Map<Long, Long> getMapOfGetAmount() {
        return getAmountMap;
    }

    @Override
    @Cacheable(cacheNames = "addAmountMap")
    public Map<Long, Long> getMapOfAddAmount() {
        return addAmountMap;
    }

    @Override
    @CacheEvict(cacheNames = "getAmountMap")
    public void incrementGetAmountCount() {
        incrementCount(getAmountMap);
    }

    @Override
    @CacheEvict(cacheNames = "addAmountMap")
    public void incrementAddAmountCount() {
        incrementCount(addAmountMap);
    }

    @Override
    @CacheEvict(cacheNames = {"addAmountMap", "getAmountMap"})
    public void clearMaps() {
        getAmountMap = new ConcurrentHashMap<>();
        addAmountMap = new ConcurrentHashMap<>();
    }

    /**
     * Increment count requests in map
     *
     * @param currentMap - map
     */
    private void incrementCount(ConcurrentMap<Long, Long> currentMap) {
        Instant now = Instant.now();
        Long roundMillsToSecond = timeRoundService.roundToSecond(now);
        synchronized (LOCK) {
            currentMap.put(roundMillsToSecond, currentMap.getOrDefault(roundMillsToSecond, 0l) + 1);
        }
    }
}
