package com.eveiled.ex01;

import java.util.LinkedList;
import java.util.List;

public class PC {
    private static final int BUFFER = 1;
    private volatile int buffer = 0;

    synchronized void produce(String str) throws InterruptedException{
        while (buffer == 1) {
            wait();
        }
        System.out.println(str);
        buffer = 1;
        notify();
    }

    synchronized void consume(String str) throws InterruptedException{
        while (buffer == 0) {
            wait();
        }
        System.out.println(str);
        buffer = 0;
        notify();
    }

}
