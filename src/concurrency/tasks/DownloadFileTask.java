package concurrency.tasks;

import concurrency.statuses.Status;

public class DownloadFileTask implements Runnable {

  private Status status; //interface - because we want to show atomic and non atomic solution

  //overloaded constructor - we can pass an instance of s status
  public DownloadFileTask(Status status) {
    this.status = status;
  }

  //overloaded constructor - if we dont pass status, we create new instance
//  public DownloadFileTask(){
//    status = new DownloadStatus();
//  }

  @Override
  public void run() {
    System.out.println("Downloading a file: " + Thread.currentThread().getName());

    for (var i = 0; i < 10_000; i++) {
      if (Thread.currentThread().isInterrupted()) return;
      status.incrementTotalBytes();
    }

    System.out.println("Download complete: " + Thread.currentThread().getName());
    status.done();
  }

  public Status getStatus() {
    return status;
  }

}
