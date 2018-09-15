package com.sep1914.pismo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class PaymentDTO {

    @JsonProperty(value = "account_id", required = true)
    private long accountId;

    @JsonProperty(value = "amount", required = true)
    private BigDecimal amount;

    PaymentDTO() {

    }

    public PaymentDTO(long accountId, BigDecimal amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
