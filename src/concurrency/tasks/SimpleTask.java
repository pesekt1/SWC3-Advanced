package concurrency.tasks;

public class SimpleTask implements Runnable{
    @Override
    public void run() {
        System.out.println("Simple task -" + Thread.currentThread().getName());
    }
}
