package com.sep1914.pismo.facade;

import com.sep1914.pismo.dto.PaymentDTO;
import com.sep1914.pismo.entity.PaymentTracking;
import com.sep1914.pismo.entity.Transaction;
import com.sep1914.pismo.persistence.OperationTypeRepository;
import com.sep1914.pismo.persistence.PaymentTrackingRepository;
import com.sep1914.pismo.persistence.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Component
public class PaymentFacade {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    @Autowired
    private PaymentTrackingRepository paymentTrackingRepository;

    @Transactional
    public void addPayments(PaymentDTO[] paymentDTOs) {
        for (PaymentDTO paymentDTO : paymentDTOs) {
            PaymentAddResult paymentAddResult = addPayment(paymentDTO);

            final Transaction paymentTransaction = savePaymentTransaction(paymentAddResult);
            savePaymentTrackings(paymentAddResult, paymentTransaction);
        }
    }

    private void savePaymentTrackings(PaymentAddResult paymentAddResult, Transaction transaction) {
        paymentAddResult.getPaymentTrackings().forEach(t -> {
            t.setCreditTransaction(transaction);
            paymentTrackingRepository.save(t);
        });
    }

    private Transaction savePaymentTransaction(PaymentAddResult paymentAddResult) {
        return transactionRepository.save(paymentAddResult.getPaymentTransaction());
    }

    PaymentAddResult addPayment(PaymentDTO paymentDTO) {
        List<Transaction> transactions = transactionRepository.findNegativeBalanceByAccountId(paymentDTO.getAccountId());
        List<PaymentTracking> trackings = new LinkedList<>();

        BigDecimal remainingAmount = paymentDTO.getAmount();

        for (Transaction transaction : transactions) {
            if (remainingAmount.equals(ZERO.setScale(2))) {
                break;
            }

            BigDecimal balanceAdjust = min(remainingAmount, transaction.getBalance().abs());
            transaction.setBalance(transaction.getBalance().add(balanceAdjust));
            remainingAmount = remainingAmount.subtract(balanceAdjust);

            addPaymentTracking(trackings, transaction, balanceAdjust);
        }

        Transaction paymentTransaction = createPaymentTransaction(paymentDTO, remainingAmount);

        return new PaymentAddResult(trackings, paymentTransaction);
    }

    private Transaction createPaymentTransaction(PaymentDTO paymentDTO, BigDecimal remainingAmount) {
        Transaction paymentTransaction = new Transaction();

        paymentTransaction.setBalance(remainingAmount);
        paymentTransaction.setEventDate(LocalDate.now());
        paymentTransaction.setDueDate(LocalDate.now());
        paymentTransaction.setOperationType(operationTypeRepository.getOne(4L));
        paymentTransaction.setAmount(paymentDTO.getAmount());
        paymentTransaction.setAccountId(paymentDTO.getAccountId());

        return paymentTransaction;
    }

    private void addPaymentTracking(List<PaymentTracking> trackings, Transaction transaction, BigDecimal balanceAdjust) {
        PaymentTracking paymentTracking = new PaymentTracking();
        paymentTracking.setDebitTransaction(transaction);
        paymentTracking.setAmount(balanceAdjust);
        trackings.add(paymentTracking);
    }

    BigDecimal min(BigDecimal one, BigDecimal another) {
        if (one.compareTo(another) <= 0) {
            return one;
        }

        return another;
    }

}
