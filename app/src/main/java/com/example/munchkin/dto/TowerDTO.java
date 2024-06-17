package com.example.munchkin.dto;

public class TowerDTO {
    private int lifePoints;
    protected int idTower;


    public TowerDTO() {

    }

    public TowerDTO(int lifePoints, int idTower) {
        this.lifePoints = lifePoints;
        this.idTower = idTower;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }
}