package ru.test.project.account.balance.service.server.service.impl;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import ru.test.project.account.balance.service.server.models.StatisticInfo;
import ru.test.project.account.balance.service.server.service.StatisticInfoService;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link StatisticInfoService}
 */
@Slf4j
public abstract class AbstractStatisticInfoServiceImpl implements StatisticInfoService {

    private static final int MILLI = 1000;
    private static final int SECOND = 60;
    private static final int HOUR = 24;
    private AtomicLong currentSecond;
    private AtomicLong currentMinute;
    private AtomicLong currentHour;
    private AtomicInteger secondCount;
    private AtomicInteger minuteCount;
    private AtomicInteger hourCount;

    @PostConstruct
    public void init() {
        clear();
    }

    @Override
    public StatisticInfo get() {
        return new StatisticInfo(secondCount.get(), minuteCount.get(), hourCount.get());
    }

    @Override
    public void increment() {
        updateFrequency();
    }

    @Override
    public void clear() {
        this.currentSecond = new AtomicLong(System.currentTimeMillis());
        this.currentMinute = new AtomicLong(currentSecond.longValue() / MILLI);
        this.currentHour = new AtomicLong(currentMinute.longValue() / SECOND);

        this.secondCount = new AtomicInteger();
        this.minuteCount = new AtomicInteger();
        this.hourCount = new AtomicInteger();
    }

    private void updateFrequency() {
        long newSecond = System.currentTimeMillis();
        long newMinute = newSecond / MILLI;
        long newHour = newMinute / SECOND;

        currentSecond.getAndUpdate(prev -> {
            if (isNewSecond(prev, newSecond)) {
                log.debug("Second count is updated");
                secondCount.set(0);
                return newSecond;
            } else {
                secondCount.incrementAndGet();
                return prev;
            }
        });

        currentMinute.getAndUpdate(prev -> {
            if (isNewMinute(prev, newMinute)) {
                log.debug("Minute count is updated");
                minuteCount.set(0);
                return newMinute;
            } else {
                minuteCount.incrementAndGet();
                return prev;
            }
        });

        currentHour.getAndUpdate(prev -> {
            if (isNewHour(prev, newHour)) {
                log.debug("Hour count is updated");
                hourCount.set(0);
                return newHour;
            } else {
                hourCount.incrementAndGet();
                return prev;
            }
        });
    }

    public void clearIfNoRequests() {
        log.debug("Clear");
        long newSecond = System.currentTimeMillis();
        long newMinute = newSecond / MILLI;
        long newHour = newMinute / SECOND;

        currentSecond.getAndUpdate(prev -> {
            if (isNewSecond(prev, newSecond)) {
                log.debug("Second count is updated");
                secondCount.set(0);
                return newSecond;
            } else {
                return prev;
            }
        });

        currentMinute.getAndUpdate(prev -> {
            if (isNewMinute(prev, newMinute)) {
                log.debug("Minute count is updated");
                minuteCount.set(0);
                return newMinute;
            } else {
                return prev;
            }
        });

        currentHour.getAndUpdate(prev -> {
            if (isNewHour(prev, newHour)) {
                log.debug("Hour count is updated");
                hourCount.set(0);
                return newHour;
            } else {
                return prev;
            }
        });

    }

    private boolean isNewSecond(long prevTime, long newTime) {
        return prevTime + MILLI < newTime;
    }

    private boolean isNewMinute(long prevTime, long newTime) {
        return prevTime + SECOND < newTime;
    }

    private boolean isNewHour(long prevTime, long newTime) {
        return prevTime + HOUR < newTime;
    }
}
