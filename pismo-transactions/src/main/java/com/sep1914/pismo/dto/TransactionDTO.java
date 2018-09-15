package com.sep1914.pismo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TransactionDTO {

    @JsonProperty(value = "account_id", required = true)
    private long accountId;

    @JsonProperty(value = "operation_type_id", required = true)
    private long operationTypeId;

    @JsonProperty(value = "amount", required = true)
    private BigDecimal amount;

    public TransactionDTO() {

    }

    public TransactionDTO(long accountId, long operationTypeId, BigDecimal amount) {
        this.accountId = accountId;
        this.operationTypeId = operationTypeId;
        this.amount = amount;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(long operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
