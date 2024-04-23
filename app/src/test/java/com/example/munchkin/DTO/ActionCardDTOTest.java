package com.example.munchkin.DTO;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ActionCardDTOTest {

    @Test
    public void testActionCardDTOConstructorAndAccessors() {
        ActionCardDTO actionCard = new ActionCardDTO("Red Knight", 2, 69);

        assertEquals("Red Knight", actionCard.getName());
        assertEquals(2, actionCard.getZone());
        assertEquals(69, actionCard.getId());
    }


    @Test
    public void testNoArgConstructor() {
        ActionCardDTO actionCard = new ActionCardDTO();
        assertNotNull(actionCard);
    }
}
