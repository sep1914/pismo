package com.sep1914.pismo.api;

import com.sep1914.pismo.dto.AccountDTO;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountsAPI {

    @PatchMapping("/{accountId}")
    public void updateAccount(@RequestParam long accountId,
                              @RequestBody AccountDTO accountDTO) {

    }

    @GetMapping("/limits")
    public List<AccountDTO> listAccounts() {
        return Collections.emptyList();
    }

}
