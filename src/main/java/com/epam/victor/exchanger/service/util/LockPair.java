package com.epam.victor.exchanger.service.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.locks.Lock;

@Getter
@AllArgsConstructor
public class LockPair {
    private Lock firstLock;
    private Lock secondLock;

    private String firstId;
    private String secondId;
}

