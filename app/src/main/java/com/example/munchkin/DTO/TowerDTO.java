package com.example.munchkin.DTO;

public class TowerDTO {
    private int lifePoints;



    public TowerDTO() {

    }

    public TowerDTO(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }
}
