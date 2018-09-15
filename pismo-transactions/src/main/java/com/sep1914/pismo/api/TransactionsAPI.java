package com.sep1914.pismo.api;

import com.sep1914.pismo.dto.PaymentDTO;
import com.sep1914.pismo.dto.TransactionDTO;
import com.sep1914.pismo.facade.PaymentFacade;
import com.sep1914.pismo.facade.TransactionFacade;
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

    @PostMapping("/transactions")
    public ResponseEntity<?> addTransaction(@RequestBody TransactionDTO transactionDTO) {
        transactionFacade.addTransaction(transactionDTO);

        return ResponseEntity.accepted().build();
    }

    @PostMapping("/payments")
    public ResponseEntity<?> addPayments(@RequestBody PaymentDTO[] paymentDTOs) {
        paymentFacade.addPayments(paymentDTOs);

        return ResponseEntity.accepted().build();
    }

}
