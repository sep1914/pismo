package com.sep1914.pismo.facade;

import com.sep1914.pismo.dto.TransactionDTO;
import com.sep1914.pismo.entity.OperationType;
import com.sep1914.pismo.entity.Transaction;
import com.sep1914.pismo.facade.exception.InvalidOperationTypeException;
import com.sep1914.pismo.persistence.OperationTypeRepository;
import com.sep1914.pismo.persistence.TransactionRepository;
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

import static java.math.BigDecimal.ZERO;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionFacadeTest {

    @Autowired
    private TransactionFacade transactionFacade;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    @Before
    public void before() {
        OperationTypeTestUtil.initializeOperationTypes(operationTypeRepository);
    }

    @Test(expected = InvalidOperationTypeException.class)
    @Transactional
    public void testAddWithInvalidOperationType() {
        TransactionDTO transactionDTO = new TransactionDTO(42L, 999, ZERO);

        transactionFacade.addTransaction(transactionDTO);
    }

    @Test
    @Transactional
    public void testAddTransaction() {
        TransactionDTO transactionDTO = new TransactionDTO(42L, 3, BigDecimal.valueOf(123.45));

        transactionFacade.addTransaction(transactionDTO);

        Transaction transaction = transactionRepository.findById(1L).get();
        assertTransactionAttributes(transaction);
    }

    private void assertTransactionAttributes(Transaction transaction) {
        assertEquals(3, transaction.getOperationType().getId());
        assertEquals(42L, transaction.getAccountId());
        assertEquals(BigDecimal.valueOf(123.45), transaction.getAmount());
        assertEquals(BigDecimal.valueOf(123.45), transaction.getBalance());
        assertEquals(LocalDate.now(), transaction.getEventDate());
    }


}