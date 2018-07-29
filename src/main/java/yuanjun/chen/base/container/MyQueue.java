/**
 * 
 */
package yuanjun.chen.base.container;

import yuanjun.chen.base.exception.QueueOverflowException;
import yuanjun.chen.base.exception.QueueUnderflowException;

/**
 * @author 陈元俊
 * tail 为虚， head为实
 */
public class MyQueue<T extends Object> {
	private T[] vals;
	private int head = 0;
	private int tail = 0;
	private static final int INIT_SIZE = 16;
	
	@SuppressWarnings("unchecked")
	public MyQueue() {
		super();
		this.vals = (T[]) new Object[INIT_SIZE];
		this.head = 0;
		this.tail = 0;
	}
	
	@SuppressWarnings("unchecked")
	public MyQueue(int initSize) {
		super();
		if (initSize < 0) {
			initSize = INIT_SIZE;
		}
		this.vals = (T[]) new Object[initSize + 1]; // 1个额外空间存放安全距离
		this.head = 0;
		this.tail = 0;
	}
	
	// enqueue影响tail，并且tail永远保持一个虚的占位
	public void enqueue(T item) throws QueueOverflowException {
		if (isFull()) throw new QueueOverflowException("queue full");
		this.vals[this.tail] = item;
		this.tail = stepAhead(this.tail);
		// tellHeadAndTail();
	}

	// dequeue仅影响head，head实打实
	public T dequeue() throws QueueUnderflowException {
		if (isEmpty()) throw new QueueUnderflowException("queue underflow");
		T x = this.vals[this.head];
		this.head = stepAhead(this.head);
		// tellHeadAndTail();
		return x;
	}
	
	public void tellHeadAndTail() {
		System.out.println("head = " + this.head + ", tail = " + this.tail);
	}
	
	public boolean isFull() {
		return stepAhead(this.tail) == this.head;
	}
	
	public boolean isEmpty() {
		return this.head == this.tail;
	}
	
	public int stepAhead(int pos) {
		return (pos + 1) % vals.length;
	}
	
	public static void main(String[] args) throws Exception {
		MyQueue<Integer> myqueue = new MyQueue<>(4); // 实际上是5个空间
		myqueue.enqueue(12);
		myqueue.enqueue(13);
		myqueue.enqueue(14);
		myqueue.enqueue(15);
		System.out.println(myqueue.dequeue()); // 12
		System.out.println(myqueue.dequeue()); // 13
		System.out.println(myqueue.dequeue()); // 14
		System.out.println(myqueue.dequeue()); // 15
		try {
			myqueue.dequeue();
		} catch (QueueUnderflowException e) {
			System.out.println(e.getMsg());
		}
		myqueue.enqueue(44);
		myqueue.dequeue();
		try {
			myqueue.dequeue();
		} catch (QueueUnderflowException e) {
			System.out.println(e.getMsg());
		}
	}
}
