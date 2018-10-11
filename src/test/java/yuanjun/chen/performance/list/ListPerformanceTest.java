/**
 * @Title: ListPerformanceTest.java
 * @Package: yuanjun.chen.performance.list
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年10月10日 下午12:37:25
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.performance.list;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

/**
 * @ClassName: ListPerformanceTest
 * @Description: 对LinkedList和ArrayList的一些性能测试
 * @author: 陈元俊
 * @date: 2018年10月10日 下午12:37:25
 */
public class ListPerformanceTest {
    /**
     * @Title: testLinkedList
     * @Description: LinkedList的每一个元素都有prev,next，value，self四个成员 因此对GC的压力比较大
     * @return: void
     */
    @Test
    public void testLinkedList() {
        final int PREFILL_COUNT = 100_000;
        final int LOOP_COUNT = 100_000_000;
        final LinkedList<Integer> lst = new LinkedList<>();
        final Integer val = 1;
        for (int i = 0; i < PREFILL_COUNT; ++i) {
            lst.add(35);
        }
        // start measuring time here<br/>
        Long t1 = System.currentTimeMillis();
        for (int i = 0; i < LOOP_COUNT; ++i) {
            for (int j = 0; j < 5; ++j) {
                lst.addFirst(val);
            }
            for (int j = 0; j < 5; ++j) {
                lst.removeLast();
            }
        }
        Long t2 = System.currentTimeMillis();
        System.out.println("Time is " + (t2 - t1) + "ms");
    }

    @Test
    public void testArrayDeque() {
        final int PREFILL_COUNT = 100_000;
        final int LOOP_COUNT = 100_000_000;
        final ArrayDeque<Integer> lst = new ArrayDeque<>(PREFILL_COUNT);
        final Integer val = 1;
        for (int i = 0; i < PREFILL_COUNT; ++i) {
            lst.add(35);
        }
        // start measuring time here<br/>
        Long t1 = System.currentTimeMillis();
        for (int i = 0; i < LOOP_COUNT; ++i) {
            for (int j = 0; j < 5; ++j) {
                lst.addFirst(val);
            }
            for (int j = 0; j < 5; ++j) {
                lst.pop();
            }
        }
        Long t2 = System.currentTimeMillis();
        System.out.println("Time is " + (t2 - t1) + "ms");
    }

    @Test
    public void testLinkedList2() {
        Long t1 = System.currentTimeMillis();
        final List<Integer> lst = new LinkedList<>();
        for (int i = 0; i < 100000; ++i) {
            lst.add(i);
        }
        long sum = 0;
        for (int i = 0; i < 100000; ++i) {
            sum += lst.get(i);
        }
        System.out.println("sum = " + sum);
        Long t2 = System.currentTimeMillis();
        System.out.println("Time is " + (t2 - t1) + "ms");
    }
}
