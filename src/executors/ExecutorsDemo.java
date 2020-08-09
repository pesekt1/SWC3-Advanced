package executors;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class ExecutorsDemo {

    public static void executors() {
        var executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> System.out.println(Thread.currentThread().getName()));
    }

    public static void executorPool() {
        var executor = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 10; i++) {
            executor.submit(() -> System.out.println(Thread.currentThread().getName()));
        }
    }

    public static void shuttingDownExecutor(){
        var executor = Executors.newFixedThreadPool(2);
        try {
            executor.submit(() -> System.out.println(Thread.currentThread().getName()));
        }finally{
            executor.shutdown();
        }
    }


    public static void callableObject(){
        var executor = Executors.newFixedThreadPool(2);

        //submit method returns the Future object immediately.
        //but it takes time before the future value is ready
        var future = executor.submit(() -> {
            LongTask.simulate(3000); //simulating a long task
            return 1; //simulation
        });

        System.out.println("Doing some other tasks.");
        try {
            var result = future.get(); //blocking method get(), current thread has to wait for the result
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        //in the end we should shut down the executor
    }

    public static void show() {
        var executor = Executors.newFixedThreadPool(2);

        try {
            var future = executor.submit(() -> {
                LongTask.simulate();
                return 1;
            });

            System.out.println("Do more work");

            try {
                var result = future.get();
                System.out.println(result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } finally {
            executor.shutdown();
        }
    }
}
