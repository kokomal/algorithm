package yuanjun.chen.base.container;

import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import yuanjun.chen.base.common.RandomGenner;
import yuanjun.chen.base.exception.QueueOverflowException;

/**
 * @author 陈元俊
 */
public class MyQueueTest {
    private static final Logger logger = LogManager.getLogger(MyQueueTest.class);

    @Test
    public void testDequeue() throws Exception {
        MyQueue<Float> q = genMyQueue(100, 200, Float.class); // 生成一个满的queue
        try {
            q.enqueue(11.11f); // 再次enqueue
        } catch (QueueOverflowException e) {
            logger.error("enqueue extra failed " + e.getMsg());
        }

        StringBuilder sb = new StringBuilder("["); // 全部dequeue出来
        while (!q.isEmpty()) {
            Float x = q.dequeue();
            sb.append(x).append(", ");
        }
        String str = sb.substring(0, sb.length() - 2);
        logger.info("outputdata is = " + str + "]");
    }

    private static <T> MyQueue<T> genMyQueue(int size, int bound, Class<T> clazz) throws Exception {
        MyQueue<T> myqueue = new MyQueue<>(size);
        T[] arr1 = RandomGenner.generateRandomTArray(size, 0, bound, clazz);
        logger.info("input data is = " + Arrays.toString(arr1));
        for (T ob : arr1) {
            myqueue.enqueue(ob);
        }
        return myqueue;
    }
}
