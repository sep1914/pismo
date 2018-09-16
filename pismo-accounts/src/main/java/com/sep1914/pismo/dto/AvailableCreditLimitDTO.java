package com.sep1914.pismo.dto;

import java.math.BigDecimal;

public class AvailableCreditLimitDTO {

    private BigDecimal amount;

    public AvailableCreditLimitDTO() {

    }

    public AvailableCreditLimitDTO(BigDecimal amount) {
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
        return "AvailableCreditLimitDTO{" +
                "amount=" + amount +
                '}';
    }
}
