package com.sep1914.pismo.util;

import com.sep1914.pismo.entity.OperationType;
import com.sep1914.pismo.persistence.OperationTypeRepository;

public class OperationTypeTestUtil {

    public static void initializeOperationTypes(OperationTypeRepository operationTypeRepository) {
        operationTypeRepository.save(new OperationType(1, "COMPRA A VISTA", 2));
        operationTypeRepository.save(new OperationType(2, "COMPRA PARCELADA", 1));
        operationTypeRepository.save(new OperationType(3, "SAQUE", 0));
        operationTypeRepository.save(new OperationType(4, "PAGAMENTO", 0));
    }

}
