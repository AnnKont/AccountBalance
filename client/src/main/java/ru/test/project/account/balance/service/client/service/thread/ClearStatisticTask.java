package ru.test.project.account.balance.service.client.service.thread;

import lombok.extern.slf4j.Slf4j;
import ru.test.project.account.balance.service.client.service.StatisticService;

/**
 * Task for clear statistic
 */
@Slf4j
public class ClearStatisticTask extends ThreadTask {

    private StatisticService statisticService;

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
