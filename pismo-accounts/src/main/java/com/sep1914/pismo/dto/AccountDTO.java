package com.sep1914.pismo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sep1914.pismo.entity.Account;

import java.math.BigDecimal;

public class AccountDTO {

    @JsonProperty(value = "available_credit_limit", required = true)
    private BigDecimal availableCreditLimit;

    @JsonProperty(value = "available_withdrawal_limit", required = true)
    private BigDecimal availableWithdrawalLimit;

    AccountDTO() {

    }

    public AccountDTO(BigDecimal availableCreditLimit, BigDecimal availableWithdrawalLimit) {
        this.availableCreditLimit = availableCreditLimit;
        this.availableWithdrawalLimit = availableWithdrawalLimit;
    }

    public AccountDTO(Account account) {
        this.availableCreditLimit = account.getAvailableCreditLimit();
        this.availableWithdrawalLimit = account.getAvailableWithdrawalLimit();
    }

    public BigDecimal getAvailableCreditLimit() {
        return availableCreditLimit;
    }

    public void setAvailableCreditLimit(BigDecimal availableCreditLimit) {
        this.availableCreditLimit = availableCreditLimit;
    }

    public BigDecimal getAvailableWithdrawalLimit() {
        return availableWithdrawalLimit;
    }

    public void setAvailableWithdrawalLimit(BigDecimal availableWithdrawalLimit) {
        this.availableWithdrawalLimit = availableWithdrawalLimit;
    }

}
