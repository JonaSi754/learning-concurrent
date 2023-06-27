package org.sijinghua.concurrent.chap02;

import org.junit.Test;

import java.util.Date;
import java.util.stream.IntStream;

public class ThreadOperationTest {
    /**
     * 线程启动
     * public synchronized void start()
     */
    @Test
    public void operationTest1() {
        Thread thread = new Thread(() -> {});
        thread.start();
    }

    /**
     * 线程休眠
     * 这部分代码在 @Test 里是不能正常运行的
     * 原因是 junit 的单元测试是不支持多线程的
     *
     * 三种方法：
     * 1. 放在 main 里测试
     * 2. 使用 thread.join() 等待当前进程结束后再退出
     * 3. 调用 thread 的 start() 方法后让主线程也休眠一段时间
     */
    @Test
    public void operationTest2() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("当前时间为 ===>> " + new Date());
            System.out.println("子线程名称 ===>> " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("当前时间为 ===>> " + new Date());
        });
        thread.start();
        System.out.println("主线程名称 ===>> " + Thread.currentThread().getName());
        // Thread.sleep(5000);
        // thread.join();
    }

    /**
     * 中断线程
     * public void interrupt()
     *
     * 两种情况：
     * 1. 线程被 Object.wait() Thread.join() 或 Thread.sleep() 阻塞时，调用 interrupt() 会抛出
     *    InterruptedException 异常并清空中断标记，此时注意捕获并妥善处理
     * 2. 线程在运行时调用了当前线程的 interrupt() 方法，则当前线程中断标记为 true，需要通过 Thread 类
     *    的 isInterrupted() 方法来检查是否已经中断
     */
    @Test
    public void operationTest3() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("子线程名称 ===>> " + Thread.currentThread().getName());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("中断休眠中的线程会抛出异常，清空中断标记，捕获异常后重新设置中断标记");
                Thread.currentThread().interrupt();
            }
        });
        thread.start();
        // 保证子线程启动
        Thread.sleep(500);
        System.out.println("主线程中断子线程");
        // 中断子线程
        thread.interrupt();
        System.out.println("主线程名称 ===>> " + Thread.currentThread().getName());
        thread.join();
    }

    /**
     * 中断线程的第二种情况
     */
    @Test
    public void operationTest4() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("子线程名称 ===>> " + Thread.currentThread().getName());
            while (!Thread.currentThread().isInterrupted()) {}
            System.out.println("子线程退出了while循环");
        });
        thread.start();
        // 保证子线程已经启动
        Thread.sleep(500);
        System.out.println("在主线程中断子线程");
        // 中断子线程
        thread.interrupt();
        System.out.println("主线程名称 ===>> " + Thread.currentThread().getName());
        thread.join();
    }

    /**
     * 等待与通知
     *
     * 包含以下几个方法
     * public final void wait() throws InterruptedException
     * public native void wait(long timeout) throws InterruptedException
     * public final void wait(long timeout, int nanos) throws InterruptedException
     * public final native void notify();
     * public final native void notifyAll();
     * 需要注意的是以上方法均在 Object 类中，而不在 Thread 类中
     */
    @Test
    public void operationTest5() throws InterruptedException {
        final Object obj = new Object();
        Thread thread = new Thread(() -> {
            System.out.println("子线程名称 ===>> " + Thread.currentThread().getName());
            System.out.println("子线程等待");
            synchronized (obj) {
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("子线程被唤醒");
        });
        thread.start();
        // 保证子线程启动
        Thread.sleep(500);
        System.out.println("主线程通知子线程");
        synchronized (obj) {
            obj.notify();
        }
        System.out.println("主线程名称 ===>> " + Thread.currentThread().getName());
    }

    /**
     * 挂起与执行
     *
     * Thread 类中有两个方法 suspend() 和 resume()，但是都是 Deprecated 方法
     * 类似与 Object 中的 wait() 和 notify()，不推荐使用
     */

    /**
     * 等待结束与谦让
     *
     * Thread 还提供了等待线程结束的 join 方法和让其他线程先执行的 yield 方法
     * public final void join() throws InterruptedException
     * public final synchronized void join(long millis) throws InterruptedException
     * public final synchronized void join(long millis, int nanos) throws InterruptedException
     * public static native void yield()
     */
    private int sum = 0;
    @Test
    public void operationTest6() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("子线程名称 ===>> " + Thread.currentThread().getName());
            IntStream.range(0, 1000).forEach((i) -> {sum += 1;});
        });
        thread.start();
        thread.join();
        System.out.println("主线程获取的结果为 ===>> " + sum);
        System.out.println("主线程名称 ===>> " + Thread.currentThread().getName());
    }

    /**
     * 守护线程
     *
     * 可以通过 Thread 类提供的 setDaemon() 方法来设置一个线程是否是守护线程
     */
}
