package org.sijinghua.concurrent.chap03;

import java.util.HashSet;
import java.util.Set;

/**
 * 显然，这种方式虽然实现了线程安全，但开发效率无疑是低效但
 */
public class SafeHashSet<E> {

    private volatile Set<E> set = new HashSet<>();

    public synchronized boolean add(E e) {
        return set.add(e);
    }

    public synchronized boolean isEmpty() {
        return set.isEmpty();
    }

    public synchronized boolean remove(E e) {
        return set.remove(e);
    }

    public synchronized void clear() {
        set.clear();
    }
}
