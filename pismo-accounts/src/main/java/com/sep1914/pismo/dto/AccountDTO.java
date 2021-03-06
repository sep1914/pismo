package com.sep1914.pismo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sep1914.pismo.entity.Account;

import java.math.BigDecimal;

public class AccountDTO {

    @JsonProperty(value = "account_id")
    private Long accountId;

    @JsonProperty(value = "available_credit_limit", required = true)
    private AvailableCreditLimitDTO availableCreditLimit;

    @JsonProperty(value = "available_withdraw_limit", required = true)
    private AvailableWithdrawalLimitDTO availableWithdrawalLimit;

    public AccountDTO() {

    }

    public AccountDTO(Account account) {
        this.accountId = account.getId();
        this.availableCreditLimit = new AvailableCreditLimitDTO(account.getAvailableCreditLimit());
        this.availableWithdrawalLimit = new AvailableWithdrawalLimitDTO(account.getAvailableWithdrawalLimit());
    }

    public AccountDTO(AvailableCreditLimitDTO availableCreditLimit, AvailableWithdrawalLimitDTO availableWithdrawalLimit) {
        this.availableCreditLimit = availableCreditLimit;
        this.availableWithdrawalLimit = availableWithdrawalLimit;
    }

    public AccountDTO(BigDecimal creditLimit, BigDecimal withdrawalLimit) {
        this.availableCreditLimit = new AvailableCreditLimitDTO(creditLimit);
        this.availableWithdrawalLimit = new AvailableWithdrawalLimitDTO(withdrawalLimit);
    }

    public AvailableCreditLimitDTO getAvailableCreditLimit() {
        return availableCreditLimit;
    }

    public void setAvailableCreditLimit(AvailableCreditLimitDTO availableCreditLimit) {
        this.availableCreditLimit = availableCreditLimit;
    }

    public AvailableWithdrawalLimitDTO getAvailableWithdrawalLimit() {
        return availableWithdrawalLimit;
    }

    public void setAvailableWithdrawalLimit(AvailableWithdrawalLimitDTO availableWithdrawalLimit) {
        this.availableWithdrawalLimit = availableWithdrawalLimit;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "accountId=" + accountId +
                ", availableCreditLimit=" + availableCreditLimit +
                ", availableWithdrawalLimit=" + availableWithdrawalLimit +
                '}';
    }

}
