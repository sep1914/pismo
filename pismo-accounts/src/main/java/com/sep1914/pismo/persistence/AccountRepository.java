package com.sep1914.pismo.persistence;

import com.sep1914.pismo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
