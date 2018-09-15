package com.sep1914.pismo.facade;

import com.sep1914.pismo.dto.AccountDTO;
import com.sep1914.pismo.entity.Account;
import com.sep1914.pismo.facade.exception.InvalidLimitUpdateException;
import com.sep1914.pismo.persistence.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountFacadeTest {

    @Autowired
    private AccountFacade accountFacade;

    @Autowired
    private AccountRepository accountRepository;

    private final double[] creditLimits = new double[]{ 100.00, 200.00, 200.00 };

    private final double[] withdrawalLimits = new double[]{ 20.00, 40.00, 50.00 };

    @Before
    public void before() {
        createAccounts();
    }

    @Test
    @Transactional
    public void testAddUpdateAccount() {
        AccountDTO accountDTO = new AccountDTO(
                BigDecimal.valueOf(200.00).setScale(2),
                BigDecimal.valueOf(100.00).setScale(2));
        accountFacade.updateAccount(1L, accountDTO);

        assertNewLimitsOnAccount(accountRepository.findById(1L).get(), 300.00, 120.00);
    }

    @Test
    @Transactional
    public void testSubtractUpdateAccount() {
        AccountDTO accountDTO = new AccountDTO(
                BigDecimal.valueOf(-10.00).setScale(2),
                BigDecimal.valueOf(-5.00).setScale(2));
        accountFacade.updateAccount(1L, accountDTO);

        assertNewLimitsOnAccount(accountRepository.findById(1L).get(), 90.00, 15.00);
    }

    @Test(expected = InvalidLimitUpdateException.class)
    @Transactional
    public void testSubtractInvalidUpdateAccount() {
        AccountDTO accountDTO = new AccountDTO(
                BigDecimal.valueOf(-10000.00),
                BigDecimal.valueOf(-50000.00));
        accountFacade.updateAccount(1L, accountDTO);
    }

    @Test
    public void testListAll() {
        List<Account> accounts = new ArrayList<>(accountFacade.listAll());
        assertEquals(creditLimits.length, accounts.size());

        for (int i = 0; i < accounts.size(); i++) {
            assertNewLimitsOnAccount(accounts.get(i), creditLimits[i], withdrawalLimits[i]);
        }
    }

    private void assertNewLimitsOnAccount(Account account, double v, double v2) {
        assertEquals(BigDecimal.valueOf(v).setScale(2), account.getAvailableCreditLimit());
        assertEquals(BigDecimal.valueOf(v2).setScale(2), account.getAvailableWithdrawalLimit());
    }

    private void createAccounts() {
        for (int i = 0; i < creditLimits.length; i++) {
            Account account = new Account(
                    BigDecimal.valueOf(creditLimits[i]),
                    BigDecimal.valueOf(withdrawalLimits[i]));

            accountRepository.save(account);
        }
    }

}