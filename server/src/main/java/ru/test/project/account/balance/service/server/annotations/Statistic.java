package ru.test.project.account.balance.service.server.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation for statistic
 */
@Documented
@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface Statistic {
    /**
     * Type of response
     */
    ResponseType type();

    enum ResponseType {
        //Response type for add
        ADD,
        //Response type for get
        GET
    }
}