package ru.test.project.account.balance.service.client.service.factory;

import ru.test.project.account.balance.service.client.service.thread.AbstractTask;

/**
 * Task factory
 */
@FunctionalInterface
public interface TaskFactory {
    AbstractTask createTask();
}
