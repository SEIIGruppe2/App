package com.example.munchkin.model;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.example.munchkin.interfaces.DiceRollCallback;

class DiceRollModelTest {

    @Test
    protected void testRollDice() throws InterruptedException {
        DiceRollModel.DiceRollCallback mockCallback = mock(DiceRollModel.DiceRollCallback.class);
        DiceRollModel model = new DiceRollModel();
        CountDownLatch latch = new CountDownLatch(1);
        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);

        model.rollDice(new DiceRollModel.DiceRollCallback() {
            @Override
            public void onDiceRolled(int randomNumber) {
                mockCallback.onDiceRolled(randomNumber);
                latch.countDown();
            }
        });
        boolean await = latch.await(2, TimeUnit.SECONDS);

        verify(mockCallback, times(1)).onDiceRolled(argumentCaptor.capture());
        assertTrue(argumentCaptor.getValue() >= 0 && argumentCaptor.getValue() < 4);
        assertTrue(await);
    }
}