package ru.test.project.account.balance.service.server.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.test.project.account.balance.service.server.annotation.TestAnnotation;
import ru.test.project.account.balance.service.server.dao.AccountBalanceDao;
import ru.test.project.account.balance.service.server.error.AmountFitLongException;
import ru.test.project.account.balance.service.server.error.ItemNotFoundException;
import ru.test.project.account.balance.service.server.service.impl.AccountBalanceServiceImpl;
import ru.test.project.account.balance.service.server.util.TestModelHelper;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SpringBootTest(classes = {AccountBalanceServiceImpl.class})
@TestAnnotation
@RunWith(SpringRunner.class)
public class AccountBalanceServiceTest {

    @Autowired
    private AccountBalanceService accountBalanceService;

    @MockBean
    private AccountBalanceDao accountBalanceDao;

    @After
    public void tearDown() {
        verifyNoMoreInteractions(accountBalanceDao);
    }

    @Test
    public void testGetAmountOk() throws ItemNotFoundException {
        Integer id = TestModelHelper.getInt();
        Long expectedAmount = TestModelHelper.getLong();
        given(accountBalanceDao.getAmount(id)).willReturn(expectedAmount);

        Long actualAmount = accountBalanceService.getAmount(id);
        Assert.assertEquals(expectedAmount, actualAmount);
        then(accountBalanceDao).should().getAmount(id);
    }

    @Test
    public void testGetAmountErrorItemNotFound() throws ItemNotFoundException {
        Integer id = TestModelHelper.getInt();
        ItemNotFoundException error = new ItemNotFoundException(id);
        given(accountBalanceDao.getAmount(id)).willThrow(error);

        try {
            accountBalanceService.getAmount(id);
            Assert.fail("Expected ItemNotFoundException, but didn't get it.");
        } catch (ItemNotFoundException e) {
            then(accountBalanceDao).should().getAmount(id);
            Assert.assertEquals(error.getMessage(), e.getMessage());
        }
    }

    @Test
    public void testAddAmountOk() throws AmountFitLongException {
        Integer id = TestModelHelper.getInt();
        Long amount = TestModelHelper.getLong();

        accountBalanceService.addAmount(id, amount);
        then(accountBalanceDao).should().addAmount(id, amount);
    }
}
