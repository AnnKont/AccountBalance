package ru.test.project.account.balance.service.client.service;

/**
 * Service for http requests
 *
 * @param <T> - class for get request
 * @param <D> - class for put request
 */
public interface HttpService<T, D> {
    /**
     * Do get http request by given url and return object {@link T}
     *
     * @param url    - given url
     * @param tClass - class of return object
     * @return object {@link T}
     */
    T get(String url, Class<T> tClass);

    /**
     * Do put http request by given url and object {@link D}
     *
     * @param url - given url
     * @param d   - object {@link D}
     */
    void put(String url, D d);

    /**
     * Do delete http request by given url
     *
     * @param url - given url
     */
    void delete(String url);
}
