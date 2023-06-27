package org.sijinghua.concurrent.chap04;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class CopyOnWriteArrayListTest {
    /**
     * 简单测试一下用 CopyOnWriteArrayList 把迭代器换成 for 的情况
     */
    public static final String ELEMENT_DATA = "jinghua";

    private static CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

    static {
        IntStream.range(0, 100).forEach((i) -> {
            copyOnWriteArrayList.add(ELEMENT_DATA);
        });
    }

    public static void main(String[] args) {
        AtomicInteger cnt = new AtomicInteger();
        new Thread(() -> {
            // 普通的 for 会随时遍历最新的 copyOnWriteArrayList，输出得到的最新的结果
            // 用 for (String s : copyOnWriteArrayList) 和使用迭代器一样，会先获得一个 copyOnWriteArrayList 的快照，然后遍历
            for (int i = 0; i < copyOnWriteArrayList.size(); ++i) {
                System.out.println(copyOnWriteArrayList.get(i) + cnt.getAndIncrement());
            }
        }).start();
        new Thread(() -> {
            copyOnWriteArrayList.set(99, "sijinghua");
        }).start();
    }
}
