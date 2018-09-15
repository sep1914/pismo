package com.sep1914.pismo.facade;

import com.sep1914.pismo.entity.PaymentTracking;
import com.sep1914.pismo.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class PaymentAddResult {

    private final List<PaymentTracking> paymentTrackings;

    private final Transaction paymentTransaction;

    private final BigDecimal limitIncreaseAmount;

    public PaymentAddResult(List<PaymentTracking> paymentTrackings, Transaction paymentTransaction, BigDecimal limitIncreaseAmount) {
        this.paymentTrackings = paymentTrackings;
        this.paymentTransaction = paymentTransaction;
        this.limitIncreaseAmount = limitIncreaseAmount;
    }

    public List<PaymentTracking> getPaymentTrackings() {
        return paymentTrackings;
    }

    public Transaction getPaymentTransaction() {
        return paymentTransaction;
    }

    public BigDecimal getLimitIncreaseAmount() {
        return limitIncreaseAmount;
    }

}
