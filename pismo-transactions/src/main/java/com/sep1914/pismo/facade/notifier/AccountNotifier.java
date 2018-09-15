package com.sep1914.pismo.facade.notifier;

import com.sep1914.pismo.dto.AccountDTO;

public interface AccountNotifier {

    void notifyAccounts(AccountDTO accountDTO, long accountId);

}
