package com.sep1914.pismo.facade;

import com.sep1914.pismo.dto.AccountDTO;
import com.sep1914.pismo.dto.PaymentDTO;
import com.sep1914.pismo.entity.PaymentTracking;
import com.sep1914.pismo.entity.Transaction;
import com.sep1914.pismo.facade.notifier.AccountNotifier;
import com.sep1914.pismo.persistence.OperationTypeRepository;
import com.sep1914.pismo.persistence.PaymentTrackingRepository;
import com.sep1914.pismo.persistence.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static com.sep1914.pismo.entity.OperationTypeEnum.PAYMENT;
import static java.math.BigDecimal.ZERO;

@Component
public class PaymentFacade {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    @Autowired
    private PaymentTrackingRepository paymentTrackingRepository;

    @Autowired
    @Qualifier("rest-notifier")
    private AccountNotifier accountNotifier;

    private final Logger LOGGER = LoggerFactory.getLogger(PaymentFacade.class);

    @Transactional
    public void addPayments(PaymentDTO[] paymentDTOs) {
        for (PaymentDTO paymentDTO : paymentDTOs) {
            LOGGER.info("Adding payment {}", paymentDTO);

            List<Transaction> transactions = transactionRepository
                    .findNegativeBalanceByAccountId(paymentDTO.getAccountId());
            PaymentAddResult paymentAddResult = addPayment(paymentDTO, transactions);

            final Transaction paymentTransaction = savePaymentTransaction(paymentAddResult);
            savePaymentTrackings(paymentAddResult, paymentTransaction);
            notifyAccounts(paymentAddResult, paymentDTO);
        }
    }

    PaymentAddResult addPayment(PaymentDTO paymentDTO, List<Transaction> transactions) {
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

        BigDecimal limitIncrease = paymentDTO.getAmount().subtract(remainingAmount);
        Transaction paymentTransaction = createPaymentTransaction(paymentDTO, remainingAmount);

        return new PaymentAddResult(trackings, paymentTransaction, limitIncrease);
    }

    private void notifyAccounts(PaymentAddResult paymentAddResult, PaymentDTO paymentDTO) {
        LOGGER.info("Notifying Account API of {}", paymentDTO);

        AccountDTO accountDTO =
                new AccountDTO(paymentAddResult.getLimitIncreaseAmount(),
                        paymentAddResult.getLimitIncreaseAmount());

        accountNotifier.notifyAccounts(accountDTO, paymentDTO.getAccountId());
    }

    private void savePaymentTrackings(PaymentAddResult paymentAddResult, Transaction transaction) {
        paymentAddResult.getPaymentTrackings().forEach(t -> {
            LOGGER.info("Saving payment tracking");

            t.setCreditTransaction(transaction);
            paymentTrackingRepository.save(t);
        });
    }

    private Transaction savePaymentTransaction(PaymentAddResult paymentAddResult) {
        LOGGER.info("Saving transaction");

        return transactionRepository.save(paymentAddResult.getPaymentTransaction());
    }

    private Transaction createPaymentTransaction(PaymentDTO paymentDTO, BigDecimal remainingAmount) {
        Transaction paymentTransaction = new Transaction();

        paymentTransaction.setBalance(remainingAmount);
        paymentTransaction.setEventDate(LocalDate.now());
        paymentTransaction.setDueDate(LocalDate.now());
        paymentTransaction.setOperationType(operationTypeRepository.getOne(PAYMENT.getOperationId()));
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
