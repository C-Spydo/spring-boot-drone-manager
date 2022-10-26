package com.cspydo.dronemanager.model;
import javax.persistence.*;

import javax.validation.constraints.*;

@Entity
@Table(name = "medications")
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "weight")
    private Double weight;

    @Lob
    @Column (name = "image")
    private byte[] image;

    public Medication() {

    }

    public Medication(String name, String code, Double weight, byte[] image) {
        this.name = name;
        this.code = code;
        this.weight = weight;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
