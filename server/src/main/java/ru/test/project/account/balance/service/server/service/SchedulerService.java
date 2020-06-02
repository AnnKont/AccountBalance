package ru.test.project.account.balance.service.server.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.Instant;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Scheduler service
 */
@Component
@Slf4j
public class SchedulerService {
    /**
     * Name of get amount request
     */
    private static final String NAME_OF_GET_AMOUNT_REQUEST = "Get Amount";
    /**
     * Name of add amount request
     */
    private static final String NAME_OF_ADD_AMOUNT_REQUEST = "Add Amount";
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
    @Scheduled(fixedRate = 1000)
    public synchronized void saveStatistic() {
        Map<Instant, Long> getAmountMap = statisticService.getCountOfGetAmountBySecond();
        Map<Instant, Long> addAmountMap = statisticService.getCountOfAddAmountBySecond();
        if (getAmountMap.size() <= 0 && addAmountMap.size() <= 0) {
            return;
        }
        try (FileWriter writer = new FileWriter(filePath, true)) {
            try (BufferedWriter bufferWriter = new BufferedWriter(writer)) {
                log.info("Write statistic to file.");

                StringBuilder stringBuilder = new StringBuilder("\nStatistic: ").append(Instant.now()).append("\n");
                addMapToStringBuilder(stringBuilder, getAmountMap, NAME_OF_GET_AMOUNT_REQUEST);
                addMapToStringBuilder(stringBuilder, addAmountMap, NAME_OF_ADD_AMOUNT_REQUEST);
                bufferWriter.write(stringBuilder.toString());
            }
        } catch (Exception e) {
            log.error("Error when try to write statistic to file: {}", e.getMessage());
        }
    }

    /**
     * Add map data to string builder
     *
     * @param stringBuilder - current string builder
     * @param map           - map with data
     * @param nameOfRequest - name of map
     */
    private void addMapToStringBuilder(StringBuilder stringBuilder, Map<Instant, Long> map, String nameOfRequest) {
        if (map.size() > 0) {
            Long count = getCountFromMap(map);
            stringBuilder.append(nameOfRequest)
                    .append(" total: ")
                    .append(count)
                    .append("\n")
                    .append(nameOfRequest)
                    .append(" by seconds: \n");
            for (Map.Entry<Instant, Long> entry : map.entrySet()) {
                stringBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            stringBuilder.append("\n");
        }
    }

    /**
     * Get count of requests from map
     *
     * @param map - map of requests
     * @return count of requests
     */
    private Long getCountFromMap(Map<Instant, Long> map) {
        return map.values().stream().mapToLong(Long::longValue).sum();
    }
}
