/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yuanjun.chen.perf;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**   
 * @ClassName: DemoBenchmark   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 陈元俊 
 * @SpecialThanksTo hjungnitsch
 * @date: 2018年9月10日 下午3:05:28  
 */
public class DemoBenchmark {

    public static void main(String... args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + DemoBenchmark.class.getSimpleName() + ".*")
                .forks(1)
                .warmupForks(0)
                .warmupIterations(20)
                .measurementIterations(20)
                .warmupBatchSize(1)
                .measurementBatchSize(1)
                .timeUnit(TimeUnit.MILLISECONDS)
                .mode(Mode.AverageTime)
                .build();
        new Runner(opt).run();
    }

    @State(Scope.Thread)
    public static class MyState {

        public List<Integer> zahlen;
        public int[] zahlenArray = new int[10000];

        @Setup(Level.Trial)
        public void doSetup() {
            zahlenArray = IntStream.range(1, 10000).toArray();
            zahlen = Arrays.stream(zahlenArray).boxed().collect(Collectors.toList());
        }

        @TearDown(Level.Trial)
        public void doTearDown() {
            zahlen = null;
            zahlenArray = null;
        }
    }

    @Benchmark
    public int calculateSumStream(MyState state) {
        return state.zahlen.stream().mapToInt(i -> i).sum();
    }


    @Benchmark
    public int calculateMaxStream(MyState state) {
        return state.zahlen.stream().mapToInt(i -> i).max().getAsInt();
    }

    @Benchmark
    public int calculateMaxCollections(MyState state) {
        return Collections.max(state.zahlen);
    }

    @Benchmark
    public double calculateAverageStream(MyState state) {
        return state.zahlen.stream().mapToInt(i -> i).average().getAsDouble();
    }

    @Benchmark
    public int calculateSumStreamPrimitive(MyState state) {
        return Arrays.stream(state.zahlenArray).sum();
    }
}
