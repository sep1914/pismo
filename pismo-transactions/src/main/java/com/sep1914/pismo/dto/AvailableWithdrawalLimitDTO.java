package com.sep1914.pismo.dto;

import java.math.BigDecimal;

public class AvailableWithdrawalLimitDTO {

    private BigDecimal amount;

    public AvailableWithdrawalLimitDTO() {

    }

    public AvailableWithdrawalLimitDTO(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "AvailableWithdrawalLimitDTO{" +
                "amount=" + amount +
                '}';
    }

}

