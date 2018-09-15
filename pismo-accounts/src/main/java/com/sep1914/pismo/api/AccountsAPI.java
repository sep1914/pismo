package com.sep1914.pismo.api;

import com.sep1914.pismo.dto.AccountDTO;
import com.sep1914.pismo.facade.AccountFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/accounts")
public class AccountsAPI {

    @Autowired
    private AccountFacade accountFacade;

    @PatchMapping("/{accountId}")
    public ResponseEntity<?> updateAccount(@RequestParam long accountId,
                              @RequestBody AccountDTO accountDTO) {
        accountFacade.updateAccount(accountId, accountDTO);

        return ResponseEntity.accepted().build();
    }

    @GetMapping("/limits")
    public ResponseEntity<?> listAccounts() {
        return ResponseEntity.ok(accountFacade.listAll());
    }

}
