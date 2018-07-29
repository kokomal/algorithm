/**
 * 
 */
package yuanjun.chen.base.container;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import yuanjun.chen.base.common.RandomGenner;

/**
 * @author 陈元俊
 *
 */
public class MyQueueTest {
	private static final Logger logger = LogManager.getLogger(MyQueueTest.class);

	@Test
	public void testDequeue() throws Exception {
		MyQueue<Float> q = genMyQueue(100, 200, Float.class);
		StringBuilder sb = new StringBuilder("[");
		while (!q.isEmpty()) {
			Float x = q.dequeue();
			sb.append(x + ", ");
		}
		String str = sb.substring(0, sb.length() - 2);
		logger.info("outputdata is = " + str + "]");
	}
	
	private static <T> MyQueue<T> genMyQueue(int size, int bound, Class<T> clazz) throws Exception {
		MyQueue<T> myqueue = new MyQueue<>(size);
		T[] arr1 = RandomGenner.generateRandomTArray(size, bound, clazz);
		logger.info("input data is = " + Arrays.toString(arr1));
		for (T ob : arr1) {
			myqueue.enqueue(ob);
		}
		return myqueue;
	}
}
