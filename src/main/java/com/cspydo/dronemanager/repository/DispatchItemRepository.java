package com.cspydo.dronemanager.repository;

import java.util.List;

import com.cspydo.dronemanager.model.DispatchItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DispatchItemRepository extends JpaRepository<DispatchItem, Long> {

    @Query("SELECT d FROM DispatchItem d WHERE d.dispatchId = ?1")
    List<DispatchItem> getDispatchItems(long dispatchId);
}

