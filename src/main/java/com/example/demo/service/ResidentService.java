package com.example.demo.service;

import com.example.demo.model.Resident;
import com.example.demo.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResidentService {

    @Autowired
    private ResidentRepository residentRepository;

    public List<Resident> getAllResidents() {
        return residentRepository.findAll();
    }

    public Resident saveResident(Resident resident) {
        return residentRepository.save(resident);
    }

    public Resident getResidentById(Long id) {
        return residentRepository.findById(id).orElse(null);
    }

    public void deleteResident(Long id) {
        residentRepository.deleteById(id);
    }

    public List<Resident> findByFullNameContaining(String name) {
        return residentRepository.findByFullNameContainingIgnoreCase(name);
    }
}