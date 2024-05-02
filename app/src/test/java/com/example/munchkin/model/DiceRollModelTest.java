package com.example.munchkin.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static org.mockito.Mockito.*;

class DiceRollModelTest {

    private DiceRollModel diceRollModel;

    @Mock
    private DiceRollModel.DiceRollCallback mockCallback;

    @BeforeEach
    protected void setUp() {
        MockitoAnnotations.initMocks(this);
        diceRollModel = new DiceRollModel();
    }

    @Test
    protected void testRollDice() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        diceRollModel.rollDice(new DiceRollModel.DiceRollCallback() {
            @Override
            public void onDiceRolled(int randomNumber) {
                verify(mockCallback).onDiceRolled(randomNumber);

                latch.countDown();
            }
        });

        latch.await(2, TimeUnit.SECONDS);
    }

    @Test
    protected void testRollDiceMultipleTimes() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            diceRollModel.rollDice(new DiceRollModel.DiceRollCallback() {
                @Override
                public void onDiceRolled(int randomNumber) {
                    verify(mockCallback).onDiceRolled(randomNumber);

                    latch.countDown();
                }
            });
        }

        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    protected void testRollDiceCallbackInvocation() {
        diceRollModel.rollDice(mockCallback);
        verify(mockCallback, timeout(2000)).onDiceRolled(anyInt());
    }

}
