package com.example.munchkin.game;

import com.example.munchkin.Player.Player;
import com.example.munchkin.interfaces.GameEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class GameRoundTest {
    @Mock
    private Player mockPlayer;
    @Mock
    private GameEventHandler mockEventHandler;
    private GameRound gameRound;
    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        gameRound = new GameRound(mockPlayer, mockEventHandler);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testConstructor() {
    }

    @Test
    public void testStartMethod() {
        gameRound.start();
        verify(mockEventHandler, times(1)).requestDiceRoll(any());
    }
}

