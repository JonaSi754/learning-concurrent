package org.sijinghua.concurrent.chap01;

public class ProcessThreadTest {
    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {}
        }, "thread-sijinghua-001").start();
        System.out.println("程序启动成功...");
    }
}
