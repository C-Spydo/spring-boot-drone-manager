package com.cspydo.dronemanager.repository;

import com.cspydo.dronemanager.model.Medication;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

}

