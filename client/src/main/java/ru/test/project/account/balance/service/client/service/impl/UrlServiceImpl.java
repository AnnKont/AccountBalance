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

    private final String SLASH = "/";

    /**
     * Url for server
     */
    @Value("${account.service.server.url}")
    private String url;

    /**
     * Path for balance
     */
    @Value("${account.service.server.path.balance}")
    private String balancePath;

    /**
     * Path for statistic
     */
    @Value("${account.service.server.path.statistic}")
    private String statisticPath;

    @Override
    @Cacheable("balanceUrl")
    public String getBalanceUrl(Integer id) {
        StringBuilder stringBuilder = new StringBuilder(url).append(balancePath).append(SLASH).append(id);
        return stringBuilder.toString();
    }

    @Override
    @Cacheable("statisticClearUrl")
    public String getStatisticClearUrl() {
        StringBuilder stringBuilder = new StringBuilder(url).append(statisticPath);
        return stringBuilder.toString();
    }
}
