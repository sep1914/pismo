package com.sep1914.pismo.persistence;

import com.sep1914.pismo.entity.OperationType;
import com.sep1914.pismo.entity.Transaction;
import com.sep1914.pismo.util.OperationTypeTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    @Before
    public void before() {
        OperationTypeTestUtil.initializeOperationTypes(operationTypeRepository);
        saveTransactions();
    }

    @Test
    @Transactional
    public void testListByAccountId() {
        List<Transaction> transactions = transactionRepository.findNegativeBalanceByAccountId(1L);

        assertEquals(3, transactions.size());
        assertEquals(BigDecimal.valueOf(-50.00).setScale(2), transactions.get(0).getBalance());
        assertEquals(BigDecimal.valueOf(-23.50).setScale(2), transactions.get(1).getBalance());
        assertEquals(BigDecimal.valueOf(-18.70).setScale(2), transactions.get(2).getBalance());
    }

    @Test
    @Transactional
    public void testSortByEventDate() {
        List<Transaction> transactions = transactionRepository.findNegativeBalanceByAccountId(2L);

        assertEquals(3, transactions.size());
        assertEquals(BigDecimal.valueOf(-1.00).setScale(2), transactions.get(0).getBalance());
        assertEquals(BigDecimal.valueOf(-1.00).setScale(2), transactions.get(1).getBalance());
        assertEquals(BigDecimal.valueOf(-1.00).setScale(2), transactions.get(2).getBalance());
    }

    @Test
    @Transactional
    public void testSortByChargeOrder() {
        List<Transaction> transactions = transactionRepository.findNegativeBalanceByAccountId(3L);

        assertEquals(3, transactions.size());
        assertEquals(BigDecimal.valueOf(-10.00).setScale(2), transactions.get(0).getBalance());
        assertEquals(BigDecimal.valueOf(-20.00).setScale(2), transactions.get(1).getBalance());
        assertEquals(BigDecimal.valueOf(-30.00).setScale(2), transactions.get(2).getBalance());
    }

    @Test
    @Transactional
    public void testSortByBalance() {
        List<Transaction> transactions = transactionRepository.findNegativeBalanceByAccountId(4L);

        assertEquals(3, transactions.size());
        assertEquals(BigDecimal.valueOf(-300.00).setScale(2), transactions.get(0).getBalance());
        assertEquals(BigDecimal.valueOf(-200.00).setScale(2), transactions.get(1).getBalance());
        assertEquals(BigDecimal.valueOf(-100.00).setScale(2), transactions.get(2).getBalance());
    }

    private void saveTransactions() {
        saveTransaction(1L, operationTypeRepository.getOne(1L), -18.70, LocalDate.of(2018, 8, 30));
        saveTransaction(1L, operationTypeRepository.getOne(2L), -23.50, LocalDate.of(2018, 8, 15));
        saveTransaction(1L, operationTypeRepository.getOne(3L), -50.00, LocalDate.of(2018, 8, 1));

        saveTransaction(2L, operationTypeRepository.getOne(3L), -1.00, LocalDate.of(2018, 8, 20));
        saveTransaction(2L, operationTypeRepository.getOne(3L), -1.00, LocalDate.of(2018, 8, 10));
        saveTransaction(2L, operationTypeRepository.getOne(3L), -1.00, LocalDate.of(2018, 8, 1));

        saveTransaction(3L, operationTypeRepository.getOne(1L), -30.00, LocalDate.of(2018, 8, 1));
        saveTransaction(3L, operationTypeRepository.getOne(2L), -20.00, LocalDate.of(2018, 8, 1));
        saveTransaction(3L, operationTypeRepository.getOne(3L), -10.00, LocalDate.of(2018, 8, 1));

        saveTransaction(4L, operationTypeRepository.getOne(1L), -100.00, LocalDate.of(2018, 8, 1));
        saveTransaction(4L, operationTypeRepository.getOne(1L), -200.00, LocalDate.of(2018, 8, 1));
        saveTransaction(4L, operationTypeRepository.getOne(1L), -300.00, LocalDate.of(2018, 8, 1));
    }

    private void saveTransaction(long accountId, OperationType operationType, double amount, LocalDate date) {
        Transaction transaction = new Transaction();

        transaction.setAccountId(accountId);
        transaction.setOperationType(operationType);
        transaction.setAmount(BigDecimal.valueOf(amount).setScale(2));
        transaction.setBalance(BigDecimal.valueOf(amount).setScale(2));
        transaction.setEventDate(date);
        transaction.setDueDate(date);

        transactionRepository.save(transaction);
    }

}