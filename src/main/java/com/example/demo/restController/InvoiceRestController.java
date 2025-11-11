package com.example.demo.restController;

import com.example.demo.model.Invoice;
import com.example.demo.model.Resident;
import com.example.demo.service.InvoiceService;
import com.example.demo.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = "*")
public class InvoiceRestController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ResidentService residentService;

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        try {
            List<Invoice> invoices = invoiceService.getAllInvoices();
            return ResponseEntity.ok(invoices);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        try {
            Invoice invoice = invoiceService.getInvoiceById(id);
            if (invoice != null) {
                return ResponseEntity.ok(invoice);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        try {
            if (invoiceRequest.getResidentId() == null ||
                    invoiceRequest.getPeriod() == null ||
                    invoiceRequest.getAmount() == null ||
                    invoiceRequest.getAmount() <= 0) {
                return ResponseEntity.badRequest().build();
            }

            Resident resident = residentService.getResidentById(invoiceRequest.getResidentId());
            if (resident == null) {
                return ResponseEntity.notFound().build();
            }

            Invoice invoice = new Invoice();
            invoice.setResident(resident);
            invoice.setPeriod(LocalDate.parse(invoiceRequest.getPeriod()));
            invoice.setAmount(invoiceRequest.getAmount());

            Invoice savedInvoice = invoiceService.saveInvoice(invoice);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedInvoice);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id,
                                                 @RequestBody InvoiceRequest invoiceRequest) {
        try {
            Invoice existingInvoice = invoiceService.getInvoiceById(id);
            if (existingInvoice != null) {

                if (invoiceRequest.getPeriod() != null) {
                    existingInvoice.setPeriod(LocalDate.parse(invoiceRequest.getPeriod()));
                }

                if (invoiceRequest.getAmount() != null && invoiceRequest.getAmount() > 0) {
                    existingInvoice.setAmount(invoiceRequest.getAmount());
                }

                if (invoiceRequest.getResidentId() != null) {
                    Resident resident = residentService.getResidentById(invoiceRequest.getResidentId());
                    if (resident != null) {
                        existingInvoice.setResident(resident);
                    }
                }

                Invoice updatedInvoice = invoiceService.saveInvoice(existingInvoice);
                return ResponseEntity.ok(updatedInvoice);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        try {
            Invoice invoice = invoiceService.getInvoiceById(id);
            if (invoice != null) {
                invoiceService.deleteInvoice(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(path = "/resident/{residentId}")
    public ResponseEntity<List<Invoice>> getInvoicesByResident(@PathVariable Long residentId) {
        try {
            List<Invoice> invoices = invoiceService.getInvoicesByResidentId(residentId);
            return ResponseEntity.ok(invoices);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(path = "/period/{period}")
    public ResponseEntity<List<Invoice>> getInvoicesByPeriod(@PathVariable String period) {
        try {
            LocalDate periodDate = LocalDate.parse(period);
            List<Invoice> invoices = invoiceService.findByPeriod(periodDate);
            return ResponseEntity.ok(invoices);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public static class InvoiceRequest {
        private Long residentId;
        private String period;
        private Double amount;

        public Long getResidentId() { return residentId; }
        public void setResidentId(Long residentId) { this.residentId = residentId; }
        public String getPeriod() { return period; }
        public void setPeriod(String period) { this.period = period; }
        public Double getAmount() { return amount; }
        public void setAmount(Double amount) { this.amount = amount; }
    }
}
