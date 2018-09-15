package com.sep1914.pismo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "Accounts")
public class Account {

    @Id
    @Column(name = "Account_ID")
    private long id;

    @Column(name = "AvailableCreditLimit", scale = 2)
    private BigDecimal availableCreditLimit;

    @Column(name = "AvailableWithdrawalLimit", scale = 2)
    private BigDecimal availableWithdrawalLimit;

    public Account() {
    }

    public Account(BigDecimal availableCreditLimit, BigDecimal availableWithdrawalLimit) {
        this.availableCreditLimit = availableCreditLimit;
        this.availableWithdrawalLimit = availableWithdrawalLimit;
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

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}


