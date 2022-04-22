package com.eveiled.ex00;

public class Program {

    public static void main(String[] args) {

        int count = 50;
        int TIME_TO_SLEEP = 100;

        if (args.length != 0 && args[0].contains("--count=")) {
            count = Integer.parseInt(args[0].split("=")[1]);
        }

        MyTread first = new MyTread("Egg", count, TIME_TO_SLEEP);
        MyTread second = new MyTread("Chicken", count, TIME_TO_SLEEP);

        first.start();
        second.start();

        try {
            first.join();
            second.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < count; i++) {
            System.out.println("Human");
            try {
                Thread.sleep(TIME_TO_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}

class MyTread extends Thread {
    private final String creature;
    private final int number;
    private int sleepTime;

    public MyTread(String creature, int number, int sleepTime)
    {
        this.creature = creature;
        this.number = number;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        for (int i = 0; i < number; i++) {
            System.out.println(creature);
            try {
                sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}