package ru.test.project.account.balance.service.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.test.project.account.balance.service.server.constant.ControllerConstants;
import ru.test.project.account.balance.service.server.service.StatisticService;

/**
 * REST API controller for statistic
 */
@Api(value = "API statistic")
@RestController
@RequestMapping(value = ControllerConstants.STATISTIC, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful")})
    @ApiOperation(value = "Clear count of all request for addAmount and getAmount")
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void clearCountOfGetAndAddAmount() {
        log.info("Clear all counts");
        statisticService.clearMaps();
    }
}
