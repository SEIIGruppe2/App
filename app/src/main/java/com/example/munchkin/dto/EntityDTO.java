package com.example.munchkin.dto;

public class EntityDTO {
    protected String name;
    protected int zone;

    protected int id;

    // No-argument constructor
    public EntityDTO() {
    }

    // All-argument constructor for common fields
    public EntityDTO(String name, int zone, int id) {
        this.name = name;
        this.zone = zone;
        this.id = id;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
