package com.example.demo.repository;

import com.example.demo.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByResidentId(Long residentId);

    @Query("SELECT p FROM Payment p WHERE p.resident.fullName LIKE %:residentName%")
    List<Payment> findByResidentName(@Param("residentName") String residentName);

    @Query("SELECT p FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate")
    List<Payment> findByPaymentDateBetween(@Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);

    @Query("SELECT p FROM Payment p WHERE p.resident.fullName LIKE %:residentName% AND p.amount >= :minAmount")
    List<Payment> findByResidentNameAndAmountGreaterThanEqual(@Param("residentName") String residentName,
                                                              @Param("minAmount") Double minAmount);
}
