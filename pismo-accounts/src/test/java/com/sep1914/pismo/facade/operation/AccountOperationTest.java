package com.sep1914.pismo.facade.operation;

import com.sep1914.pismo.dto.AccountDTO;
import com.sep1914.pismo.dto.AvailableCreditLimitDTO;
import com.sep1914.pismo.dto.AvailableWithdrawalLimitDTO;
import com.sep1914.pismo.entity.Account;
import com.sep1914.pismo.facade.exception.InvalidLimitUpdateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class AccountOperationTest {

    private AccountOperation accountOperation;

    @Before
    public void before() {
        this.accountOperation = new AccountOperation();
    }

    @Test
    public void testAddNothingToZeroedLimits() {
        Account account = new Account(ZERO, ZERO);
        AccountDTO accountDTO = new AccountDTO(
                new AvailableCreditLimitDTO(ZERO),
                new AvailableWithdrawalLimitDTO(ZERO));

        accountOperation.updateLimits(account, accountDTO);

        assertLimits(account, ZERO, ZERO);
    }

    @Test
    public void testAddNothingToAlreadySetLimits() {
        Account account = new Account(BigDecimal.valueOf(500.0), BigDecimal.valueOf(200.0));
        AccountDTO accountDTO = new AccountDTO(
                new AvailableCreditLimitDTO(ZERO),
                new AvailableWithdrawalLimitDTO(ZERO));

        accountOperation.updateLimits(account, accountDTO);

        assertLimits(account, BigDecimal.valueOf(500.0), BigDecimal.valueOf(200.0));
    }

    @Test
    public void testAddToExistingLimits() {
        Account account = new Account(BigDecimal.valueOf(500.0), BigDecimal.valueOf(200.0));
        AccountDTO accountDTO = new AccountDTO(
                new AvailableCreditLimitDTO(BigDecimal.valueOf(123.45)),
                new AvailableWithdrawalLimitDTO(BigDecimal.valueOf(432.10)));

        accountOperation.updateLimits(account, accountDTO);

        assertLimits(account, BigDecimal.valueOf(623.45), BigDecimal.valueOf(632.10));
    }

    @Test
    public void testSubtractOfExistingLimits() {
        Account account = new Account(BigDecimal.valueOf(500.0), BigDecimal.valueOf(900.0));
        AccountDTO accountDTO = new AccountDTO(
                new AvailableCreditLimitDTO(BigDecimal.valueOf(-123.45)),
                new AvailableWithdrawalLimitDTO(BigDecimal.valueOf(-432.10)));

        accountOperation.updateLimits(account, accountDTO);

        assertLimits(account, BigDecimal.valueOf(376.55), BigDecimal.valueOf(467.90));
    }

    @Test(expected = InvalidLimitUpdateException.class)
    public void testSubtractOverLimits() {
        Account account = new Account(BigDecimal.valueOf(500.0), BigDecimal.valueOf(300.0));
        AccountDTO accountDTO = new AccountDTO(
                new AvailableCreditLimitDTO(BigDecimal.valueOf(-600.00)),
                new AvailableWithdrawalLimitDTO(BigDecimal.valueOf(-100.0)));

        accountOperation.updateLimits(account, accountDTO);
    }

    private void assertLimits(Account account, BigDecimal creditLimit, BigDecimal withdrawalLimit) {
        assertEquals(creditLimit, account.getAvailableCreditLimit());
        assertEquals(withdrawalLimit, account.getAvailableWithdrawalLimit());
    }

}