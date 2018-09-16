package com.sep1914.pismo.facade;

import com.sep1914.pismo.dto.TransactionDTO;
import com.sep1914.pismo.entity.OperationType;
import com.sep1914.pismo.entity.Transaction;
import com.sep1914.pismo.facade.exception.InvalidOperationTypeException;
import com.sep1914.pismo.persistence.OperationTypeRepository;
import com.sep1914.pismo.persistence.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TransactionFacade {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    public void addTransaction(TransactionDTO transactionDTO) {
        OperationType operationType = operationTypeRepository.findById(transactionDTO.getOperationTypeId())
                .orElseThrow(() -> new InvalidOperationTypeException());

        Transaction transaction = new Transaction();
        transaction.setAccountId(transactionDTO.getAccountId());
        transaction.setAmount(transactionDTO.getAmount().negate());
        transaction.setBalance(transactionDTO.getAmount().negate());
        transaction.setOperationType(operationType);
        transaction.setEventDate(LocalDate.now());

        transactionRepository.save(transaction);
    }

}
