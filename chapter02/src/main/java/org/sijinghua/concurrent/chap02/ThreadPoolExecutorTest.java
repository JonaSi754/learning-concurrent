package org.sijinghua.concurrent.chap02;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {

    private static ThreadPoolExecutor threadPool;

    static {
        threadPool = new ThreadPoolExecutor(3, 3, 30,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(5));
    }

    public static void main(String[] args) {

        System.out.println("主线程名称 ===>> " + Thread.currentThread().getName());

        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程名称 ===>> " + Thread.currentThread().getName());
            }
        });

        threadPool.execute(() -> {
            System.out.println("主线程名称 ===>> " + Thread.currentThread().getName());
        });

        threadPool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("主线程名称 ===>> " + Thread.currentThread().getName());
                return Thread.currentThread().getName();
            }
        });

        threadPool.submit(() -> {
            System.out.println("主线程名称 ===>> " + Thread.currentThread().getName());
            return Thread.currentThread().getName();
        });
    }
}
