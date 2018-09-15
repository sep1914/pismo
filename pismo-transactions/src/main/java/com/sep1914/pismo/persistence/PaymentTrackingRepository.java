package com.sep1914.pismo.persistence;

import com.sep1914.pismo.entity.PaymentTracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTrackingRepository extends JpaRepository<PaymentTracking, Long> {
}
