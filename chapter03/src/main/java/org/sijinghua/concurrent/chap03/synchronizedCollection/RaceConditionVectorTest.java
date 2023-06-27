package org.sijinghua.concurrent.chap03.synchronizedCollection;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class RaceConditionVectorTest {

    private static final String ELEMENT_DATA = "jinghua";

    private static final int THREAD_COUNT = 400;

    private static final int TOTAL_COUNT = 5000;

    private static Vector<String> vector = new Vector<>();

    /**
     * 解决竞态问题也很简单，在这个复合操作上上锁就可以了
     */
    public static void addIfEmpty() {
        synchronized (vector) {
            if (vector.isEmpty()) {
                vector.add(ELEMENT_DATA);
            }
        }
    }

    /**
     * 理论上是应该出现竞态问题的，因为判空和 add 之间并没有上锁，是有可能重复 add 的
     * 但是在实际测试中并没有出现
     */
    public static void main1(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(THREAD_COUNT);
        final CountDownLatch countDownLatch = new CountDownLatch(TOTAL_COUNT);
        for (int i = 0; i < TOTAL_COUNT; ++i) {
            threadPool.execute(() -> {
                try {
                    semaphore.acquire();
                    addIfEmpty();
                    semaphore.release();
                } catch (Exception e) {
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        threadPool.shutdown();
        System.out.println("vector 的最终大小 ===>> " + vector.size());
    }

    /**
     * 使用迭代器遍历同步集合也会出现线程安全问题，因为迭代器迭代本身也是一种复合操作
     * 如果在迭代器迭代过程中进行了修改，会抛出 ConcurrentModificationException
     */
    private static List<String> list = Collections.synchronizedList(new ArrayList<>());

    static {
        IntStream.range(0, 100).forEach((i) -> {
            list.add(ELEMENT_DATA);
        });
    }

    private static void ergodicArrayList() {
        synchronized (list) {
            Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                printElement(iterator.next());
            }
        }
    }

    private static void removeElement() {
        list.remove(ELEMENT_DATA);
    }

    private static void printElement(String str) {
        System.out.println("输出的元素 ===>> " + str);
    }

    public static void main2(String[] args) {
        new Thread(RaceConditionVectorTest::ergodicArrayList, "read-thread").start();
        new Thread(RaceConditionVectorTest::removeElement, "write-thread").start();
    }
}
