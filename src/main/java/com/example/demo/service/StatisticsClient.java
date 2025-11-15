package com.example.demo.service;

import com.example.demo.model.StatisticsEvent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "statistics-server", url = "http://localhost:8081")
public interface StatisticsClient {
    @PostMapping("/api/statistics/event")
    String recordEvent(StatisticsEvent event);
}