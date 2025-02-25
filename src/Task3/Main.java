package Task3;

public class Main {
    public static void main(String[] args) {

        MyThreadPool myThreadPool = new MyThreadPool(3);
        for (int i = 1; i <= 8; i++) {
            final int taskId = i;
            myThreadPool.execute(() -> {
                System.out.println("Task " + taskId + " - Thread " + Thread.currentThread().getName() + " START");
                try {
                    // Simulate some work with Thread.sleep
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Task " + taskId + " - Thread " + Thread.currentThread().getName() + " FINISH");
            });
        }

        myThreadPool.shutdown();
        myThreadPool.awaitTermination();

        System.out.println("Все задачи выполнены");
    }
}
