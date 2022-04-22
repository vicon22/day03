package com.eveiled.ex02;

import java.util.Random;
import java.util.stream.IntStream;

public class Program {

    private static int MAX_NUMBER_OF_ARRAY = 2_000_000;

    public static void main(String[] args) {

        int arraySize = parseArgs("arraySize", args);
        int numberThreads = parseArgs("threadsCount", args);

        if (arraySize > MAX_NUMBER_OF_ARRAY || arraySize < numberThreads || arraySize * numberThreads == 0){
            System.out.println("Invalid arguments");
            System.exit(1);
        }
        int sum = 0;
        MyThread[] threads = new MyThread[numberThreads];
        int[] randomArray = createRandomArray(arraySize);



        System.out.println("Sum: " + IntStream.of(randomArray).sum());

        int sellSize = arraySize / numberThreads;
        int from = 0;
        int to = sellSize - 1;
        for (int i = 0; i < numberThreads - 1; i++) {
            threads[i] = new MyThread(from,to,randomArray, i + 1);
            from += sellSize;
            to += sellSize;
        }
        threads[numberThreads - 1] = new MyThread(from,arraySize - 1,randomArray, numberThreads);

        for (int i = 0; i < numberThreads; i++) {
            threads[i].start();
        }

        try {
            for (int i = 0; i < numberThreads; i++) {
                threads[i].join();
                sum += threads[i].getSum();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Sum by threads: " + sum);
    }

    private static int parseArgs(String nameOfArguments, String[] args) {

        int ans = 0;
        String parsed = null;

        nameOfArguments = "--" + nameOfArguments + "=";

        for (String arg: args) {
            if (arg.contains(nameOfArguments)){
                parsed = arg.split("=")[1];
            }
        }
        if (parsed != null){
            ans = Integer.parseInt(parsed);
        }
        return ans;
    }

    private static int[] createRandomArray(int arraySize) {

        Random random = new Random();
        int[] randomArray = new int[arraySize];

        for (int i = 0; i < arraySize; i++) {
            randomArray[i] = random.nextInt(2000) - 1000;
        }

        return randomArray;
    }

}


