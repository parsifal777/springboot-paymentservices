package com.example.demo.repository;

import com.example.demo.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {
    List<Resident> findByFullNameContainingIgnoreCase(String fullName);
}