package com.eveiled.ex03;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Program {

    static String PATH_TO_FILE_WITH_URLS = "files_urls.txt";

    public static void main(String[] args) {

        int threadsCount = parseArgs("threadsCount", args);
        if (threadsCount == 0){
            System.out.println("Argument threadsCount has to be positive(>0)!");
            System.exit(1);
        }
        String[] urls = parseURLS();
        //Arrays.stream(urls).forEach(System.out::println);

        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount,  new ThreadFactory() {
            int i = 1;

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("Thread " + i++);

                return thread;
            }
        });

        for (int i = 0; i < urls.length; i++) {
            final int index = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    int numberFile = index + 1;
                    System.out.println(Thread.currentThread().getName()
                            + " start download file number "
                            + numberFile);
                    try {
                        donwloadFile(urls[index]);
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()
                            + " finish download file number "
                            + numberFile);
                }
            });
        }

        executorService.shutdown();
    }

    private static String[] parseURLS() {

        StringBuilder stringBuilder = new StringBuilder();
        String[] urls = null;
        File file = new File(PATH_TO_FILE_WITH_URLS);
        if (!file.exists()){
            System.out.println("File with urls doesnt exists");
            System.exit(1);
        }

        try(Reader reader = new InputStreamReader(new FileInputStream(file))) {
            int a = reader.read();
            while (a > 0) {
                stringBuilder.append((char) a);
                a = reader.read();
            }
        }catch (IOException e){
                e.printStackTrace();
            }
        urls = stringBuilder.toString().split("\n");

        if (urls.length == 0){
            System.out.println("File with urls is empty");
            System.exit(1);
        }

        return urls;
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

    private static void donwloadFile(String url) throws IOException {

        String filename =  url.substring(url.lastIndexOf("/") + 1);
        FileOutputStream fout = new FileOutputStream(filename);
        BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
        byte data[] = new byte[1024];
        int count = in.read(data);
        while (count != -1){
            fout.write(data, 0, count);
            count = in.read(data);
        }
        in.close();
        fout.close();
    }


}
