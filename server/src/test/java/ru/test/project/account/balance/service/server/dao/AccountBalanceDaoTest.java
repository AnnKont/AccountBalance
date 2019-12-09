package ru.test.project.account.balance.service.server.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import ru.test.project.account.balance.service.server.annotation.TestAnnotation;
import ru.test.project.account.balance.service.server.constant.ExceptionDescription;
import ru.test.project.account.balance.service.server.dao.impl.AccountBalanceDaoImpl;
import ru.test.project.account.balance.service.server.error.AmountFitLongException;
import ru.test.project.account.balance.service.server.error.ItemNotFoundException;
import ru.test.project.account.balance.service.server.util.TestModelHelper;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SpringBootTest(classes = {AccountBalanceDaoImpl.class})
@TestAnnotation
@RunWith(SpringRunner.class)
public class AccountBalanceDaoTest {

    @Autowired
    private AccountBalanceDao dao;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @After
    public void tearDown() {
        verifyNoMoreInteractions(jdbcTemplate);
    }

    @Test
    public void testGetAmountOk() throws ItemNotFoundException {
        Integer id = TestModelHelper.getInt();
        Long expectedAmount = TestModelHelper.getLong();
        given(jdbcTemplate.queryForObject(anyString(), eq(new Object[]{id}), eq(Long.class))).willReturn(expectedAmount);
        Long actualAmount = dao.getAmount(id);
        Assert.assertEquals(expectedAmount, actualAmount);
        then(jdbcTemplate).should().queryForObject(anyString(), eq(new Object[]{id}), eq(Long.class));
    }

    @Test
    public void testGetAmountErrorItemNotFound() {
        Integer id = TestModelHelper.getInt();
        given(jdbcTemplate.queryForObject(anyString(), eq(new Object[]{id}), eq(Long.class))).willThrow(EmptyResultDataAccessException.class);
        try {
            dao.getAmount(id);
            Assert.fail("Expected ItemNotFoundException, but didn't get it.");
        } catch (ItemNotFoundException e) {
            Assert.assertEquals(ExceptionDescription.EXCEPTION_ITEM_NOT_FOUND + id, e.getMessage());
            then(jdbcTemplate).should().queryForObject(anyString(), eq(new Object[]{id}), eq(Long.class));
        }
    }

    @Test
    public void testAddAmountOkForFirstTime() throws AmountFitLongException {
        Integer id = TestModelHelper.getInt();
        Long amount = TestModelHelper.getLong();
        given(jdbcTemplate.update(anyString(), eq(amount), eq(id))).willReturn(0);
        dao.addAmount(id, amount);
        then(jdbcTemplate).should().update(anyString(), eq(amount), eq(id));
        then(jdbcTemplate).should().update(anyString(), eq(id), eq(amount));
    }

    @Test
    public void testAddAmountOkForSecondTime() throws AmountFitLongException {
        Integer id = TestModelHelper.getInt();
        Long amount = TestModelHelper.getLong();
        given(jdbcTemplate.update(anyString(), eq(amount), eq(id))).willReturn(1);
        dao.addAmount(id, amount);
        then(jdbcTemplate).should().update(anyString(), eq(amount), eq(id));
    }
}
