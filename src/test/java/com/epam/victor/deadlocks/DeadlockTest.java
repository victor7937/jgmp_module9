package com.epam.victor.deadlocks;

import org.junit.jupiter.api.Test;

public class DeadlockTest {

    @Test
    void runDeadlockTask(){
        DeadlockTask deadlockTask = new DeadlockTask();
        deadlockTask.executeDeadlockTask();
    }
}
