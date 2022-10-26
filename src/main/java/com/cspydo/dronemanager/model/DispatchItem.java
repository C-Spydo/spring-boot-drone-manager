package com.cspydo.dronemanager.model;
import javax.persistence.*;

import javax.validation.constraints.*;

@Entity
@Table(name = "dispatch_items")
public class DispatchItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "dispatchId")
    private long dispatchId;

    @Column(name = "medicationId")
    private long medicationId;

    public DispatchItem() {

    }

    public DispatchItem(long dispatchId, long medicationId) {
        this.dispatchId = dispatchId;
        this.medicationId = medicationId;
    }

    public long getId() {
        return id;
    }

    public long getDispatch() {
        return dispatchId;
    }

    public void setDispatch(int dispatchId) {
        this.dispatchId = dispatchId;
    }

    public long getMedication() {
        return medicationId;
    }

    public void setMedication(int medicationId) {
        this.medicationId = medicationId;
    }

}
