package ru.test.project.account.balance.service.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.test.project.account.balance.service.server.BaseTest;
import ru.test.project.account.balance.service.server.constant.ControllerConstants;
import ru.test.project.account.balance.service.server.dto.AmountDto;
import ru.test.project.account.balance.service.server.error.AmountFitLongException;
import ru.test.project.account.balance.service.server.error.BadAmountException;
import ru.test.project.account.balance.service.server.error.BadIdException;
import ru.test.project.account.balance.service.server.error.ItemNotFoundException;
import ru.test.project.account.balance.service.server.service.AccountBalanceService;
import ru.test.project.account.balance.service.server.service.StatisticService;
import ru.test.project.account.balance.service.server.service.ValidationService;
import ru.test.project.account.balance.service.server.util.TestModelHelper;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class AccountBalanceControllerTest extends BaseTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountBalanceService accountBalanceService;
    @MockBean
    private ValidationService validationService;
    @MockBean
    private StatisticService statisticService;

    @After
    public void tearDown() {
        verifyNoMoreInteractions(accountBalanceService, validationService, statisticService);
    }

    @Test
    public void testGetAmountOk() throws Exception {
        Integer id = TestModelHelper.getInt();
        Long expectedAmount = TestModelHelper.getLong();

        given(accountBalanceService.getAmount(id)).willReturn(expectedAmount);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/" + ControllerConstants.BALANCE + "/" + id)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Long actualAmount = objectMapper.readValue(contentAsString, Long.class);

        Assert.assertEquals(expectedAmount, actualAmount);

        then(statisticService).should().incrementGetAmountCount();
        then(validationService).should().validId(id);
        then(accountBalanceService).should().getAmount(id);
    }

    @Test
    public void testGetAmountBadId() throws Exception {
        Integer id = TestModelHelper.getInt();

        BadIdException error = new BadIdException(id);
        willThrow(error).given(validationService).validId(id);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/" + ControllerConstants.BALANCE + "/" + id)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        TestModelHelper.AwesomeException awesomeException = objectMapper.readValue(contentAsString, TestModelHelper.AwesomeException.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), awesomeException.getStatus());
        Assert.assertEquals(error.getMessage(), awesomeException.getMessage());
        then(statisticService).should().incrementGetAmountCount();
        then(validationService).should().validId(id);
    }

    @Test
    public void testGetAmountItemNotFound() throws Exception {
        Integer id = TestModelHelper.getInt();
        ItemNotFoundException error = new ItemNotFoundException(id);
        given(accountBalanceService.getAmount(id)).willThrow(error);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/" + ControllerConstants.BALANCE + "/" + id)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        TestModelHelper.AwesomeException awesomeException = objectMapper.readValue(contentAsString, TestModelHelper.AwesomeException.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), awesomeException.getStatus());
        Assert.assertEquals(error.getMessage(), awesomeException.getMessage());

        then(statisticService).should().incrementGetAmountCount();
        then(validationService).should().validId(id);
        then(accountBalanceService).should().getAmount(id);
    }

    @Test
    public void testAddAmountOk() throws Exception {
        Integer id = TestModelHelper.getInt();
        AmountDto amountDto = TestModelHelper.getAmountDto();
        String request = objectMapper.writeValueAsString(amountDto);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .put("/" + ControllerConstants.BALANCE + "/" + id)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        then(statisticService).should().incrementAddAmountCount();
        then(validationService).should().validId(id);
        then(validationService).should().validAmount(amountDto.getValue());
        then(accountBalanceService).should().addAmount(id, amountDto.getValue());
    }

    @Test
    public void testAddAmountBadId() throws Exception {
        Integer id = TestModelHelper.getInt();
        AmountDto amountDto = TestModelHelper.getAmountDto();
        String request = objectMapper.writeValueAsString(amountDto);

        BadIdException error = new BadIdException(id);
        willThrow(error).given(validationService).validId(id);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .put("/" + ControllerConstants.BALANCE + "/" + id)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        TestModelHelper.AwesomeException awesomeException = objectMapper.readValue(contentAsString, TestModelHelper.AwesomeException.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), awesomeException.getStatus());
        Assert.assertEquals(error.getMessage(), awesomeException.getMessage());

        then(statisticService).should().incrementAddAmountCount();
        then(validationService).should().validId(id);
    }

    @Test
    public void testAddAmountBadAmount() throws Exception {
        Integer id = TestModelHelper.getInt();
        AmountDto amountDto = TestModelHelper.getAmountDto();
        String request = objectMapper.writeValueAsString(amountDto);

        BadAmountException error = new BadAmountException();
        willThrow(error).given(validationService).validAmount(amountDto.getValue());

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .put("/" + ControllerConstants.BALANCE + "/" + id)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        TestModelHelper.AwesomeException awesomeException = objectMapper.readValue(contentAsString, TestModelHelper.AwesomeException.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), awesomeException.getStatus());
        Assert.assertEquals(error.getMessage(), awesomeException.getMessage());

        then(statisticService).should().incrementAddAmountCount();
        then(validationService).should().validId(id);
        then(validationService).should().validAmount(amountDto.getValue());
    }

    @Test
    public void testAddAmountAmountFitLong() throws Exception {
        Integer id = TestModelHelper.getInt();
        AmountDto amountDto = TestModelHelper.getAmountDto();
        String request = objectMapper.writeValueAsString(amountDto);

        AmountFitLongException error = mock(AmountFitLongException.class);
        willThrow(error).given(accountBalanceService).addAmount(id, amountDto.getValue());

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .put("/" + ControllerConstants.BALANCE + "/" + id)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isConflict());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        TestModelHelper.AwesomeException awesomeException = objectMapper.readValue(contentAsString, TestModelHelper.AwesomeException.class);
        Assert.assertEquals(HttpStatus.CONFLICT.getReasonPhrase(), awesomeException.getStatus());
        Assert.assertEquals(error.getMessage(), awesomeException.getMessage());

        then(statisticService).should().incrementAddAmountCount();
        then(validationService).should().validId(id);
        then(validationService).should().validAmount(amountDto.getValue());
        then(accountBalanceService).should().addAmount(id, amountDto.getValue());
    }
}
