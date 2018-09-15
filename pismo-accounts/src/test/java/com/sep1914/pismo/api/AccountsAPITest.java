package com.sep1914.pismo.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sep1914.pismo.dto.AccountDTO;
import com.sep1914.pismo.facade.AccountFacade;
import com.sep1914.pismo.facade.exception.InvalidLimitUpdateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountsAPI.class)
public class AccountsAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountFacade accountFacade;

    @Before
    public void before() {
        doThrow(new RuntimeException()).when(accountFacade).updateAccount(anyLong(), any());
    }

    @Test
    public void testSuccessfulUpdateAccount() throws Exception {
        doNothing().when(accountFacade).updateAccount(eq(123L), any());

        mockMvc.perform(patch("/v1/accounts/123")
                .contentType("application/json")
                .content("{ " +
                        "\"available_credit_limit\" : { \"amount\": 123.45 }," +
                        "\"available_withdrawal_limit\" : { \"amount\": 432.10 }" +
                        "}"))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testUnsuccessfulUpdateAccount() throws Exception {
        doThrow(new InvalidLimitUpdateException()).when(accountFacade).updateAccount(eq(42L), any());

        mockMvc.perform(patch("/v1/accounts/42")
                .contentType("application/json")
                .content("{ " +
                        "\"available_credit_limit\" : { \"amount\": -1000.00 }," +
                        "\"available_withdrawal_limit\" : { \"amount\": -500.00 }" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testListAccounts() throws Exception {
        when(accountFacade.listAll()).thenReturn(buildDTOsList());

        MvcResult mvcResult = mockMvc.perform(get("/v1/accounts/limits")).andReturn();
        List<AccountDTO> accountDTOs = new ArrayList<>(deserializeAnswer(mvcResult));

        assertDTOLimits(accountDTOs.get(0), 100.00, 200.00);
        assertDTOLimits(accountDTOs.get(1), 300.00, 400.00);
    }

    private void assertDTOLimits(AccountDTO accountDTO, double v, double v2) {
        assertEquals(BigDecimal.valueOf(v).setScale(2), accountDTO.getAvailableCreditLimit().getAmount());
        assertEquals(BigDecimal.valueOf(v2).setScale(2), accountDTO.getAvailableWithdrawalLimit().getAmount());
    }

    private List<AccountDTO> buildDTOsList() {
        return List.of(
                new AccountDTO(BigDecimal.valueOf(100.00).setScale(2), BigDecimal.valueOf(200.0).setScale(2)),
                new AccountDTO(BigDecimal.valueOf(300.00).setScale(2), BigDecimal.valueOf(400.0).setScale(2)));
    }

    private List<AccountDTO> deserializeAnswer(MvcResult mvcResult) throws IOException {
        return new ObjectMapper()
                .findAndRegisterModules()
                .readValue(mvcResult.getResponse().getContentAsByteArray(),
                        new TypeReference<List<AccountDTO>>(){});
    }

}