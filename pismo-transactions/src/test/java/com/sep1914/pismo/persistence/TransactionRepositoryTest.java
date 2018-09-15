package com.sep1914.pismo.persistence;

import com.sep1914.pismo.entity.OperationType;
import com.sep1914.pismo.entity.Transaction;
import com.sep1914.pismo.util.OperationTypeTestUtil;
import org.junit.Assert;
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
        List<Transaction> transactions = transactionRepository.findByAccountId(1L);

        assertEquals(3, transactions.size());
        assertEquals(BigDecimal.valueOf(-50.00).setScale(2), transactions.get(0).getBalance());
        assertEquals(BigDecimal.valueOf(-23.50).setScale(2), transactions.get(1).getBalance());
        assertEquals(BigDecimal.valueOf(-18.70).setScale(2), transactions.get(2).getBalance());
    }

    private void saveTransactions() {
        saveTransaction(1L, operationTypeRepository.getOne(1L), -18.70);
        saveTransaction(1L, operationTypeRepository.getOne(2L), -23.50);
        saveTransaction(1L, operationTypeRepository.getOne(3L), -50.00);

        saveTransaction(2L, operationTypeRepository.getOne(3L), -128.00);
    }

    private void saveTransaction(long accountId, OperationType operationType, double amount) {
        Transaction transaction = new Transaction();

        transaction.setAccountId(accountId);
        transaction.setOperationType(operationType);
        transaction.setAmount(BigDecimal.valueOf(amount).setScale(2));
        transaction.setBalance(BigDecimal.valueOf(amount).setScale(2));
        transaction.setEventDate(LocalDate.now());
        transaction.setDueDate(LocalDate.now());

        transactionRepository.save(transaction);
    }

}