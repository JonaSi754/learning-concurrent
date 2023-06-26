import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ThreadCreationTest {

    /**
     * *************** 通过继承 Thread 创建线程 *****************
     */
    private static class MyThreadTask extends Thread {

        @Override
        public void run() {
            System.out.println("子线程名称 ===>> " + Thread.currentThread().getName());
        }
    }

    /**
     * *************** 通过实现 Runnable 创建线程 *****************
     */
    // 通过匿名类创建线程
    public Thread createThreadByRunnableAnonymousClass() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程名称 ===>> " + Thread.currentThread().getName());
            }
        });
    }

    // 通过 Lambda 表达式创建线程
    public Thread createThreadByRunnableLambda() {
        return new Thread(() -> {
            System.out.println("子线程名称 ===>> " + Thread.currentThread().getName());
        });
    }

    // Runnable 实现类
    private static class MyRunnableTask implements Runnable {
        @Override
        public void run() {
            System.out.println("子线程名称 ===>> " + Thread.currentThread().getName());
        }
    }

    /**
     * *************** 通过实现 Callable 创建线程 *****************
     */
    // 通过匿名类创建线程
    public FutureTask createThreadByCallableAnonymousClass() {
        return new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("子线程名称 ===>> " + Thread.currentThread().getName());
                return Thread.currentThread().getName();
            }
        });
    }

    // 通过 Lambda 表达式创建线程
    public FutureTask createThreadByCallableLambda() {
        return new FutureTask<>(() -> {
            System.out.println("子线程名称 ===>> " + Thread.currentThread().getName());
            return Thread.currentThread().getName();
        });
    }

    // Callable 实现类
    private static class MyCallableTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("子线程名称 ===>> " + Thread.currentThread().getName());
            return Thread.currentThread().getName();
        }
    }

    public static void main(String[] args) {
        System.out.println("主线程名称 ===>> " + Thread.currentThread().getName());

        // 继承 Thread 创建
        Thread thread = new MyThreadTask();
        thread.start();

        // 实现 Runnable - 匿名类
        new ThreadCreationTest().createThreadByRunnableAnonymousClass().start();

        // 实现 Runnable - Lambda 表达式
        new ThreadCreationTest().createThreadByRunnableLambda().start();

        // 实现 Runnable - Runnable 实现类
        new Thread(new MyRunnableTask()).start();

        // 实现 Callable - 匿名类
        FutureTask futureTask = new ThreadCreationTest().createThreadByCallableAnonymousClass();
        new Thread(futureTask).start();

        // 实现 Callable - Lambda 表达式
        FutureTask futureTask1 = new ThreadCreationTest().createThreadByCallableLambda();
        new Thread(futureTask1).start();

        // 实现 Callable - Callable 实现类
        FutureTask futureTask2 = new FutureTask(new MyCallableTask());
        new Thread(futureTask2).start();
    }
}
