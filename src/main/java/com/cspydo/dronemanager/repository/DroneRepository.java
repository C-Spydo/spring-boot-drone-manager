package com.cspydo.dronemanager.repository;

import java.util.List;

import com.cspydo.dronemanager.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DroneRepository extends JpaRepository<Drone, Long> {

    @Query("SELECT d.batteryCapacity FROM Drone d WHERE d.id=?1")
    int getBatteryLevel(long drone_id);

    @Query("SELECT d FROM Drone d WHERE d.state = ?1")
    List<Drone> getAvailable(String idle);
}

