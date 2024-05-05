package com.example.munchkin.game;

import com.example.munchkin.Player.Player;
import com.example.munchkin.interfaces.GameEventHandler;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class GameRoundTest {

    @Mock
    private Player mockPlayer;
    @Mock
    private GameEventHandler mockEventHandler;

    private GameRound gameRound;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        gameRound = new GameRound(mockPlayer, mockEventHandler);
    }

    @Test
    public void testConstructor() {

    }





}

