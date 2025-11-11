package com.example.demo.restController;

import com.example.demo.model.Payment;
import com.example.demo.model.Resident;
import com.example.demo.service.PaymentService;
import com.example.demo.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentRestController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ResidentService residentService;

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        try {
            List<Payment> payments = paymentService.getAllPayments();
            return ResponseEntity.ok(payments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        try {
            Payment payment = paymentService.getPaymentById(id);
            if (payment != null) {
                return ResponseEntity.ok(payment);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody PaymentCreateRequest paymentRequest) {
        try {
            if (paymentRequest.getResidentId() == null ||
                    paymentRequest.getAmount() == null ||
                    paymentRequest.getAmount() <= 0) {
                return ResponseEntity.badRequest().build();
            }

            Resident resident = residentService.getResidentById(paymentRequest.getResidentId());
            if (resident == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }

            Payment payment = new Payment();
            payment.setResident(resident);
            payment.setAmount(paymentRequest.getAmount());
            payment.setPaymentDate(LocalDateTime.now());

            Payment savedPayment = paymentService.savePayment(payment);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPayment);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id,
                                                 @RequestBody PaymentUpdateRequest paymentRequest) {
        try {
            Payment existingPayment = paymentService.getPaymentById(id);
            if (existingPayment == null) {
                return ResponseEntity.notFound().build();
            }

            if (paymentRequest.getAmount() != null && paymentRequest.getAmount() > 0) {
                existingPayment.setAmount(paymentRequest.getAmount());
            }

            if (paymentRequest.getResidentId() != null) {
                Resident resident = residentService.getResidentById(paymentRequest.getResidentId());
                if (resident != null) {
                    existingPayment.setResident(resident);
                }
            }

            Payment updatedPayment = paymentService.savePayment(existingPayment);
            return ResponseEntity.ok(updatedPayment);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        try {
            Payment payment = paymentService.getPaymentById(id);
            if (payment != null) {
                paymentService.deletePayment(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(path = "/resident/{residentId}")
    public ResponseEntity<List<Payment>> getPaymentsByResident(@PathVariable Long residentId) {
        try {
            List<Payment> payments = paymentService.getPaymentsByResidentId(residentId);
            return ResponseEntity.ok(payments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(path = "/date/{date}")
    public ResponseEntity<List<Payment>> getPaymentsByDate(@PathVariable String date) {
        try {
            LocalDateTime startDate = LocalDateTime.parse(date + "T00:00:00");
            LocalDateTime endDate = LocalDateTime.parse(date + "T23:59:59");
            List<Payment> payments = paymentService.getAllPayments();
            return ResponseEntity.ok(payments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public static class PaymentCreateRequest {
        private Long residentId;
        private Double amount;

        public Long getResidentId() { return residentId; }
        public void setResidentId(Long residentId) { this.residentId = residentId; }
        public Double getAmount() { return amount; }
        public void setAmount(Double amount) { this.amount = amount; }
    }

    public static class PaymentUpdateRequest {
        private Long residentId;
        private Double amount;

        public Long getResidentId() { return residentId; }
        public void setResidentId(Long residentId) { this.residentId = residentId; }
        public Double getAmount() { return amount; }
        public void setAmount(Double amount) { this.amount = amount; }
    }
}
