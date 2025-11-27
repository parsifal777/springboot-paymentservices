package com.example.demo.service;

import com.example.demo.model.Payment;
import com.example.demo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    public Payment getPaymentById(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        return payment.orElse(null);
    }

    public List<Payment> getPaymentsByResidentId(Long residentId) {
        return paymentRepository.findByResidentId(residentId);
    }

    public List<Payment> findByResidentName(String residentName) {
        return paymentRepository.findByResidentName(residentName);
    }

    public List<Payment> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return paymentRepository.findByPaymentDateBetween(startDate, endDate);
    }

    public List<Payment> findByResidentNameAndAmountGreaterThanEqual(String residentName, Double minAmount) {
        return paymentRepository.findByResidentNameAndAmountGreaterThanEqual(residentName, minAmount);
    }

}
