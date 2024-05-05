package com.example.munchkin.model;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class DiceRollModelTest {

    @Test
    void testRollDice() throws InterruptedException {
        // Create a mock of DiceRollCallback
        DiceRollModel.DiceRollCallback mockCallback = mock(DiceRollModel.DiceRollCallback.class);

        // Instantiate the model
        DiceRollModel model = new DiceRollModel();

        // Latch for synchronizing the test thread and the dice roll thread
        CountDownLatch latch = new CountDownLatch(1);

        // ArgumentCaptor to capture the random number passed to the callback
        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);

        // Perform the test
        model.rollDice(new DiceRollModel.DiceRollCallback() {
            @Override
            public void onDiceRolled(int randomNumber) {
                mockCallback.onDiceRolled(randomNumber);
                latch.countDown();
            }
        });

        // Wait for the thread to finish
        boolean await = latch.await(2, TimeUnit.SECONDS);

        // Verify that the callback was called
        verify(mockCallback, times(1)).onDiceRolled(argumentCaptor.capture());

        // Assert that the captured random number is within the expected range
        assertTrue(argumentCaptor.getValue() >= 0 && argumentCaptor.getValue() < 4, "Random number should be between 0 and 3");

        // Check if the thread finished properly
        assertTrue(await, "Dice roll thread did not complete in time");
    }
}
