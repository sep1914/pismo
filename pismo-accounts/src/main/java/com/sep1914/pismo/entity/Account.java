package com.sep1914.pismo.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Account_ID")
    private long id;

    @Column(name = "AvailableCreditLimit")
    private BigDecimal availableCreditLimit;

    @Column(name = "AvailableWithdrawalLimit")
    private BigDecimal availableWithdrawalLimit;

    public Account() {
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
