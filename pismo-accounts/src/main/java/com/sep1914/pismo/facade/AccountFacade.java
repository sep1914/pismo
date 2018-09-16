package com.sep1914.pismo.facade;

import com.sep1914.pismo.dto.AccountDTO;
import com.sep1914.pismo.entity.Account;
import com.sep1914.pismo.facade.operation.AccountOperation;
import com.sep1914.pismo.persistence.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Component
public class AccountFacade {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountOperation accountOperation;

    public AccountDTO updateAccount(long accountId, AccountDTO accountDTO) {
        Account newAccount = new Account(ZERO, ZERO);
        newAccount.setId(accountId);

        Account account = accountRepository.findById(accountId).orElse(newAccount);

        accountOperation.updateLimits(account, accountDTO);
        Account savedAccount = accountRepository.save(account);

        return new AccountDTO(savedAccount);
    }

    public List<AccountDTO> listAll() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountDTO> accountDTOs = new ArrayList<>(accounts.size());

        accounts.forEach(a -> accountDTOs.add(new AccountDTO(a)));
        return accountDTOs;
    }

}
