package com.example.demo.config;

import com.example.demo.model.Invoice;
import com.example.demo.model.Payment;
import com.example.demo.model.Resident;
import com.example.demo.repository.InvoiceRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public void run(String... args) throws Exception {
        paymentRepository.deleteAll();
        invoiceRepository.deleteAll();
        residentRepository.deleteAll();

        Resident resident1 = residentRepository.save(new Resident(null, "Иванов Иван Иванович", "ул. Ленина, д. 10, кв. 5"));
        Resident resident2 = residentRepository.save(new Resident(null, "Петров Петр Петрович", "ул. Советская, д. 25, кв. 12"));
        Resident resident3 = residentRepository.save(new Resident(null, "Сидорова Мария Васильевна", "ул. Мира, д. 8, кв. 3"));
        Resident resident4 = residentRepository.save(new Resident(null, "Кузнецов Алексей Сергеевич", "ул. Ленина, д. 15, кв. 7"));

        invoiceRepository.save(new Invoice(null, resident1, LocalDate.of(2024, 1, 1), 1500.50));
        invoiceRepository.save(new Invoice(null, resident1, LocalDate.of(2024, 2, 1), 1600.75));
        invoiceRepository.save(new Invoice(null, resident2, LocalDate.of(2024, 1, 1), 1800.00));
        invoiceRepository.save(new Invoice(null, resident3, LocalDate.of(2024, 1, 1), 1200.25));
        invoiceRepository.save(new Invoice(null, resident4, LocalDate.of(2024, 1, 1), 2000.00));
        invoiceRepository.save(new Invoice(null, resident2, LocalDate.of(2024, 2, 1), 1750.30));

        paymentRepository.save(new Payment(null, resident1, LocalDateTime.of(2024, 1, 5, 10, 30), 1500.50));
        paymentRepository.save(new Payment(null, resident2, LocalDateTime.of(2024, 1, 6, 11, 15), 1800.00));
        paymentRepository.save(new Payment(null, resident3, LocalDateTime.of(2024, 1, 7, 14, 20), 1200.25));
        paymentRepository.save(new Payment(null, resident1, LocalDateTime.of(2024, 2, 3, 9, 45), 1600.75));
        paymentRepository.save(new Payment(null, resident4, LocalDateTime.of(2024, 1, 8, 16, 30), 2000.00));

        System.out.println("Тестовые данные успешно загружены!");
        System.out.println("Жильцов: " + residentRepository.count());
        System.out.println("Счетов: " + invoiceRepository.count());
        System.out.println("Платежей: " + paymentRepository.count());
    }
}
