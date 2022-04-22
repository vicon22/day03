package com.eveiled.ex00;

public class Program {
    public static void main(String[] args) {

        int count = 50;

        if (args.length != 0 && args[0].contains("--count=")) {
            count = Integer.parseInt(args[0].split("=")[1]);
        }

        MyTread first = new MyTread("Egg", count);
        MyTread second = new MyTread("Chicken", count);

        first.start();
        second.start();

        try {
            first.join();
            second.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Human");

    }
}

class MyTread extends Thread {
    private final String creature;
    private final int number;

    public MyTread(String creature, int number)
    {
        this.creature = creature;
        this.number = number;
    }

    @Override
    public void run() {
        for (int i = 0; i < number; i++) {
            System.out.print(i + " ");
            System.out.println(creature);
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}