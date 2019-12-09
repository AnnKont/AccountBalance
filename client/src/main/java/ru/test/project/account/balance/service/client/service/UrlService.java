package ru.test.project.account.balance.service.client.service;

/**
 * Service for create urls
 */
public interface UrlService {
    /**
     * Create url for balance(getAmount/addAmount methods) by given id
     *
     * @param id - given id
     * @return url for balance
     */
    String getBalanceUrl(Integer id);

    /**
     * Create url for clear statistic
     *
     * @return url for clear statistic
     */
    String getStatisticClearUrl();
}
