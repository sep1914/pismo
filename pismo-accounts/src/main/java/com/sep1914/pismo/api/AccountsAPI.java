package com.sep1914.pismo.api;

import com.sep1914.pismo.dto.AccountDTO;
import com.sep1914.pismo.facade.AccountFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/accounts")
public class AccountsAPI {

    @Autowired
    private AccountFacade accountFacade;

    private final Logger LOGGER = LoggerFactory.getLogger(AccountsAPI.class);

    @PatchMapping("/{accountId}")
    public ResponseEntity<?> updateAccount(@PathVariable long accountId,
                              @RequestBody AccountDTO accountDTO) {
        LOGGER.info("Received update account {} request: {}", accountId, accountDTO);

        accountFacade.updateAccount(accountId, accountDTO);

        LOGGER.info("Account {} updated successfully", accountId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/limits")
    public ResponseEntity<?> listAccounts() {
        LOGGER.info("Listing accounts limits");

        return ResponseEntity.ok(accountFacade.listAll());
    }

}
