package executors;

import java.sql.SQLOutput;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CompletableFuturesDemo {

    public static void creatingCompletableFuture() {
        //Runnable CompletableFuture
        Runnable task = () -> System.out.println("a");
        var future = CompletableFuture.runAsync(task);

        //Supplier CompletableFuture
        Supplier<Integer> task2 = () -> 1;
        var future2 = CompletableFuture.supplyAsync(task2);
    }

    public static void asyncAPI() {
        System.out.println("Example of synchronous code: ");
        var service = new MailService();
        service.send();
        System.out.println("Doing some other work.");

        System.out.println("\nExample of asynchronous code: ");
        service.sendAsync();
        System.out.println("Doing some other work.");

        // this is only here because otherwise the program stops
        // and we will not see the result from the other thread.
        mainSleep(4000);
    }

    public static void runningOnCompletion() {
        var future = CompletableFuture.runAsync(() -> System.out.println("Doing something."));
        future.thenRunAsync(() -> System.out.println("Done, " + Thread.currentThread().getName()));

        var future2 = CompletableFuture.supplyAsync(() -> 1);
        future2.thenAcceptAsync(result -> System.out.println(result + ", " + Thread.currentThread().getName()));

        //This is just to make sure the threads finish before main thead terminates the program
        mainSleep(1000);
    }

    public static void handlingExceptions() {
        // we will not see the exception - because it is thrown on a separate thread...
        var future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Getting the current weather.");
            throw new IllegalStateException();
        });

        //to get the exception we must call future.get()
        try {
            //future.get(); //this would give the exception
            //We dont want out app to crash:
            var result = future.exceptionally(ex -> 28).get(); // if exception, return a default value and continue.
            System.out.println("Current temperature: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    //With CompletableFutures we can build complex asynchronous operations using functional programming
    public static void transformingCompletableFuture() {
        var future = CompletableFuture.supplyAsync(() -> 20);
        var result = future
                .thenApply(celsius -> toFahrenheit(celsius)) //change from Celsius to Fahrenheit
                .thenAccept(farhenheit -> System.out.println(farhenheit)); //print
        System.out.println("Temperature: " + result);
    }

    private static int toFahrenheit(int celsius) {
        return (int) ((celsius * 1.8) + 32);
    }

    public static void composingCompletableFutures() {
        //simulation: We get user id
        //1.task: we look up users email in the database
        //2.task: we pass the email to spotify and get users playlist

        getUserEmailAsync() //simulating querying a database
                .thenCompose(email -> getPlaylistAsync(email))
                .thenAccept(playlist -> System.out.println(playlist));

        mainSleep(1000); //to force main thread to wait for the result to appear in the command line
    }

    private static CompletableFuture<String> getUserEmailAsync() {
        return CompletableFuture.supplyAsync(() -> "email"); //simulating querying a database
    }

    private static CompletableFuture<String> getPlaylistAsync(String email) {
        return CompletableFuture.supplyAsync(() -> "playlist"); //simulating getting the playlist object
    }

    public static void combiningCompletableFutures() {
        //we call a remote service to get a price of a product (in USD).
        //concurrently we want to call another service to give us the exchange rate to EUR

        //simulating getting product price
        var first = CompletableFuture
                .supplyAsync(() -> "20USD")
                .thenApply(str -> {
                    var price = str.replace("USD", "");
                    return Integer.parseInt(price);
                });

        var second = CompletableFuture.supplyAsync(() -> 0.9); //simulating getting exchange rate
        first
                .thenCombine(second, (price, exchangeRate) -> price * exchangeRate)
                .thenAccept(result -> System.out.println("Price: " + result + "EUR"));

        mainSleep(1000); //to force main thread to wait for the result to appear in the command line
    }

    public static void waitingForManyTasks() {
        var first = CompletableFuture.supplyAsync(() -> 1);
        var second = CompletableFuture.supplyAsync(() -> 2);
        var third = CompletableFuture.supplyAsync(() -> 3);

        //we wait for all tasks to complete
        var all = CompletableFuture.allOf(first, second, third);
        all.thenRun(() -> {
            try {
                System.out.println("All tasks completed, results: " + first.get() + "," + second.get() + "," + third.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        mainSleep(1000);
    }

    public static void waitingForFirstResult() {
        //We have 2 services for getting the current weather.

        //slow service
        var first = CompletableFuture.supplyAsync(() -> {
            LongTask.simulate(3000);
            return 20;
        });

        //fast service
        var second = CompletableFuture.supplyAsync(() -> 20);

        //as soon as one task completes, temperature will be printed.
        CompletableFuture.anyOf(first, second).thenAccept(temp -> System.out.println(temp));
    }

    public static void handlingTimeOuts() {
        var future = CompletableFuture.supplyAsync(() -> {
            LongTask.simulate(3000);
            return 3;
        });

        //Another method: future.orTimeout().get()
        try {
            var result = future
                    .completeOnTimeout(1, 2, TimeUnit.SECONDS)
                    .get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        mainSleep(3000);
    }

    public static void bestPriceFinder() {
        var start = LocalTime.now(); //for measuring the time

        var service = new FlightService();
        var futures = service.getQuotes() //simulating getting quotes from different sites
                .map(future -> future.thenAccept(System.out::println)) //print the quote
                .collect(Collectors.toList()); // terminal operation, we collect the result in a List

        //Calculating the duration of all tasks
        CompletableFuture
                .allOf(futures.toArray(new CompletableFuture[0]))
                .thenRun(() -> {
                    var end = LocalTime.now();
                    var duration = Duration.between(start, end);
                    System.out.println("Retrieved all quotes in " + duration.toMillis() + " msec.");
                });

        mainSleep(10_000);
    }

    private static void mainSleep(int msec) {
        try {
            Thread.sleep(msec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
