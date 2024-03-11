package com.epam.victor.experiment;

import com.epam.victor.expirement.CopyOnWriteMap;
import com.epam.victor.expirement.CopyOnWriteMapNoSync;
import com.epam.victor.expirement.Experiment;
import com.epam.victor.expirement.ExperimentConcurrentModificationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExperimentTest {

    @Test
    void doExperimentWithHashMap(){
        Experiment experiment = new Experiment();
        Assertions.assertThrows(
                ExperimentConcurrentModificationException.class,
                () -> experiment.doExperiment(new HashMap<>(), 30));
    }


    @Test
    void doExperimentWithConcurrentHashMap(){
        Experiment experiment = new Experiment();
        experiment.doExperiment(new ConcurrentHashMap<>(), 30);
    }

    @Test
    void doExperimentWithSynchronizedMap(){
        Experiment experiment = new Experiment();
        Map<Integer, Integer> map = new HashMap<>();
        Assertions.assertThrows(
                ExperimentConcurrentModificationException.class,
                () -> experiment.doExperiment(Collections.synchronizedMap(map), 30));
    }

    @Test
    void doExperimentWithCustomMap(){
        Experiment experiment = new Experiment();
        experiment.doExperiment(new CopyOnWriteMap<>(), 30);
    }

    @Test
    void doExperimentWithNoSyncCustomMap(){
        Experiment experiment = new Experiment();
        experiment.doExperiment(new CopyOnWriteMapNoSync<>(), 30);
    }
}
