package org.sijinghua.concurrent.chap03.synchronizedCollection;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * 以下
 * 表示同时开启 5 个线程，在线程共享实例的模式下预热 3 次，执行 5 次，并输出每种方法的平均响应时间
 */
@Fork(1)
@Measurement(iterations = 5)
@Warmup(iterations = 3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Threads(value = 5)
@State(Scope.Benchmark)
public class VectorTest {

    private static final String ELEMENT_DATA = "jinghua";

    private Vector<String> vector;

    @Setup
    public void setup() {
        this.vector = new Vector<>();
        vector.add(ELEMENT_DATA);
    }

    @Benchmark
    public void addElement() {
        vector.add(ELEMENT_DATA);
    }

    @Benchmark
    public void getElement() {
        vector.get(0);
    }

    @Benchmark
    public void removeElement() {
        vector.remove(ELEMENT_DATA);
    }

    public static void main(String[] args) throws RunnerException {
        final Options opts = new OptionsBuilder().include(VectorTest.class.getSimpleName()).build();
        new Runner(opts).run();
    }
}
