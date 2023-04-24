package org.example;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    static volatile boolean exit = false;
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        var taskManager = new TaskManager();
        int threadsAmount = 5;
        if (args.length > 0) {
            threadsAmount = Integer.parseInt(args[0]);
        }
        for (int i = 0; i < threadsAmount; i++) {
            taskManager.addTask(() -> {
                try {
                    TaskManager.isPrime(random.nextInt(500000, 500000000));
                } catch (InterruptedException e) {
                    System.out.println("Przerwano watek" + Thread.currentThread());
                }
            });
        }
        Thread mainThread = new Thread(taskManager);
        mainThread.start();

        while (true) {
            String input = scanner.nextLine();

            if (!input.isEmpty()) {
                if (input.equals("q")){
                    System.out.println("koniec");
                    break;

                }
                taskManager.addTask(() -> {
                    try {
                        TaskManager.isPrime(random.nextInt(500000, 500000000));
                    } catch (InterruptedException e) {
                        System.out.println("Przerwano watek" + Thread.currentThread());
                    }
                });

            }
        }
        System.out.println("Aktualna ilosc watkow: " +Thread.activeCount() + " (Przed zakonczeniem programu moga byc aktywne tylko 3)");
        exit = true;
        mainThread.interrupt();
        while (Thread.activeCount() > 3){
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Wygaszanie wyjatkow, pozostala liczba: "+ Thread.activeCount());
        }
        System.out.println("Wygaszanie wyjatkow, pozostala liczba: "+ Thread.activeCount());
        System.out.println("Sukces! Program zostanie poprawnie wyłączony");


        System.exit(0);


    }

}