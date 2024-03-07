package com.epam.victor.experiment;

import com.epam.victor.expirement.Experiment;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExperimentTest {

    @Test
    void doExperimentWithHashMap(){
        Experiment experiment = new Experiment();
        experiment.doExperiment(new HashMap<>());
    }


    @Test
    void doExperimentWithConcurrentHashMap(){
        Experiment experiment = new Experiment();
        experiment.doExperiment(new ConcurrentHashMap<>());
    }

    @Test
    void doExperimentWithSynchronizedMap(){
        Experiment experiment = new Experiment();
        Map<Integer, Integer> map = new HashMap<>();
        experiment.doExperiment(Collections.synchronizedMap(map));
    }
}
