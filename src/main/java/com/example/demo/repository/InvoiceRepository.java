package com.example.demo.repository;

import com.example.demo.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByResidentId(Long residentId);
    List<Invoice> findByPeriod(LocalDate period);

    @Query("SELECT i FROM Invoice i WHERE i.resident.fullName LIKE %:residentName%")
    List<Invoice> findByResidentName(@Param("residentName") String residentName);

    @Query("SELECT i FROM Invoice i WHERE i.resident.id = :residentId AND i.period = :period")
    List<Invoice> findByResidentIdAndPeriod(@Param("residentId") Long residentId,
                                            @Param("period") LocalDate period);

    @Query("SELECT i FROM Invoice i WHERE i.resident.fullName LIKE %:residentName% AND i.amount > :minAmount")
    List<Invoice> findByResidentNameAndAmountGreaterThan(@Param("residentName") String residentName,
                                                         @Param("minAmount") Double minAmount);
}
