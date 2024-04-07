package com.example.munchkin.DTO;

public class MonsterDTO extends EntityDTO {
    private int ring;
    private int lifePoints;

    // No-argument constructor
    public MonsterDTO() {
        super(); // Calls the superclass's no-argument constructor
    }

    // Constructor including inherited fields
    public MonsterDTO(String name, int zone, int ring, int lifePoints, int id) {
        super(name, zone, id);
        this.ring = ring;
        this.lifePoints = lifePoints;
    }

    // Additional getters and setters for new fields
    public int getRing() {
        return ring;
    }

    public void setRing(int ring) {
        this.ring = ring;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }
}
