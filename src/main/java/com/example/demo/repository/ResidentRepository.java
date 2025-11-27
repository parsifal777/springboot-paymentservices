package com.example.demo.repository;

import com.example.demo.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {
    List<Resident> findByFullNameContainingIgnoreCase(String fullName);

    @Query("SELECT r FROM Resident r WHERE r.fullName LIKE %:name%")
    List<Resident> findAllByNameContaining(@Param("name") String name);

    @Query("SELECT r FROM Resident r WHERE r.fullName LIKE %:name% AND r.address LIKE %:address%")
    List<Resident> findByNameAndAddress(@Param("name") String name, @Param("address") String address);
}
