package com.sep1914.pismo.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Transaction_ID")
    private long id;

    @Column(name = "Account_ID")
    private long accountId;

    @ManyToOne
    @JoinColumn(name = "OperationType_ID", nullable = false)
    private OperationType operationType;

    @Column(name = "Amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "Balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "EventDate", nullable = false)
    private LocalDate eventDate;

    @Column(name = "DueDate", nullable = false)
    private LocalDate dueDate;

    public Transaction() {
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

}
