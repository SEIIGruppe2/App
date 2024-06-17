package com.example.munchkin.drawableutils;

import com.example.munchkin.dto.ActionCardDTO;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CardUtilsTest {

    @Test
    protected void testGetResources() {
        List<ActionCardDTO> cards = Arrays.asList(
                new ActionCardDTO("Bogenschütze", 1, 1),
                new ActionCardDTO("Schwertkämpfer", 2, 2),
                new ActionCardDTO("Held", 3, 3),
                new ActionCardDTO("Ritter", 4, 4),
                new ActionCardDTO("Unknown", 5, 5)
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
        assertEquals("roter", CardUtils.getzone(1));
        assertEquals("blauer", CardUtils.getzone(2));
        assertEquals("gruener", CardUtils.getzone(3));
        assertEquals("brauner", CardUtils.getzone(4));

        assertEquals("blauer", CardUtils.getzone(5));
    }
}