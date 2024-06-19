package com.example.munchkin.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class AppStateTest {


    @Test
    void testSingletonInstance() {
        AppState firstInstance = AppState.getInstance();
        AppState secondInstance = AppState.getInstance();
        Assertions.assertEquals(firstInstance, secondInstance);
    }

    @Test
    void testSetAndGetUser() {
        AppState instance = AppState.getInstance();
        instance.setCurrentUser("testUser");
        Assertions.assertEquals("testUser", instance.getCurrentUser());
    }


    @Test
    void testSingletonThreadSafety() throws InterruptedException {
        final int threadCount = 100;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        final CountDownLatch latch = new CountDownLatch(threadCount);
        final Set<AppState> instances = ConcurrentHashMap.newKeySet();

        for (int i = 0; i < threadCount; i++) {
            service.submit(() -> {
                instances.add(AppState.getInstance());
                latch.countDown();
            });
        }

        latch.await();
        Assertions.assertEquals(1, instances.size());
        service.shutdown();
    }

}
