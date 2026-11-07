package com.example.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountBalanceResponse {
    private String accountId;
    private String customerId;
    private String currency;
    private String limitRef;
    private String category;
    private String limitId;
    private String limitExpiryDate;

    private BigDecimal workingBalance;
    private BigDecimal lockedAmountTotal;
    private BigDecimal limitBalance;
}
