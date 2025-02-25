package Task3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyThreadPool {
    private final Queue<Runnable> workQueue = new LinkedList<>();
    private final Thread[] workers;
    private boolean isShutdown = false;

    public MyThreadPool(int poolSize) {
        workers = new Thread[poolSize];
        for (int i = 0; i < poolSize; i++) {
            workers[i] = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()){
                    Runnable task;
                    synchronized (workQueue) {
                        while (workQueue.isEmpty() && !isShutdown) {
                            try {
                                workQueue.wait();
                            } catch (InterruptedException e) {
                                Thread.currentThread().isInterrupted();
                                return;
                            }
                        }
                        if (workQueue.isEmpty() && isShutdown){
                            return;
                        }
                        task= workQueue.poll();
                    }
                    if (task != null) {
                        task.run();
                    }
                }
            });
            workers[i].start();
        }
    }

    public void execute(Runnable task) {
        synchronized (workQueue) {
            if (isShutdown) {
                throw new IllegalStateException("ThreadPool is shut down");
            }
            workQueue.add(task);
            workQueue.notify();
        }
    }

    public void shutdown() {
        isShutdown = true;
    }

    public boolean awaitTermination() {
        for (Thread worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return true;
    }

}
