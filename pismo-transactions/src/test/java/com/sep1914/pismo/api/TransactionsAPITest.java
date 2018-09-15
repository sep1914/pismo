package com.sep1914.pismo.api;

import com.sep1914.pismo.facade.PaymentFacade;
import com.sep1914.pismo.facade.TransactionFacade;
import com.sep1914.pismo.facade.exception.InvalidOperationTypeException;
import com.sep1914.pismo.facade.exception.InvalidPaymentAccountException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionsAPI.class)
public class TransactionsAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentFacade paymentFacade;

    @MockBean
    private TransactionFacade transactionFacade;

    @Test
    public void testSuccessfulAddTransaction() throws Exception {
        mockMvc.perform(post("/v1/transactions")
                .contentType("application/json")
                .content("{" +
                        "\"account_id\": 1," +
                        "\"operation_type_id\": 1," +
                        "\"amount\": 123.45" +
                        "}"))
                .andExpect(status().isAccepted());

        verify(transactionFacade, times(1)).addTransaction(any());
    }

    @Test
    public void testInvalidOperationTypeException() throws Exception {
        doThrow(new InvalidOperationTypeException()).when(transactionFacade).addTransaction(any());

        mockMvc.perform(post("/v1/transactions")
                .contentType("application/json")
                .content("{" +
                        "\"account_id\": 1," +
                        "\"operation_type_id\": 999," +
                        "\"amount\": 123.45" +
                        "}"))
                .andExpect(status().isBadRequest());

        verify(transactionFacade, times(1)).addTransaction(any());
    }

    @Test
    public void testSuccessfulAddPayment() throws Exception {
        mockMvc.perform(post("/v1/payments")
                .contentType("application/json")
                .content("[{" +
                        "\"account_id\": 1," +
                        "\"amount\": 123.45" +
                        "}]"))
                .andExpect(status().isAccepted());

        verify(paymentFacade, times(1)).addPayments(any());
    }

    @Test
    public void testInvalidPaymentAccountException() throws Exception {
        doThrow(new InvalidPaymentAccountException()).when(paymentFacade).addPayments(any());

        mockMvc.perform(post("/v1/payments")
                .contentType("application/json")
                .content("[{" +
                        "\"account_id\": 999," +
                        "\"amount\": 123.45" +
                        "}]"))
                .andExpect(status().isBadRequest());

        verify(paymentFacade, times(1)).addPayments(any());
    }

}