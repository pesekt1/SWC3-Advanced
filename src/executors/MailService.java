package executors;

import java.util.concurrent.CompletableFuture;

public class MailService {

  //synchronous email sending - blocking
  public void send() {
    //Sending an email takes time - we are communicating through the network.
    System.out.println("Sending mail started.");
    LongTask.simulate(3000);
    System.out.println("Mail was sent.");
  }

  //asynchronous email sending - non-blocking
  public CompletableFuture<Void> sendAsync() {
    //task will be executed on a separate thread from common thread pool
    return CompletableFuture.runAsync(() -> send());
  }
}
