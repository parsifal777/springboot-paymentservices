package com.example.demo.service;

import com.example.demo.model.StatisticsEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class StatisticsService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StatisticsClient statisticsClient;

    private final String statisticsServerUrl = "http://localhost:8081";

    public void recordEvent(String directoryName, String action) {
        StatisticsEvent event = new StatisticsEvent(directoryName, action);

        if ("RESIDENTS".equals(directoryName)) {
            sendViaRestTemplate(event);
        } else if ("INVOICES".equals(directoryName)) {
            sendViaOpenFeign(event);
        }
    }

    // Новый метод для получения статистики
    public Map<String, Object> getAllStatistics() {
        try {
            Map statistics = restTemplate.getForObject(
                    statisticsServerUrl + "/api/statistics", Map.class);
            return statistics;
        } catch (Exception e) {
            System.err.println("Error getting statistics: " + e.getMessage());
            return null;
        }
    }

    private void sendViaRestTemplate(StatisticsEvent event) {
        try {
            String response = restTemplate.postForObject(
                    statisticsServerUrl + "/api/statistics/event",
                    event, String.class);
            System.out.println("RestTemplate: " + response);
        } catch (Exception e) {
            System.err.println("RestTemplate error: " + e.getMessage());
        }
    }

    private void sendViaOpenFeign(StatisticsEvent event) {
        try {
            String response = statisticsClient.recordEvent(event);
            System.out.println("OpenFeign: " + response);
        } catch (Exception e) {
            System.err.println("OpenFeign error: " + e.getMessage());
        }
    }
}
