package com.example.demo.restController;

import com.example.demo.model.Resident;
import com.example.demo.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/residents")
@CrossOrigin(origins = "*")
public class ResidentRestController {

    @Autowired
    private ResidentService residentService;

    @GetMapping
    public ResponseEntity<List<Resident>> getAllResidents() {
        try {
            List<Resident> residents = residentService.getAllResidents();
            return ResponseEntity.ok(residents);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Resident> getResidentById(@PathVariable Long id) {
        try {
            Resident resident = residentService.getResidentById(id);
            if (resident != null) {
                return ResponseEntity.ok(resident);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(path = "")
    public ResponseEntity<Resident> createResident(@RequestBody Resident resident) {
        try {
            if (resident.getFullName() == null || resident.getFullName().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Resident savedResident = residentService.saveResident(resident);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedResident);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Resident> updateResident(@PathVariable Long id,
                                                   @RequestBody Resident residentDetails) {
        try {
            Resident existingResident = residentService.getResidentById(id);
            if (existingResident != null) {
                if (residentDetails.getFullName() != null) {
                    existingResident.setFullName(residentDetails.getFullName());
                }
                if (residentDetails.getAddress() != null) {
                    existingResident.setAddress(residentDetails.getAddress());
                }

                Resident updatedResident = residentService.saveResident(existingResident);
                return ResponseEntity.ok(updatedResident);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteResident(@PathVariable Long id) {
        try {
            Resident resident = residentService.getResidentById(id);
            if (resident != null) {
                residentService.deleteResident(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(path = "/search")
    public ResponseEntity<List<Resident>> searchResidents(@RequestParam String name) {
        try {
            List<Resident> residents = residentService.findByFullNameContaining(name);
            return ResponseEntity.ok(residents);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
