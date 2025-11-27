package com.example.demo.service;

import com.example.demo.model.Invoice;
import com.example.demo.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private StatisticsService statisticsService;

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Transactional
    public Invoice saveInvoice(Invoice invoice) {
        boolean isNew = invoice.getId() == null;
        Invoice savedInvoice = invoiceRepository.save(invoice);

        if (isNew) {
            statisticsService.recordEvent("INVOICES", "ADD");
        } else {
            statisticsService.recordEvent("INVOICES", "UPDATE");
        }

        return savedInvoice;
    }

    @Transactional
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
        statisticsService.recordEvent("INVOICES", "DELETE");
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

    public List<Invoice> findByResidentName(String residentName) {
        return invoiceRepository.findByResidentName(residentName);
    }

    public List<Invoice> findByResidentIdAndPeriod(Long residentId, LocalDate period) {
        return invoiceRepository.findByResidentIdAndPeriod(residentId, period);
    }

    public List<Invoice> findByResidentNameAndAmountGreaterThan(String residentName, Double minAmount) {
        return invoiceRepository.findByResidentNameAndAmountGreaterThan(residentName, minAmount);
    }

    public boolean hasInvoicesForResident(Long residentId) {
        return !invoiceRepository.findByResidentId(residentId).isEmpty();
    }
}
