package com.sep1914.pismo.facade.operation;

import com.sep1914.pismo.dto.AccountDTO;
import com.sep1914.pismo.entity.Account;
import com.sep1914.pismo.facade.exception.InvalidLimitUpdateException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountOperation {

    public void updateLimits(Account account, AccountDTO accountDTO) {
        updateCreditLimit(account, accountDTO);
        updateWithdrawalLimit(account, accountDTO);
    }

    private void updateWithdrawalLimit(Account account, AccountDTO accountDTO) {
        BigDecimal newWithdrawalLimit = account.getAvailableWithdrawalLimit()
                .add(accountDTO.getAvailableWithdrawalLimit().getAmount());

        if (newWithdrawalLimit.signum() < 0) {
            throw new InvalidLimitUpdateException();
        }

        account.setAvailableWithdrawalLimit(newWithdrawalLimit);
    }

    private void updateCreditLimit(Account account, AccountDTO accountDTO) {
        BigDecimal newCreditLimit = account.getAvailableCreditLimit()
                .add(accountDTO.getAvailableCreditLimit().getAmount());

        if (newCreditLimit.signum() < 0) {
            throw new InvalidLimitUpdateException();
        }

        account.setAvailableCreditLimit(newCreditLimit);
    }

}
