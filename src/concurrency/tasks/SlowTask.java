package concurrency.tasks;

public class SlowTask implements Runnable{
    @Override
    public void run() {
        System.out.println("Simple task - " + Thread.currentThread().getName());

        //simulating the duration of the task
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Task finished - " + Thread.currentThread().getName());
    }
}