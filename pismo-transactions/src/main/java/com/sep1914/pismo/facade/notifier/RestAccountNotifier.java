package com.sep1914.pismo.facade.notifier;

import com.sep1914.pismo.dto.AccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Qualifier("rest-notifier")
public class RestAccountNotifier implements AccountNotifier {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${accountsAPI.url}")
    private String accountsAPI;

    private final Logger LOGGER = LoggerFactory.getLogger(RestAccountNotifier.class);

    @Override
    public void notifyAccounts(AccountDTO accountDTO, long accountId) {
        String url = accountsAPI + "/v1/accounts/" + accountId;
        LOGGER.debug("Calling {} with {}", url, accountDTO);

        restTemplate.patchForObject(url, accountDTO, AccountDTO.class);
    }

}
