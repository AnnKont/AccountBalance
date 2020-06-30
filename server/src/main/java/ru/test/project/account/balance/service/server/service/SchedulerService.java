package ru.test.project.account.balance.service.server.service;

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ru.test.project.account.balance.service.server.models.StatisticInfo;
import ru.test.project.account.balance.service.server.utils.StatisticTextUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Scheduler service
 */
@Component
@Slf4j
public class SchedulerService {
    /**
     * Path to file for statistic
     */
    private final String filePath;

    private final StatisticService statisticService;

    public SchedulerService(StatisticService statisticService, @Value("${file.path}") String filePath) {
        this.statisticService = statisticService;
        this.filePath = filePath;
    }

    /**
     * Every 1000 mc save statistic to file
     */
    @Scheduled(initialDelay = 0, fixedRate = 50)
    public synchronized void saveStatistic() {
        StatisticInfo getRequestStatisticInfo = statisticService.getStatisticForGetRequest();
        StatisticInfo addRequestStatisticInfo = statisticService.getStatisticForAddRequest();

        try (FileWriter writer = new FileWriter(filePath, true)) {
            try (BufferedWriter bufferWriter = new BufferedWriter(writer)) {
                log.info("Write statistic to file.");
                bufferWriter.write(StatisticTextUtil.createText(getRequestStatisticInfo, addRequestStatisticInfo));
            }
        } catch (Exception e) {
            log.error("Error when try to write statistic to file: {}", e.getMessage());
        }
    }

    @Scheduled(fixedRate = 1000)
    public synchronized void clearStatisticIfNoRequests() {
        statisticService.clearIfNoRequests();
    }
}
