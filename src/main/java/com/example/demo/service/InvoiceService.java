package com.example.demo.service;

import com.example.demo.model.Invoice;
import com.example.demo.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    public List<Invoice> getInvoicesByResidentId(Long residentId) {
        return invoiceRepository.findByResidentId(residentId);
    }

    public List<Invoice> findByPeriod(LocalDate period) {
        return invoiceRepository.findByPeriod(period);
    }
}