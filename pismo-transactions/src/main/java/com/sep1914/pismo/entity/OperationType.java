package com.sep1914.pismo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OperationsTypes")
public class OperationType {

    @Id
    @Column(name = "OperationType_ID")
    private long id;

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "ChargeOrder")
    private int chargeOrder;

    public OperationType() {
    }

    public OperationType(long id, String description, int chargeOrder) {
        this.id = id;
        this.description = description;
        this.chargeOrder = chargeOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getChargeOrder() {
        return chargeOrder;
    }

    public void setChargeOrder(int chargeOrder) {
        this.chargeOrder = chargeOrder;
    }

    public long getId() {
        return id;
    }
}
