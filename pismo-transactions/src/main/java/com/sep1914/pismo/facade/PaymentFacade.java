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
            addPayment(paymentDTO);
        }
    }

    void addPayment(PaymentDTO paymentDTO) {
        List<Transaction> transactions = transactionRepository.findNegativeBalanceByAccountId(paymentDTO.getAccountId());
        List<PaymentTracking> trackings = new LinkedList<>();

        BigDecimal remainingAmount = paymentDTO.getAmount();

        for (Transaction transaction : transactions) {
            if (remainingAmount.equals(ZERO)) {
                break;
            }

            BigDecimal balanceAdjust = min(remainingAmount, transaction.getBalance().abs());
            transaction.setBalance(transaction.getBalance().add(balanceAdjust));
            remainingAmount = remainingAmount.subtract(balanceAdjust);

            PaymentTracking paymentTracking = new PaymentTracking();
            paymentTracking.setDebitTransaction(transaction);
            paymentTracking.setAmount(balanceAdjust);
            trackings.add(paymentTracking);
        }

        Transaction paymentTransaction = new Transaction();
        paymentTransaction.setBalance(remainingAmount);
        paymentTransaction.setEventDate(LocalDate.now());
        paymentTransaction.setDueDate(LocalDate.now());
        paymentTransaction.setOperationType(operationTypeRepository.getOne(4L));
        paymentTransaction.setAmount(paymentDTO.getAmount());
        paymentTransaction.setAccountId(paymentDTO.getAccountId());

        final Transaction managedPaymentTransaction = transactionRepository.save(paymentTransaction);

        trackings.forEach(t -> {
            t.setCreditTransaction(managedPaymentTransaction);
            paymentTrackingRepository.save(t);
        });
    }

    BigDecimal min(BigDecimal one, BigDecimal another) {
        if (one.compareTo(another) <= 0) {
            return one;
        }

        return another;
    }

}
