package org.sijinghua.concurrent.chap02;

import org.junit.Test;

public class ThreadGroupTest {

    /**
     * 一级关联（只关联线程）
     */
    @Test
    public void groupTest1() throws InterruptedException {
        ThreadGroup threadGroup = new ThreadGroup("jinghua-thread-group");
        Thread thread1 = new Thread(threadGroup, () -> {
            System.out.println("子线程名称 ===>> " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(threadGroup, () -> {
            System.out.println("子线程名称 ===>> " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
        Thread.sleep(500);
        System.out.println("线程组中活跃的线程数为 ===>> " + threadGroup.activeGroupCount());
        System.out.println("主线程名称 ===>> " + Thread.currentThread().getName());
        Thread.sleep(5000);
    }
}
