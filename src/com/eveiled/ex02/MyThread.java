package com.eveiled.ex02;

public class MyThread extends Thread{

    private int sum = 0;
    private int from;
    private int to;
    private int threadNumber;
    private int[] array;

    public MyThread(int from, int to, int[] array, int threadNumber) {
        this.from = from;
        this.to = to;
        this.array = array;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        for (int i = from; i <= to; i++) {
            sum += array[i];
        }
        System.out.println("Thread " + threadNumber + ": from " + from + " to " + to + " sum is " + sum);
    }

    public int getSum(){
        return sum;
    }
}
