package ru.test.project.account.balance.service.server.controller;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.test.project.account.balance.service.server.BaseTest;
import ru.test.project.account.balance.service.server.constant.ControllerConstants;
import ru.test.project.account.balance.service.server.service.StatisticService;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class StatisticControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatisticService statisticService;

    @After
    public void tearDown() {
        verifyNoMoreInteractions(statisticService);
    }

    @Test
    public void testClearAllCount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/" + ControllerConstants.STATISTIC)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        then(statisticService).should().clearMaps();
    }
}
