package ru.test.project.account.balance.service.client.service.thread;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * General class for tasks with thread
 */
@Slf4j
public abstract class ThreadTask implements Runnable {

    @Getter
    protected final Thread thread;

    public ThreadTask() {
        thread = new Thread(this);
        log.info("Task: Create new additional thread : " + thread);
    }

    @Override
    public final void run() {
        doRequest();
    }

    /**
     * Method that run in thread
     */
    protected abstract void doRequest();
}
