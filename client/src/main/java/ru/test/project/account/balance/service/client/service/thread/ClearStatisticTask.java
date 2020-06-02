package ru.test.project.account.balance.service.client.service.thread;

import ru.test.project.account.balance.service.client.service.StatisticService;

import lombok.extern.slf4j.Slf4j;

/**
 * Task for clear statistic
 */
@Slf4j
public class ClearStatisticTask extends ThreadTask {

    private final StatisticService statisticService;

    public ClearStatisticTask(StatisticService statisticService) {
        super();
        this.statisticService = statisticService;
    }

    @Override
    protected void doRequest() {
        log.info("Clear statistic request");
        statisticService.clearMaps();
    }
}
