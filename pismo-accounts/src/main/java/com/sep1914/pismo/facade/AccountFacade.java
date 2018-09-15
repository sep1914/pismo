package com.sep1914.pismo.facade;

import com.sep1914.pismo.dto.AccountDTO;
import com.sep1914.pismo.entity.Account;
import com.sep1914.pismo.facade.operation.AccountOperation;
import com.sep1914.pismo.persistence.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountFacade {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountOperation accountOperation;

    public void updateAccount(long accountId, AccountDTO accountDTO) {
        Account account = accountRepository.findById(accountId)
                .orElse(new Account());

        accountOperation.updateLimits(account, accountDTO);
        accountRepository.save(account);
    }

    public List<Account> listAll() {
        return accountRepository.findAll();
    }

}
