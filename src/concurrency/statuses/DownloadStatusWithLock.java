package concurrency.statuses;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DownloadStatusWithLock implements Status{
    private boolean isDone;
    private int totalBytes;
    private int totalFiles;
    Lock lock = new ReentrantLock(); //lock object

    @Override
    public int getTotalBytes() {
        return totalBytes;
    }

    @Override
    public void incrementTotalBytes() {
        lock.lock();
        try {
        totalBytes++;
        }finally {
            lock.unlock();
        }
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