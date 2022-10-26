package com.cspydo.dronemanager.model;
import javax.persistence.*;

import javax.validation.constraints.*;


@Entity
@Table(name = "drones")
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @NotBlank(message = "Serial Number is Required")
    @Size(max=100, message="Serial Number cannot be more than 100 characters")
    @Column(name = "serialNumber")
    private String serialNumber;

    @NotBlank(message = "Model is Required")
//    @Size(min=100, message="Serial Number cannot be more than 100 characters")
    @Column(name = "model")
    private String model;


    @NotNull
    @Max(value=500, message="Weight Limit cannot be more than 500")
    @Column(name = "weightLimit")
    private double weightLimit;

    @NotNull
    @Max(value=100, message="Max Battery Capacity is 100%")
    @Column(name = "batteryCapacity")
    private int batteryCapacity;

    @Column(name = "state")
    private String state;

    public Drone() {

    }

    public Drone(String serialNumber, String model, double weightLimit, int batteryCapacity, String state) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
    }

    public Drone(String serialNumber, String model, double weightLimit, int batteryCapacity) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
    }

    public long getId() {
        return id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state != null ? state : "IDLE";
    }

    public double getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(double weightLimit) {
        this.weightLimit = weightLimit;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }
}
