package org.sijinghua.concurrent.chap03.synchronizedCollection;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Stack;
import java.util.concurrent.TimeUnit;

@Fork(1)
@Measurement(iterations = 5)
@Warmup(iterations = 3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Threads(value = 5)
@State(Scope.Benchmark)
public class StackTest {

    private static final String ELEMENT_DATA = "jinghua";

    private Stack<String> stack;

    @Setup
    public void setup() {
        this.stack = new Stack<>();
        stack.add(ELEMENT_DATA);
    }

    @Benchmark
    public void pushElement() {
        stack.push(ELEMENT_DATA);
    }

    @Benchmark
    public void peekElement() {
        stack.peek();
    }

    @Benchmark
    public void searchElement() {
        stack.search(ELEMENT_DATA);
    }

    public static void main(String[] args) throws RunnerException {
        final Options opts = new OptionsBuilder().include(StackTest.class.getSimpleName()).build();
        new Runner(opts).run();
    }
}
