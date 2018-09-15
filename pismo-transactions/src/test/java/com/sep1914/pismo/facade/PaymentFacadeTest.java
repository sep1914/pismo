package com.sep1914.pismo.facade;

import com.sep1914.pismo.dto.PaymentDTO;
import com.sep1914.pismo.entity.OperationType;
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
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;

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
        addTransaction(1L, BigDecimal.valueOf(-20.00).setScale(2), LocalDate.of(2018, 8, 1));

        paymentFacade.addPayment(paymentDTO);

        Transaction transaction = transactionRepository.findAll().get(0);
        assertEquals(BigDecimal.valueOf(-10.00).setScale(2), transaction.getBalance());
    }

    private void addTransaction(long accountId, BigDecimal amount, LocalDate date) {
        OperationType operationType = operationTypeRepository.getOne(4L);

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