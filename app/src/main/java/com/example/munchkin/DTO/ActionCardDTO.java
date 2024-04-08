package com.example.munchkin.DTO;

public class ActionCardDTO extends EntityDTO {

    // No-argument constructor
    public ActionCardDTO() {
        super();
    }

    // Constructor including inherited fields
    public ActionCardDTO(String name, int zone, int id) {
        super(name, zone,id);
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public int getZone() {
        return super.getZone();
    }

    @Override
    public String getName() {
        return super.getName();
    }
}
