package org.sijinghua.concurrent.chap03.synchronizedPackageType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class HashMapTest {
    // 并发执行的线程数
    public static final int THREAD_COUNT = 200;
    // 执行总次数
    public static final int TOTAL_COUNT = 5000;
    // HashMap
    // private static Map<Integer, Integer> map = new HashMap<>();
    private static Map<Integer, Integer> map = Collections.synchronizedMap(new HashMap<>());

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(THREAD_COUNT);
        final CountDownLatch countDownLatch = new CountDownLatch(TOTAL_COUNT);
        for (int i = 0; i < TOTAL_COUNT; ++i) {
            final int count = i;
            threadPool.execute(() -> {
                try {
                    semaphore.acquire();  // 是否允许执行
                    map.put(count, count);
                    semaphore.release();
                } catch (Exception e) {
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        threadPool.shutdown();
        System.out.println("map 的最终大小 ===>> " + map.size());
    }
}
