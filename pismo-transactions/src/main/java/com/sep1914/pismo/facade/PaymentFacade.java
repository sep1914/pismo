package com.sep1914.pismo.facade;

import com.sep1914.pismo.dto.PaymentDTO;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class PaymentFacade {

    @Transactional
    public void addPayments(PaymentDTO[] paymentDTOs) {
    }

    void addPayment(PaymentDTO paymentDTO) {

    }

}
