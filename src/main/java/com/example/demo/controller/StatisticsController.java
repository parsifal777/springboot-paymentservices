package com.example.demo.controller;

import com.example.demo.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/statistics")
    public String showStatistics(Model model) {
        try {
            Map<String, Object> statistics = statisticsService.getAllStatistics();
            model.addAttribute("statistics", statistics);
        } catch (Exception e) {
            model.addAttribute("error", "Не удалось загрузить статистику. Убедитесь, что сервер статистики запущен на порту 8081");
        }
        return "statistics";
    }
}
