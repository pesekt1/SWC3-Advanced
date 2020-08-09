package concurrency.statuses;

import java.util.concurrent.atomic.LongAdder;

public class DownloadStatusAtomic implements Status {
  private volatile boolean isDone;
  private LongAdder totalBytes = new LongAdder();
  private int totalFiles;

  @Override
  public int getTotalBytes() {
    return totalBytes.intValue();
  }

  @Override
  public void incrementTotalBytes() {
    totalBytes.increment();
  }

  @Override
  public void incrementTotalFiles() {
    totalFiles++;
  }

  @Override
  public int getTotalFiles() {
    return totalFiles;
  }

  @Override
  public boolean isDone() {
    return isDone;
  }

  @Override
  public void done() {
    isDone = true;
  }
}
