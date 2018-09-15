package com.sep1914.pismo.facade;

import com.sep1914.pismo.entity.PaymentTracking;
import com.sep1914.pismo.entity.Transaction;

import java.util.List;

public class PaymentAddResult {

    private final List<PaymentTracking> paymentTrackings;

    private final Transaction paymentTransaction;

    public PaymentAddResult(List<PaymentTracking> paymentTrackings, Transaction paymentTransaction) {
        this.paymentTrackings = paymentTrackings;
        this.paymentTransaction = paymentTransaction;
    }

    public List<PaymentTracking> getPaymentTrackings() {
        return paymentTrackings;
    }

    public Transaction getPaymentTransaction() {
        return paymentTransaction;
    }

}
