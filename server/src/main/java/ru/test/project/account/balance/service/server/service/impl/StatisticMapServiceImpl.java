package ru.test.project.account.balance.service.server.service.impl;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ru.test.project.account.balance.service.server.service.StatisticMapService;
import ru.test.project.account.balance.service.server.service.TimeRoundService;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of {@link StatisticMapService}
 */
@Service
@RequiredArgsConstructor
public class StatisticMapServiceImpl implements StatisticMapService {

    private final TimeRoundService timeRoundService;

    /**
     * Variable for synchronized
     */
    private final Lock lock = new ReentrantLock();

    /**
     * Map for count getAmount by seconds
     */
    private Map<Long, Long> getAmountMap = new HashMap<>();

    /**
     * Map for count addAmount by seconds
     */
    private Map<Long, Long> addAmountMap = new HashMap<>();

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
        getAmountMap = new HashMap<>();
        addAmountMap = new HashMap<>();
    }

    /**
     * Increment count requests in map
     *
     * @param currentMap - map
     */
    private void incrementCount(Map<Long, Long> currentMap) {
        Instant now = Instant.now();
        Long roundMillsToSecond = timeRoundService.roundToSecond(now);
        lock.lock();
        currentMap.put(roundMillsToSecond, currentMap.getOrDefault(roundMillsToSecond, 0L) + 1);
        lock.unlock();
    }
}
