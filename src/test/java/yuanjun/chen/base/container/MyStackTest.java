/**
 * 基本栈测试
 */
package yuanjun.chen.base.container;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import yuanjun.chen.base.common.RandomGenner;
import yuanjun.chen.base.exception.StackUnderflowException;

/**
 * @author 陈元俊
 *
 */
public class MyStackTest {
	private static final Logger logger = LogManager.getLogger(MyStackTest.class);

	@Test
	public void pushMeHard() throws Exception {
		pushStackTest(50, 100, Double.class);
	}
	
	@Test
	public void testUnderflow() throws Exception {
		underflowTest(100, 200, Float.class);
	}

	public <T> void pushStackTest(int size, int bound, Class<T> clazz) throws Exception {
		MyStack<T> mystack = genMyStack(size, bound, clazz);
		logger.info("finally the capacity is: " + mystack.capacity());
		StringBuilder sb = new StringBuilder("[");
		while (!mystack.empty()) {
			sb.append(mystack.pop() + ", ");
		}
		String str = sb.substring(0, sb.length() - 2);
		logger.info("output data is = " + str + "]");
	}

	public <T> void underflowTest(int size, int bound, Class<T> clazz) throws Exception {
		MyStack<T> mystack = genMyStack(size, bound, clazz);
		T tmp = null;
		for (int i = 0; i < size; i++) { //pop size次直至empty
			mystack.pop();
		}
		logger.info("should it be empty? " + mystack.empty());
		try {
			mystack.peek(); // try to peek an empty stack
		} catch (StackUnderflowException e) {
			logger.error("gotcha---" + e.getMsg());
		}
		try {
			tmp = mystack.pop(); // try to pop an empty stack
		} catch (StackUnderflowException e) {
			logger.error("gotcha---" + e.getMsg());
		}
		mystack.shrinkToFit(); // 对于空的stack，即使capacity有值也不触发shrink操作
		logger.info("now it is empty and capacity is " + mystack.capacity());
		mystack.push(tmp);
		mystack.shrinkToFit();
		logger.info("now it is empty and capacity is " + mystack.capacity());
	}
	
	private static <T> MyStack<T> genMyStack(int size, int bound, Class<T> clazz) throws Exception {
		MyStack<T> mystack = new MyStack<>();
		T[] arr1 = RandomGenner.generateRandomTArray(size, bound, clazz);
		logger.info("input data is = " + Arrays.toString(arr1));
		for (T ob : arr1) {
			mystack.push(ob);
		}
		return mystack;
	}

}
