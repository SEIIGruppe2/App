package com.example.munchkin.DTO;

public class EntityDTO {
    protected String name;
    protected int zone;

    // No-argument constructor
    public EntityDTO() {
    }

    // All-argument constructor for common fields
    public EntityDTO(String name, int zone) {
        this.name = name;
        this.zone = zone;
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
}
