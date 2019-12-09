package ru.test.project.account.balance.service.server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel(description = "Amount to add to balance")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AmountDto {

    /**
     * Value of amount
     */
    @ApiModelProperty("Value of amount")
    private Long value;
}
