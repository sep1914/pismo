package com.sep1914.pismo.facade;

import com.sep1914.pismo.dto.PaymentDTO;
import com.sep1914.pismo.entity.OperationType;
import com.sep1914.pismo.entity.PaymentTracking;
import com.sep1914.pismo.entity.Transaction;
import com.sep1914.pismo.persistence.OperationTypeRepository;
import com.sep1914.pismo.persistence.PaymentTrackingRepository;
import com.sep1914.pismo.persistence.TransactionRepository;
import com.sep1914.pismo.util.OperationTypeTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class PaymentFacadeTest {

    @Autowired
    private PaymentFacade paymentFacade;

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PaymentTrackingRepository paymentTrackingRepository;

    @Before
    public void before() {
        OperationTypeTestUtil.initializeOperationTypes(operationTypeRepository);
    }

    @Test
    @Transactional
    public void testPaymentLowerThanBalance() {
        PaymentDTO paymentDTO = new PaymentDTO(1L, BigDecimal.valueOf(10.00).setScale(2));
        addTransaction(1L, 1L, BigDecimal.valueOf(-20.00).setScale(2), LocalDate.of(2018, 8, 1));

        paymentFacade.addPayments(new PaymentDTO[] {paymentDTO});

        List<Transaction> transactions = transactionRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        Transaction originalTransaction = transactions.get(0);
        Transaction paymentTransaction = transactions.get(1);

        assertPaymentLowerThanBalanceTransactionUpdates(originalTransaction, paymentTransaction);
        assertPaymentLowerThanBalanceCorrectPaymentTracking(originalTransaction, paymentTransaction);
    }

    @Test
    @Transactional
    public void testPaymentHigherThanBalance() {
        PaymentDTO paymentDTO = new PaymentDTO(1L, BigDecimal.valueOf(100.00).setScale(2));
        addTransaction(1L, 1L, BigDecimal.valueOf(-20.00).setScale(2), LocalDate.of(2018, 8, 1));

        paymentFacade.addPayments(new PaymentDTO[] {paymentDTO});

        List<Transaction> transactions = transactionRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        Transaction originalTransaction = transactions.get(0);
        Transaction paymentTransaction = transactions.get(1);

        assertPaymentHigherThanBalanceTransactionUpdates(originalTransaction, paymentTransaction);
        assertPaymentHigherThanBalanceCorrectPaymentTracking(originalTransaction, paymentTransaction);
    }

    @Test
    @Transactional
    public void testPaymentEqualsToBalance() {
        PaymentDTO paymentDTO = new PaymentDTO(1L, BigDecimal.valueOf(100.00).setScale(2));
        addTransaction(1L, 1L, BigDecimal.valueOf(-100.00).setScale(2), LocalDate.of(2018, 8, 1));

        paymentFacade.addPayments(new PaymentDTO[] {paymentDTO});

        List<Transaction> transactions = transactionRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        Transaction originalTransaction = transactions.get(0);
        Transaction paymentTransaction = transactions.get(1);

        assertPaymentEqualsToBalanceTransactionUpdates(originalTransaction, paymentTransaction);
        assertPaymentEqualsToBalanceCorrectPaymentTracking(originalTransaction, paymentTransaction);
    }

    @Test
    @Transactional
    public void testMixedScenario() {
        PaymentDTO paymentDTO = new PaymentDTO(1L, BigDecimal.valueOf(70.00).setScale(2));
        addTransaction(1L, 3L, BigDecimal.valueOf(-50.00).setScale(2), LocalDate.of(2017, 4, 5));
        addTransaction(1L, 1L, BigDecimal.valueOf(-23.50).setScale(2), LocalDate.of(2017, 4, 10));
        addTransaction(1L, 1L, BigDecimal.valueOf(-18.70).setScale(2), LocalDate.of(2017, 4, 30));

        paymentFacade.addPayments(new PaymentDTO[] {paymentDTO});

        assertBalancesAreCorrect();
        assertTrackingsAreCorrect();
    }

    private void assertTrackingsAreCorrect() {
        List<PaymentTracking> paymentTrackings = paymentTrackingRepository.findAll(new Sort(Sort.DEFAULT_DIRECTION, "id"));
        assertEquals(2, paymentTrackings.size());
        assertEquals(BigDecimal.valueOf(50.00).setScale(2), paymentTrackings.get(0).getAmount());
        assertEquals(BigDecimal.valueOf(20.00).setScale(2), paymentTrackings.get(1).getAmount());
    }

    private void assertBalancesAreCorrect() {
        List<Transaction> transactions = transactionRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        assertEquals(BigDecimal.ZERO.setScale(2), transactions.get(0).getBalance());
        assertEquals(BigDecimal.valueOf(-3.50).setScale(2), transactions.get(1).getBalance());
        assertEquals(BigDecimal.valueOf(-18.70).setScale(2), transactions.get(2).getBalance());
        assertEquals(BigDecimal.ZERO.setScale(2), transactions.get(3).getBalance());
    }

    private void assertPaymentEqualsToBalanceTransactionUpdates(Transaction originalTransaction, Transaction paymentTransaction) {
        assertEquals(BigDecimal.ZERO.setScale(2), originalTransaction.getBalance());
        assertEquals(BigDecimal.ZERO.setScale(2), paymentTransaction.getBalance());
    }

    private void assertPaymentEqualsToBalanceCorrectPaymentTracking(Transaction originalTransaction, Transaction paymentTransaction) {
        PaymentTracking paymentTracking = paymentTrackingRepository.findAll().get(0);
        assertEquals(paymentTransaction, paymentTracking.getCreditTransaction());
        assertEquals(originalTransaction, paymentTracking.getDebitTransaction());
        assertEquals(BigDecimal.valueOf(100.00).setScale(2), paymentTracking.getAmount());
    }

    private void assertPaymentHigherThanBalanceTransactionUpdates(Transaction originalTransaction, Transaction paymentTransaction) {
        assertEquals(BigDecimal.ZERO.setScale(2), originalTransaction.getBalance());
        assertEquals(BigDecimal.valueOf(80.00).setScale(2), paymentTransaction.getBalance());
    }

    private void assertPaymentHigherThanBalanceCorrectPaymentTracking(Transaction originalTransaction, Transaction paymentTransaction) {
        PaymentTracking paymentTracking = paymentTrackingRepository.findAll().get(0);
        assertEquals(paymentTransaction, paymentTracking.getCreditTransaction());
        assertEquals(originalTransaction, paymentTracking.getDebitTransaction());
        assertEquals(BigDecimal.valueOf(20.00).setScale(2), paymentTracking.getAmount());
    }

    private void assertPaymentLowerThanBalanceTransactionUpdates(Transaction originalTransaction, Transaction paymentTransaction) {
        assertEquals(BigDecimal.valueOf(-10.00).setScale(2), originalTransaction.getBalance());
        assertEquals(BigDecimal.ZERO.setScale(2), paymentTransaction.getBalance());
    }

    private void assertPaymentLowerThanBalanceCorrectPaymentTracking(Transaction originalTransaction, Transaction paymentTransaction) {
        PaymentTracking paymentTracking = paymentTrackingRepository.findAll().get(0);
        assertEquals(paymentTransaction, paymentTracking.getCreditTransaction());
        assertEquals(originalTransaction, paymentTracking.getDebitTransaction());
        assertEquals(BigDecimal.valueOf(10.00).setScale(2), paymentTracking.getAmount());
    }

    private void addTransaction(long accountId, long operationId, BigDecimal amount, LocalDate date) {
        OperationType operationType = operationTypeRepository.getOne(operationId);

        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId);
        transaction.setOperationType(operationType);
        transaction.setAmount(amount);
        transaction.setBalance(amount);
        transaction.setEventDate(date);
        transaction.setDueDate(date);

        transactionRepository.save(transaction);
    }

}