package ru.test.project.account.balance.service.client.service.thread;

import java.util.List;
import java.util.Random;

import ru.test.project.account.balance.service.client.service.AccountService;

import lombok.extern.slf4j.Slf4j;

/**
 * Task for add amount request
 */
@Slf4j
public class AddAmountTask extends AbstractTask {

    private final Random random;

    public AddAmountTask(AccountService accountService, List<Integer> ids) {
        super(accountService, ids);
        random = new Random();
    }

    @Override
    protected void doRequest() {
        log.info("Add amount request");
        ids.forEach(id -> accountService.addAmount(id, getRandomValue()));
    }

    /**
     * Get random long value
     *
     * @return random long value
     */
    private long getRandomValue() {
        return random.nextLong();
    }
}
