/**
 * @Title: MyDequeTest.java
 * @Package: yuanjun.chen.base.container
 * @Description: 双端队列测试
 * @author: 陈元俊
 * @date: 2018年7月30日 上午8:55:31
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.container;

import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import yuanjun.chen.base.common.DirectionEnum;
import yuanjun.chen.base.common.RandomGenner;
import yuanjun.chen.base.exception.QueueOverflowException;

/**
 * @ClassName: MyDequeTest
 * @Description: 双端队列测试
 * @author: 陈元俊
 * @date: 2018年7月30日 上午8:55:31
 */
public class MyDequeTest {
    private static final Logger logger = LogManager.getLogger(MyDequeTest.class);

    @Test
    public void enqueueTest() throws Exception {
        MyDeque<Float> mydq = genHalfDeque(10, 30, Float.class, DirectionEnum.BACKWARD);
        StringBuilder sb = new StringBuilder("["); // 全部dequeue出来
        while (!mydq.isEmpty()) {
            Float x = mydq.dequeue();
            sb.append(x + ", ");
        }
        String str = sb.substring(0, sb.length() - 2);
        logger.info("after dequeue the data is" + str + "]");
    }

    @Test
    public void enqueueTest2() throws Exception {
        MyDeque<Float> mydq = genHalfDeque(10, 30, Float.class, DirectionEnum.BACKWARD);
        StringBuilder sb = new StringBuilder("["); // 全部dequeue出来
        while (!mydq.isEmpty()) {
            Float x = mydq.dequeueTail(); // 从尾部dequeue，退化成栈了
            sb.append(x + ", ");
        }
        String str = sb.substring(0, sb.length() - 2);
        logger.info("after dequeue the data is" + str + "]");
    }

    @Test
    public void enqueueTest3() throws Exception {
        MyDeque<Float> mydq = genHalfDeque(10, 30, Float.class, DirectionEnum.FORWARD);
        StringBuilder sb = new StringBuilder("["); // 全部dequeue出来
        while (!mydq.isEmpty()) {
            Float x = mydq.dequeueTail(); // 从尾部dequeue，退化成栈了
            sb.append(x + ", ");
        }
        String str = sb.substring(0, sb.length() - 2);
        logger.info("after dequeue the data is" + str + "]");
    }

    /**
     * @Title: genHalfDeque
     * @Description: 生成半满的dequeue
     * @throws Exception
     * @return: MyDeque<T>
     */
    private <T extends Object> MyDeque<T> genHalfDeque(int size, int bound, Class<T> clazz, DirectionEnum direct)
            throws Exception {
        T[] arr1 = RandomGenner.generateRandomTArray(size, bound, clazz);
        logger.info("before enqueue orig data " + Arrays.toString(arr1));
        MyDeque<T> mydq = new MyDeque<>(size * 2); // deque容量20，传入10个元素
        if (DirectionEnum.BACKWARD.equals(direct)) {
            for (T f : arr1) {
                mydq.enqueue(f);
            }
        } else {
            for (T f : arr1) {
                mydq.enqueueHead(f);
            }
        }
        return mydq;
    }
}
