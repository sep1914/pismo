package com.sep1914.pismo.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PaymentsTracking")
public class PaymentTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PaymentTracking_ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "CreditTransaction_ID")
    private Transaction creditTransaction;

    @ManyToOne
    @JoinColumn(name = "DebitTransaction_ID")
    private Transaction debitTransaction;

    @Column(name = "Amount", nullable = false)
    private BigDecimal amount;

    public PaymentTracking() {
    }

    public Transaction getCreditTransaction() {
        return creditTransaction;
    }

    public void setCreditTransaction(Transaction creditTransaction) {
        this.creditTransaction = creditTransaction;
    }

    public Transaction getDebitTransaction() {
        return debitTransaction;
    }

    public void setDebitTransaction(Transaction debitTransaction) {
        this.debitTransaction = debitTransaction;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
