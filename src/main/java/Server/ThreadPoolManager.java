package Server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager {
    private final ExecutorService executorService;

    public ThreadPoolManager(int maxThreads) {
        this.executorService = Executors.newFixedThreadPool(maxThreads);
    }

    public void submitTask(Runnable task) {
        executorService.submit(task);
    }
}
