package com.eveiled.ex01;

import static java.lang.Thread.sleep;

public class Program {
    public static void main(String[] args) {

        PC pc = new PC();
        int count = 50;

        if (args.length != 0 && args[0].contains("--count=")) {
            count = Integer.parseInt(args[0].split("=")[1]);
        }

        Thread consumer = new ConsumerThread("Egg",count, pc);
        Thread produser = new Thread(new ProduserRunnable("Chicken", count, pc));

        consumer.start();
        produser.start();

        try {
            consumer.join();
            produser.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class ConsumerThread extends Thread {
    private final String creature;
    private final int number;
    private final PC pc;

    public ConsumerThread(String creature, int number, PC pc)
    {
        this.creature = creature;
        this.number = number;
        this.pc = pc;
    }

    @Override
    public void run() {
        for (int i = 0; i < number; i++) {
            try {
                pc.produce(creature);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ProduserRunnable implements Runnable{
    private final String creature;
    private final int number;
    private final PC pc;

    public ProduserRunnable(String creature, int number, PC pc)
    {
        this.creature = creature;
        this.number = number;
        this.pc = pc;
    }

    @Override
    public void run() {
        for (int i = 0; i < number; i++) {
            try {
                pc.consume(creature);
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
