package ru.test.project.account.balance.service.server.annotation;

import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.DirtiesContext;

/**
 * General annotation for all tests
 */
@PropertySource(value = "classpath:application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public @interface TestAnnotation {
}
