package com.example.munchkin.game;

import com.example.munchkin.Player.Player;
import com.example.munchkin.interfaces.GameEventHandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class GameRoundTest {

    @Mock
    private Player mockPlayer;

    @Mock
    private GameEventHandler mockEventHandler;


    private GameRound gameRound;

    @BeforeEach
    protected void setUp() {
        MockitoAnnotations.initMocks(this);
        gameRound = new GameRound(mockPlayer, mockEventHandler);
    }


    @Test
    protected void testConstructor() {


    }

    @Test
    protected void testStartMethod() {
        gameRound.start();
        verify(mockEventHandler, times(1)).requestDiceRoll(any());

    }
}





