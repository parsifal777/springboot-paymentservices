package com.example.demo.repository;

import com.example.demo.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByResidentId(Long residentId);
    List<Invoice> findByPeriod(LocalDate period);
}