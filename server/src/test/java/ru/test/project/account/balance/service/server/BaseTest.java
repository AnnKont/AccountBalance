package ru.test.project.account.balance.service.server;

import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.test.project.account.balance.service.server.annotation.TestAnnotation;
import ru.test.project.account.balance.service.server.service.SchedulerService;

import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Base class for tests with all spring context
 */
@SpringBootTest
@TestAnnotation
@RunWith(SpringRunner.class)
public abstract class BaseTest {

    @MockBean
    private SchedulerService schedulerService;

    @After
    public void tearDown() {
        verifyNoMoreInteractions(schedulerService);
    }
}
