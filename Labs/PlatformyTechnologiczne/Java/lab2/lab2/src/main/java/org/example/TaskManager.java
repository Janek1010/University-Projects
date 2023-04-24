package org.example;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TaskManager implements Runnable {
    private static List<Runnable> tasks;
    private static Map<Long, Boolean> results;

    public TaskManager() {
        tasks = new LinkedList<>();
        results = new HashMap<>();
    }

    public synchronized Map<Long, Boolean> getResult() {
        return new HashMap<>(results);
    }

    public synchronized static void addResult(Long value, boolean result) {
        results.put(value, result);
    }

    public synchronized void addTask(Runnable task) {
        tasks.add(task);
        notify();
    }

    public synchronized Runnable getNextTask() {
        while (tasks.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("catch w getNextTask");
            }
        }
        return tasks.remove(0);
    }

    public static void isPrime(long number) throws InterruptedException {
        for (int i = 2; i < number; i++) {
            if (Main.exit) {
                var thread = Thread.currentThread();
                thread.interrupt();
            }
            Thread.sleep(2500);
            if ((number % i) == 0) {
                addResult(number, false);
            }

            System.out.println("Liczę: " + number + " w wątku: " + Thread.currentThread().getName());
        }
        addResult(number, true);
    }

    @Override
    public void run() {
        while (true) {
            var newTask = getNextTask();
            if (newTask != null) {
                System.out.println("Odpalam nowy task");
                var thread = new Thread(newTask);
                thread.start();
            }
        }
    }
}
