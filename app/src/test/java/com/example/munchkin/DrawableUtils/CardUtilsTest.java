package com.example.munchkin.DrawableUtils;

import com.example.munchkin.DTO.ActionCardDTO;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CardUtilsTest {

    @Test
    protected void testGetResources() {
        List<ActionCardDTO> cards = Arrays.asList(
                new ActionCardDTO("Bogenschütze", 0, 1),
                new ActionCardDTO("Schwertkämpfer", 1, 2),
                new ActionCardDTO("Held", 2, 3),
                new ActionCardDTO("Ritter", 3, 4),
                new ActionCardDTO("Unknown", 4, 5)
        );
        String[] expected = {"roterbogenschuetze", "blauerschwertkaempfer", "gruenerheld", "braunerritter", "blauerheld"};
        assertArrayEquals(expected, CardUtils.getresources(cards));
    }

    @Test
    protected void testGetResourcesWithEmptyList() {
        assertEquals(0, CardUtils.getresources(Collections.emptyList()).length);
    }

    @Test
    protected void testGetCharacter() {
        assertEquals("bogenschuetze", CardUtils.getcharacter("Bogenschütze"));
        assertEquals("schwertkaempfer", CardUtils.getcharacter("Schwertkämpfer"));
        assertEquals("held", CardUtils.getcharacter("Held"));
        assertEquals("ritter", CardUtils.getcharacter("Ritter"));

        assertEquals("held", CardUtils.getcharacter("Unknown"));
    }

    @Test
    protected void testGetZone() {
        assertEquals("roter", CardUtils.getzone(0));
        assertEquals("blauer", CardUtils.getzone(1));
        assertEquals("gruener", CardUtils.getzone(2));
        assertEquals("brauner", CardUtils.getzone(3));

        assertEquals("blauer", CardUtils.getzone(4));
    }
}