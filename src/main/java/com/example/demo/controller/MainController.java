package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class MainController {

    @Autowired
    private ResidentService residentService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("residents", residentService.getAllResidents());
        model.addAttribute("invoices", invoiceService.getAllInvoices());
        model.addAttribute("payments", paymentService.getAllPayments());
        return "index";
    }

    @GetMapping("/residents")
    public String showResidents(Model model) {
        model.addAttribute("residents", residentService.getAllResidents());
        model.addAttribute("newResident", new Resident());
        return "residents";
    }

    @PostMapping("/residents/add")
    public String addResident(@ModelAttribute Resident resident) {
        residentService.saveResident(resident);
        return "redirect:/residents";
    }

    @GetMapping("/invoices")
    public String showInvoices(Model model) {
        model.addAttribute("invoices", invoiceService.getAllInvoices());
        model.addAttribute("residents", residentService.getAllResidents());
        model.addAttribute("newInvoice", new Invoice());
        return "invoices";
    }

    @PostMapping("/invoices/add")
    public String addInvoice(@RequestParam Long residentId,
                             @RequestParam String period,
                             @RequestParam Double amount) {
        Resident resident = residentService.getResidentById(residentId);
        Invoice invoice = new Invoice();
        invoice.setResident(resident);
        invoice.setPeriod(LocalDate.parse(period));
        invoice.setAmount(amount);
        invoiceService.saveInvoice(invoice);
        return "redirect:/invoices";
    }

    @GetMapping("/payments")
    public String showPayments(Model model) {
        model.addAttribute("payments", paymentService.getAllPayments());
        model.addAttribute("residents", residentService.getAllResidents());
        model.addAttribute("newPayment", new Payment());
        return "payments";
    }

    @PostMapping("/payments/add")
    public String addPayment(@RequestParam Long residentId,
                             @RequestParam Double amount) {
        Resident resident = residentService.getResidentById(residentId);
        Payment payment = new Payment();
        payment.setResident(resident);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setAmount(amount);
        paymentService.savePayment(payment);
        return "redirect:/payments";
    }
}