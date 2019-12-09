package ru.test.project.account.balance.service.server.controller;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.test.project.account.balance.service.server.constant.ControllerConstants;
import ru.test.project.account.balance.service.server.dto.AmountDto;
import ru.test.project.account.balance.service.server.error.AmountFitLongException;
import ru.test.project.account.balance.service.server.error.BadAmountException;
import ru.test.project.account.balance.service.server.error.BadIdException;
import ru.test.project.account.balance.service.server.error.ItemNotFoundException;
import ru.test.project.account.balance.service.server.service.AccountBalanceService;
import ru.test.project.account.balance.service.server.service.StatisticService;
import ru.test.project.account.balance.service.server.service.ValidationService;

/**
 * REST API controller for account balance
 */
@Api(value = "API account balance")
@RestController
@RequestMapping(value = ControllerConstants.BALANCE, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AccountBalanceController {

    @Autowired
    private AccountBalanceService accountBalanceService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private StatisticService statisticService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Identificator is not valid"),
            @ApiResponse(code = 404, message = "Account not found")})
    @ApiOperation(value = "Return amount of account balance", notes = "Get account id from url")
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long getAmount(@ApiParam(value = "Account id") @PathVariable("id") Integer id)
            throws ItemNotFoundException, BadIdException {
        log.info("Get amount by id: {}", id);
        statisticService.incrementGetAmountCount();
        validationService.validId(id);
        return accountBalanceService.getAmount(id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Identificator or amount is not valid"),
            @ApiResponse(code = 409, message = "Amount is too large after add")})
    @ApiOperation(value = "Add amount to account balance", notes = "Get account id from url, get amount from body")
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void addAmount(@ApiParam(value = "Account id") @PathVariable("id") Integer id,
                          @ApiParam(value = "Request body") @RequestBody AmountDto amountDto)
            throws BadIdException, BadAmountException, AmountFitLongException {
        log.info("Add amount by id: {}", id);
        statisticService.incrementAddAmountCount();
        validationService.validId(id);
        validationService.validAmount(amountDto.getValue());
        accountBalanceService.addAmount(id, amountDto.getValue());
    }
}
