package concurrency.statuses;

public class DownloadStatus implements Status{
    private boolean isDone;
    private int totalBytes;
    private int totalFiles;

    @Override
    public int getTotalBytes() {
        return totalBytes;
    }

    @Override
    public void incrementTotalBytes() {
        totalBytes++;
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