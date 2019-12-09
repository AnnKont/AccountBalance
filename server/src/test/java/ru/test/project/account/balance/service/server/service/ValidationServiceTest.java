package ru.test.project.account.balance.service.server.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.test.project.account.balance.service.server.annotation.TestAnnotation;
import ru.test.project.account.balance.service.server.error.BadAmountException;
import ru.test.project.account.balance.service.server.error.BadIdException;
import ru.test.project.account.balance.service.server.service.impl.ValidationServiceImpl;
import ru.test.project.account.balance.service.server.util.TestModelHelper;

@SpringBootTest(classes = {ValidationServiceImpl.class})
@TestAnnotation
@RunWith(SpringRunner.class)
public class ValidationServiceTest {

    @Autowired
    private ValidationService validationService;

    @Test
    public void testValidateIdOk() throws BadIdException {
        Integer id = TestModelHelper.getInt();
        validationService.validId(id);
    }

    @Test(expected = BadIdException.class)
    public void testValidateIdErrorNegativeId() throws BadIdException {
        Integer id = -TestModelHelper.getInt();
        validationService.validId(id);
    }

    @Test(expected = BadIdException.class)
    public void testValidateIdErrorZero() throws BadIdException {
        Integer id = 0;
        validationService.validId(id);
    }

    @Test(expected = BadIdException.class)
    public void testValidateIdErrorNull() throws BadIdException {
        validationService.validId(null);
    }

    @Test
    public void testValidateAmountOk() throws BadAmountException {
        Long amount = TestModelHelper.getLong();
        validationService.validAmount(amount);
    }

    @Test(expected = BadAmountException.class)
    public void testValidateAmountErrorNull() throws BadAmountException {
        validationService.validAmount(null);
    }
}
