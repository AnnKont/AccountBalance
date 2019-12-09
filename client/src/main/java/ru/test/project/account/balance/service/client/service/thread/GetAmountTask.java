package ru.test.project.account.balance.service.client.service.thread;

import lombok.extern.slf4j.Slf4j;
import ru.test.project.account.balance.service.client.service.AccountService;

import java.util.List;

/**
 * Task for get amount request
 */
@Slf4j
public class GetAmountTask extends AbstractTask {
    public GetAmountTask(AccountService accountService, List<Integer> ids) {
        super(accountService, ids);
    }

    @Override
    protected void doRequest() {
        log.info("Get amount request");
        ids.forEach(id -> {
            Long amount = accountService.getAmount(id);
            log.info("Amount for id: {} is: {}", id, amount);
        });
    }
}
