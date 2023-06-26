package org.sijinghua.concurrent.chap02;

import org.junit.Test;

/**
 * Thread 的构造函数包括:
 *
 * public Thread(String name)
 * public Thread(ThreadGroup group, String name)
 * public Thread(Runnable target, String name)
 * public Thread(ThreadGroup group, Runnable target, String name)
 * public Thread(ThreadGroup group, Runnable target, String name, long stackSize)
 */
public class ThreadConfigTest {

    /**
     * 设置线程名称 - 通过构造函数
     */
    @Test
    public void configTest1() {
        Thread thread = new Thread(() -> {
            System.out.println("子线程名称 ===>> " + Thread.currentThread().getName());
        }, "jinghua-thread");
        thread.start();
        System.out.println("主线程名称 ===>> " + Thread.currentThread().getName());
    }

    /**
     * 设置线程名称 - 通过 set 方法
     */
    @Test
    public void configTest2() {
        Thread thread = new Thread(() -> {
            System.out.println("子线程名称 ===>> " + Thread.currentThread().getName());
        });
        thread.setName("jinghua-thread");
        thread.start();
        System.out.println("主线程名称 ===>> " + Thread.currentThread().getName());
    }

    /**
     * 设置线程组
     */
    @Test
    public void configTest3() {
        Thread thread = new Thread(new ThreadGroup("jinghua-thread-group"), () -> {
            System.out.println("子线程名称 ===>> " + Thread.currentThread().getName());
        });
        thread.start();
        System.out.println("主线程名称 ===>> " + Thread.currentThread().getName());
        System.out.println("子线程所在线程组名称 ===>> " + thread.getThreadGroup().getName());
    }

    /**
     * 设置优先级
     * public final void setPriority(int newPriority)
     */
}
