package com.sep1914.pismo.entity;

import javax.persistence.*;

@Entity
@Table(name = "PaymentsTracking")
public class PaymentTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PaymentTracking_ID")
    private long id;

    @JoinColumn(name = "CreditTransaction_ID")
    private Transaction creditTransaction;

    @JoinColumn(name = "DebitTransaction_ID")
    private Transaction debitTransaction;

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

}
