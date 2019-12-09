package ru.test.project.account.balance.service.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class AmountDto {

    /**
     * Value of amount
     */
    private Long value;
}

