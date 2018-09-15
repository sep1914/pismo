package com.sep1914.pismo.entity;

import javax.persistence.*;

@Entity
@Table(name = "OperationTypes")
public class OperationType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "OperationType_ID")
    private long id;

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "ChargeOrder")
    private int chargeOrder;

    public OperationType() {
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
