package com.sep1914.pismo.persistence;

import com.sep1914.pismo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select t from Transaction t " +
            "where accountId = ?1 and balance < 0 " +
            "order by t.operationType.chargeOrder asc, eventDate asc, balance asc")
    public List<Transaction> findByAccountId(long accountId);

}
