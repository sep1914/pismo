package com.sep1914.pismo.api;

import com.sep1914.pismo.dto.PaymentDTO;
import com.sep1914.pismo.dto.TransactionDTO;
import com.sep1914.pismo.facade.PaymentFacade;
import com.sep1914.pismo.facade.TransactionFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class TransactionsAPI {

    @Autowired
    private TransactionFacade transactionFacade;

    @Autowired
    private PaymentFacade paymentFacade;

    private final Logger LOGGER = LoggerFactory.getLogger(TransactionsAPI.class);

    @PostMapping("/transactions")
    public ResponseEntity<?> addTransaction(@RequestBody TransactionDTO transactionDTO) {
        LOGGER.info("Adding transaction {}", transactionDTO);
        transactionFacade.addTransaction(transactionDTO);

        LOGGER.info("Transaction added successfully");
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/payments")
    public ResponseEntity<?> addPayments(@RequestBody PaymentDTO[] paymentDTOs) {
        LOGGER.info("Adding {} payments", paymentDTOs.length);
        paymentFacade.addPayments(paymentDTOs);

        LOGGER.info("Payments added successfully");
        return ResponseEntity.accepted().build();
    }

}
