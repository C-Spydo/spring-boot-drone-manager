package com.cspydo.dronemanager.repository;

import com.cspydo.dronemanager.model.Dispatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DispatchRepository extends JpaRepository<Dispatch, Long> {
    @Query("SELECT d FROM Dispatch d WHERE d.droneId = ?1")
    Dispatch getDispatchOnDrone(int droneId);
}

