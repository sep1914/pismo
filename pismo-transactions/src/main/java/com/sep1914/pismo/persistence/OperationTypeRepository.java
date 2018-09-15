package com.sep1914.pismo.persistence;

import com.sep1914.pismo.entity.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationTypeRepository extends JpaRepository<OperationType, Long> {
}
