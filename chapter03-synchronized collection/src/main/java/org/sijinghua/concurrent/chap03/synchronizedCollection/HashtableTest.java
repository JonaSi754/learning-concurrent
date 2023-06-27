package org.sijinghua.concurrent.chap03.synchronizedCollection;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

@Fork(1)
@Measurement(iterations = 5)
@Warmup(iterations = 3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Threads(value = 5)
@State(Scope.Benchmark)
public class HashtableTest {

    private static final String ELEMENT_DATA = "jinghua";

    private Hashtable<String, String> hashtable;

    @Setup
    public void setup() {
        this.hashtable = new Hashtable<>();
    }

    @Benchmark
    public void putElement() {
        hashtable.put(ELEMENT_DATA, ELEMENT_DATA);
    }

    @Benchmark
    public void getElement() {
        hashtable.get(ELEMENT_DATA);
    }

    @Benchmark
    public void removeElement() {
        hashtable.remove(ELEMENT_DATA);
    }

    public static void main(String[] args) throws RunnerException {
        final Options opts = new OptionsBuilder().include(HashtableTest.class.getSimpleName()).build();
        new Runner(opts).run();
    }
}
