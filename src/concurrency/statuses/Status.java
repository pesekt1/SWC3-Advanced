package concurrency.statuses;

public interface Status {
    public int getTotalBytes();

    public void incrementTotalBytes();

    public void incrementTotalFiles();

    public int getTotalFiles();

    public boolean isDone();

    public void done();
}
