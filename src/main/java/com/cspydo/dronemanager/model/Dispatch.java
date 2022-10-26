package com.cspydo.dronemanager.model;
import javax.persistence.*;

import javax.validation.constraints.*;

@Entity
@Table(name = "dispatches")
public class Dispatch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "droneId")
    private int droneId;

    @Column(name = "status")
    private String status;


    public Dispatch() {

    }

    public Dispatch(int droneId, String status) {
        this.droneId = droneId;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public int getDrone() {
        return droneId;
    }

    public void setDrone(int droneId) {
        this.droneId = droneId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
