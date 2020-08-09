package concurrency;

import concurrency.statuses.DownloadStatus;
import concurrency.statuses.DownloadStatusAtomic;
import concurrency.statuses.DownloadStatusWithLock;
import concurrency.statuses.Status;
import concurrency.tasks.DownloadFileTask;
import concurrency.tasks.InterruptableTask;
import concurrency.tasks.SimpleTask;
import concurrency.tasks.SlowTask;

import java.util.*;

public class ThreadDemo {

    public static void threads() {
        System.out.println("Running threads: " + Thread.activeCount());
        System.out.println("Available threads: " + Runtime.getRuntime().availableProcessors());
    }

    public static void startingThread() {
        System.out.println(Thread.currentThread().getName()); //get name of current thread

        //starting a thread using an instance of a class that implements Runnable interface:
        var thread = new Thread(new SimpleTask());
        thread.start();

        //starting a thread using lambda expression:
        var thread2 = new Thread(() -> System.out.println("new thread started: " + Thread.currentThread().getName()));
        thread2.start();
    }

    public static void startingMultipleThreads() {
        for (int i = 0; i < 10; i++) {
            new Thread(new SimpleTask()).start();
        }
        System.out.println("active threads: " + Thread.activeCount());
    }

    public static void pausingThread() {
        new Thread(new SlowTask()).start();
    }

    //we can wait for the completeon of a thread:
    public static void joiningThread() {
        Thread thread = new Thread(new SlowTask());
        thread.start();
        try {
            thread.join(); // main thread will wait for completion
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //another task
        System.out.println("Another task can start.");
    }

    public static void interruptingThread() {
        Thread thread = new Thread(new InterruptableTask());
        thread.start();

        try { //wait 2s
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt(); //interrupt
        System.out.println("Thread interrupted.");
    }

    //simulating lost updates
    public static void raceConditionsLostUpdates() {
        var status = new DownloadStatus(); // not using atomic data type
        runThreads(status);
    }

    //Simulating confinement strategy - status object is not shared by threads
    //Each thread has its own status object
    public static void confinementStrategy() {
        List<Thread> threads = new ArrayList<>();
        List<DownloadFileTask> tasks = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            var task = new DownloadFileTask(new DownloadStatus());
            tasks.add(task);

            Thread thread = new Thread(task);
            thread.start();
            threads.add(thread);
        }

        for (var thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //here all threads finished their tasks.
        //We can sum up statuses from all tasks.
        int sum = 0;
        for (var task : tasks) {
            sum += task.getStatus().getTotalBytes();
        }
        System.out.println("Total bytes: " + sum);
    }

    //no lost updates here because we are using locks
    public static void raceConditionsLocks() {
        var status = new DownloadStatusWithLock();
        runThreads(status);
    }

    //simulating visibility problem:
    //thread2 does not see the change in isDone variable
    //solution: declare it as volatile
    public static void volatileVariable(Status status) {
        var thread1 = new Thread(new DownloadFileTask(status));
        var thread2 = new Thread(() -> {
            while (!status.isDone()) {
            } // thread2 waiting until isDone = TRUE
            System.out.println("Total bytes: " + status.getTotalBytes() + " " + Thread.currentThread().getName());
        });

        thread1.start();
        thread2.start();
    }

    //no lost updates here because we are using atomic incrementing
    public static void raceConditionsAtomic() {
        var status = new DownloadStatusAtomic();
        runThreads(status);
    }

    public static void synchronizedCollection(){
        Collection<Integer> collection = new ArrayList<>();
        Collection<Integer> collectionSync = Collections.synchronizedCollection(new ArrayList<>());

        System.out.println("Using normal collection: ");
        runDemoSynchronizedCollection(collection);
        System.out.println("\nUsing synchronized collection: ");
        runDemoSynchronizedCollection(collectionSync);
    }

    private static void runDemoSynchronizedCollection(Collection<Integer> collection) {
        //Collection<Integer> collection = new ArrayList<>();
        var thread1 = new Thread(() -> {
            collection.addAll(Arrays.asList(1, 2, 3));
        });

        var thread2 = new Thread(() -> {
            collection.addAll(Arrays.asList(4, 5, 6));
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(collection);
    }

    private static void runThreads(Status status) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new DownloadFileTask(status));
            thread.start();
            threads.add(thread);
        }

        for (var thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Total bytes: " + status.getTotalBytes());
    }
}
