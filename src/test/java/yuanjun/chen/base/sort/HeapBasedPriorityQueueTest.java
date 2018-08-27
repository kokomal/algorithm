/**
 * @Title: HeapBasedPriorityQueue.java
 * @Package: yuanjun.chen.base.sort
 * @Description: 优先级队列测试
 * @author: 陈元俊
 * @date: 2018年7月18日 下午3:56:03
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.sort;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import yuanjun.chen.base.common.DispUtil;
import yuanjun.chen.base.common.RandomGenner;
import yuanjun.chen.base.common.SortOrderEnum;
import yuanjun.chen.base.container.HeapBasedPriorityQueue;

/**
 * @ClassName: HeapBasedPriorityQueueTest
 * @Description: 优先级队列测试
 * @author: 陈元俊
 * @date: 2018年7月18日 下午3:56:03
 */
public class HeapBasedPriorityQueueTest {
    private static final Logger logger = LogManager.getLogger(HeapBasedPriorityQueueTest.class);

    @Test
    public void testHeapBasedPriorityQueue() throws Exception {
        Integer[] initArray = RandomGenner.generateRandomTArray(20, 0, 100, Integer.class);
        HeapBasedPriorityQueue hbpq = new HeapBasedPriorityQueue(initArray, SortOrderEnum.DESC); // 小根堆
        displayBoth1Dand2D(hbpq);
        if (initArray.length > 8) {
            Integer eighth = hbpq.peekAt(8);
            Integer newEighth = eighth - 40;
            boolean x = hbpq.decreaseKey(8, newEighth);
            logger.info("decrease the eighth val== " + eighth + " to new val== " + newEighth + " and res = " + x);
            displayBoth1Dand2D(hbpq);
        }
        int keyToBeInserted = 30;
        DispUtil.embed(40, '=', "Before Insert== " + keyToBeInserted);
        displayBoth1Dand2D(hbpq);
        hbpq.insertKey(keyToBeInserted);
        DispUtil.embed(40, '=', "inserting...");
        displayBoth1Dand2D(hbpq);
        DispUtil.embed(40, '=', "End Insert== " + keyToBeInserted);
    }

    private void displayBoth1Dand2D(HeapBasedPriorityQueue hbpq) {
        DispUtil.embed(30, '*', "the size = " + hbpq.size());
        hbpq.peakAll2D();
        DispUtil.embed(30, '*', "the flat display is below");
        hbpq.peakAll1D();
    }

    @Test
    public void testPop() throws Exception {
        Integer[] initArray = RandomGenner.generateRandomTArray(18, 0, 100, Integer.class);
        HeapBasedPriorityQueue hbpq = new HeapBasedPriorityQueue(initArray, SortOrderEnum.DESC); // 小根堆
        DispUtil.embed(40, '=', "Before pop");
        displayBoth1Dand2D(hbpq);
        Integer pop1 = hbpq.pop();
        DispUtil.embed(30, '*', "after pop val " + pop1);
        displayBoth1Dand2D(hbpq);
        pop1 = hbpq.pop();
        DispUtil.embed(30, '*', "after pop val " + pop1);
        displayBoth1Dand2D(hbpq);
        DispUtil.embed(40, '=', "End pop");
    }

    @Test
    public void testPop2() throws Exception {
        Integer[] initArray = RandomGenner.generateRandomTArray(2, 0, 100, Integer.class);
        HeapBasedPriorityQueue hbpq = new HeapBasedPriorityQueue(initArray, SortOrderEnum.DESC); // 小根堆
        DispUtil.embed(40, '=', "Before pop");
        displayBoth1Dand2D(hbpq);
        Integer pop1 = hbpq.pop();
        logger.info("after pop val " + pop1);
        displayBoth1Dand2D(hbpq);
        Integer pop2 = hbpq.pop();
        logger.info("after pop val " + pop2);
        displayBoth1Dand2D(hbpq);
        DispUtil.embed(40, '=', "End pop");
    }

    @Test
    public void testDelete() throws Exception {
        Integer[] initArray = RandomGenner.generateRandomTArray(20, 0, 100, Integer.class);
        HeapBasedPriorityQueue hbpq = new HeapBasedPriorityQueue(initArray, SortOrderEnum.DESC); // 小根堆
        DispUtil.embed(40, '=', "Before delete");
        displayBoth1Dand2D(hbpq);
        Integer x = hbpq.deleteKey(3); // 删除第3号元素
        DispUtil.split(80, '-');
        logger.info("after delete val " + x);
        displayBoth1Dand2D(hbpq);
        x = hbpq.deleteKey(3);
        DispUtil.split(80, '-');
        logger.info("after delete val " + x);
        displayBoth1Dand2D(hbpq);
        DispUtil.embed(40, '=', "End delete");
    }
}
