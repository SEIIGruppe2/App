package com.example.munchkin.DTO;

public class ActionCardDTO extends EntityDTO {

    // No-argument constructor
    public ActionCardDTO() {
        super();
    }

    // Constructor including inherited fields
    public ActionCardDTO(String name, int zone) {
        super(name, zone);
    }


}
