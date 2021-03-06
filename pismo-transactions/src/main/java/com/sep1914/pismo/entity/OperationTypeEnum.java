package com.sep1914.pismo.entity;

public enum OperationTypeEnum {

    FULL_PAYMENT(1L),
    INSTALLMENT_PAYMENT(2L),
    WITHDRAWAL(3L),
    PAYMENT(4L);

    private final Long operationId;

    OperationTypeEnum(Long operationId) {
        this.operationId = operationId;
    }

    public Long getOperationId() {
        return operationId;
    }

    public static OperationTypeEnum map(long id) {
        for (OperationTypeEnum type : values()) {
            if (type.getOperationId() == id) {
                return type;
            }
        }

        throw new IllegalArgumentException();
    }

}
