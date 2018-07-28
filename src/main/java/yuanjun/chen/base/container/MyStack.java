/**
 * 基本栈容器
 */
package yuanjun.chen.base.container;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import yuanjun.chen.base.exception.StackUnderflowException;

/**
 * @author 陈元俊 基本栈容器
 */
public class MyStack<T extends Object> {
	private static final Logger logger = LogManager.getLogger(MyStack.class);
	    
	private T[] vals;
	private int elements = 0;
	private static final int INIT_SIZE = 16;

	@SuppressWarnings("unchecked")
	public MyStack() {
		super();
		this.vals = (T[]) new Object[INIT_SIZE];
		this.elements = 0;
	}

	@SuppressWarnings("unchecked")
	public MyStack(int initSize) {
		super();
		this.vals = (T[]) new Object[initSize];
		this.elements = 0;
	}

	public boolean empty() {
		return this.elements == 0;
	}

	public T push(T item) {
		ensureCapacity(this.elements + 1);
		return this.vals[this.elements++] = item;
	}

	public T peek() throws StackUnderflowException {
		if (this.empty())
			throw new StackUnderflowException("underflow");
		return this.vals[this.elements - 1];
	}

	public T pop() throws StackUnderflowException {
		if (this.empty())
			throw new StackUnderflowException("underflow");
		this.elements--;
		return this.vals[this.elements];
	}

	@SuppressWarnings("unchecked")
	public void shrinkToFit() {
		if (this.empty()) return;
		T[] newVals = (T[]) new Object[this.elements];
		System.arraycopy(this.vals, 0, newVals, 0, this.elements);
		this.vals = newVals;
	}
	
	public int capacity() {
		return this.vals.length;
	}
	
	/**
	 * @param i
	 */
	@SuppressWarnings("unchecked")
	private void ensureCapacity(int i) {
		if (this.vals.length >= i) {
			return;
		}
		logger.info("expanding capacity from " + this.vals.length + " to " + 2 * this.vals.length);
		T[] newVals = (T[]) new Object[2 * this.vals.length];
		System.arraycopy(this.vals, 0, newVals, 0, this.vals.length);
		this.vals = newVals;
	}

	public static void main(String[] args) throws StackUnderflowException {
		MyStack<Integer> mystack = new MyStack<>(8);
		mystack.push(11);
		mystack.push(12);
		mystack.push(13);
		mystack.push(14);
		mystack.push(15);
		System.out.println(mystack.peek()); // 15
		int a = mystack.pop(); // 15
		int b = mystack.pop(); // 14
		System.out.println("a = " + a + ", b=" + b); // a=15,b=14
		System.out.println(mystack.peek()); // 13
		System.out.println("is it empty ? " + mystack.empty()); // false
		for (int i = 20; i < 30; i++) {
			mystack.push(i); // expanding from 8 to 16
		}
		System.out.println("now size = " + mystack.capacity());
		mystack.shrinkToFit();
		System.out.println("after shrink size = " + mystack.capacity());
		
		System.out.println("peek " + mystack.peek()); // 29
		mystack.push(99); // resize again
		while (!mystack.empty()) {
			System.out.print("==>" + mystack.pop());
		}
		System.out.println("\nis it empty ? " + mystack.empty()); // true
		try {
			mystack.pop();
		} catch (StackUnderflowException e) {
			System.out.println("gotcha!--" + e.getMsg());
		}
		try {
			mystack.peek();
		} catch (StackUnderflowException e) {
			System.out.println("gotcha!--" + e.getMsg());
		}
	}
}
