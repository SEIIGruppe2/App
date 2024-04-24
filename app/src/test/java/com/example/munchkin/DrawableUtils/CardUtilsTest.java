package com.example.munchkin.DrawableUtils;

import com.example.munchkin.DTO.ActionCardDTO;

import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CardUtilsTest {
    @Test
    public void testGetResources() {
        List<ActionCardDTO> cards = Arrays.asList(
                new ActionCardDTO("Bogenschütze", 0, 1),
                new ActionCardDTO("Schwertkämpfer", 1, 2),
                new ActionCardDTO("Held", 2, 3),
                new ActionCardDTO("Ritter", 3, 4),
                new ActionCardDTO("Unknown", 4, 5) // Testing default cases
        );
        String[] expected = {"blauerbogenschuetze", "roterschwertkaempfer", "gruenerheld", "braunerritter", "blauerheld"};
        assertArrayEquals(expected, CardUtils.getresources(cards));
    }

    @Test
    public void testGetResourcesWithEmptyList() {
        assertTrue(CardUtils.getresources(Collections.emptyList()).length == 0);
    }

    @Test
    public void testGetCharacter() {
        assertEquals("bogenschuetze", CardUtils.getcharacter("Bogenschütze"));
        assertEquals("schwertkaempfer", CardUtils.getcharacter("Schwertkämpfer"));
        assertEquals("held", CardUtils.getcharacter("Held"));
        assertEquals("ritter", CardUtils.getcharacter("Ritter"));
        // Test default case
        assertEquals("held", CardUtils.getcharacter("Unknown"));
    }

    @Test
    public void testGetZone() {
        assertEquals("blauer", CardUtils.getzone(0));
        assertEquals("roter", CardUtils.getzone(1));
        assertEquals("gruener", CardUtils.getzone(2));
        assertEquals("brauner", CardUtils.getzone(3));
        // Test default case
        assertEquals("blauer", CardUtils.getzone(4));
    }

}
