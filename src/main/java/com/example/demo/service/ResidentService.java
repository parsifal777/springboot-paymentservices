package com.example.demo.service;

import com.example.demo.model.Resident;
import com.example.demo.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResidentService {

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private StatisticsService statisticsService;

    public List<Resident> getAllResidents() {
        return residentRepository.findAll();
    }

    @Transactional
    public Resident saveResident(Resident resident) {
        boolean isNew = resident.getId() == null;
        Resident savedResident = residentRepository.save(resident);

        if (isNew) {
            statisticsService.recordEvent("RESIDENTS", "ADD");
        } else {
            statisticsService.recordEvent("RESIDENTS", "UPDATE");
        }

        return savedResident;
    }

    public Resident getResidentById(Long id) {
        return residentRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteResident(Long id) {
        residentRepository.deleteById(id);
        statisticsService.recordEvent("RESIDENTS", "DELETE");
    }

    public List<Resident> findByFullNameContaining(String name) {
        return residentRepository.findByFullNameContainingIgnoreCase(name);
    }
}