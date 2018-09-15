package com.sep1914.pismo.persistence;

import com.sep1914.pismo.entity.PaymentTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTrackingRepository extends JpaRepository<PaymentTracking, Long> {
}
