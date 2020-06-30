package ru.test.project.account.balance.service.server.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import ru.test.project.account.balance.service.server.annotation.TestAnnotation;
import ru.test.project.account.balance.service.server.models.StatisticInfo;
import ru.test.project.account.balance.service.server.service.impl.StatisticServiceImpl;
import ru.test.project.account.balance.service.server.util.TestModelHelper;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SpringBootTest(classes = {StatisticServiceImpl.class})
@TestAnnotation
@RunWith(SpringRunner.class)
public class StatisticServiceTest {

    @Autowired
    private StatisticService statisticService;

    @MockBean
    @Qualifier("addRequestStatisticMapServiceImpl")
    private StatisticInfoService addRequestStatisticInfoService;
    @MockBean
    @Qualifier("getRequestStatisticMapServiceImpl")
    private StatisticInfoService getRequestStatisticInfoService;

    private StatisticInfo statisticInfo;

    @After
    public void tearDown() {
        verifyNoMoreInteractions(addRequestStatisticInfoService, getRequestStatisticInfoService);
    }

    @Before
    public void setUp() {
        statisticInfo = new StatisticInfo(TestModelHelper.getInt(), TestModelHelper.getInt(), TestModelHelper.getInt());
    }

    @Test
    public void testGetStatisticOfGetRequest() {
        given(getRequestStatisticInfoService.get()).willReturn(statisticInfo);

        StatisticInfo actualStatisticInfo = statisticService.getStatisticForGetRequest();
        Assert.assertEquals(statisticInfo, actualStatisticInfo);
        then(getRequestStatisticInfoService).should().get();
    }

    @Test
    public void testGetStatisticOfAddRequest() {
        given(addRequestStatisticInfoService.get()).willReturn(statisticInfo);

        StatisticInfo actualStatisticInfo = statisticService.getStatisticForAddRequest();
        Assert.assertEquals(statisticInfo, actualStatisticInfo);
        then(addRequestStatisticInfoService).should().get();
    }

    @Test
    public void testClear() {
        statisticService.clear();
        then(getRequestStatisticInfoService).should().clear();
        then(addRequestStatisticInfoService).should().clear();
    }

    @Test
    public void testClearIfNoRequests() {
        statisticService.clearIfNoRequests();
        then(getRequestStatisticInfoService).should().clearIfNoRequests();
        then(addRequestStatisticInfoService).should().clearIfNoRequests();
    }
}
