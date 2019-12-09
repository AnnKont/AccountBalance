package ru.test.project.account.balance.service.client.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.test.project.account.balance.service.client.service.HttpService;

/**
 * Implementation of service {@link HttpService}
 */
@Slf4j
@Service
public class HttpServiceImpl<T, D> implements HttpService<T, D> {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public T get(String url, Class<T> tClass) {
        try {
            return restTemplate.getForEntity(url, tClass).getBody();
        } catch (RestClientException e) {
            log.error("Error when do get request: {}, for url: {}", e.getMessage(), url);
            return null;
        }
    }

    @Override
    public void put(String url, D d) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<D> requestEntity = new HttpEntity<>(d, headers);
        try {
            restTemplate.put(url, requestEntity);
        } catch (RestClientException e) {
            log.error("Error when do put request: {}, for url: {}", e.getMessage(), url);
        }
    }

    @Override
    public void delete(String url) {
        try {
            restTemplate.delete(url);
        } catch (RestClientException e) {
            log.error("Error when do delete request: {}, for url: {}", e.getMessage(), url);
        }
    }
}
