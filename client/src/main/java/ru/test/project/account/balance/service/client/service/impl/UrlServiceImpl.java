package ru.test.project.account.balance.service.client.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ru.test.project.account.balance.service.client.service.UrlService;

/**
 * Implementation of service {@link UrlService}
 */
@Service
public class UrlServiceImpl implements UrlService {

    private static final String SLASH = "/";

    /**
     * Url for server
     */
    private final String url;

    /**
     * Path for balance
     */
    private final String balancePath;

    /**
     * Path for statistic
     */
    private final String statisticPath;

    public UrlServiceImpl(@Value("${account.service.server.url}") String url,
            @Value("${account.service.server.path.balance}") String balancePath,
            @Value("${account.service.server.path.statistic}") String statisticPath) {
        this.url = url;
        this.balancePath = balancePath;
        this.statisticPath = statisticPath;
    }

    @Override
    @Cacheable("balanceUrl")
    public String getBalanceUrl(Integer id) {
        return url + balancePath + SLASH + id;
    }

    @Override
    @Cacheable("statisticClearUrl")
    public String getStatisticClearUrl() {
        return url + statisticPath;
    }
}
