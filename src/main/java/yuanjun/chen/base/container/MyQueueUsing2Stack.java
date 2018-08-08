/**
 * @Title: MyQueueUsing2Stack.java
 * @Package: yuanjun.chen.base.container
 * @Description: MyQueueUsing2Stack
 * @author: 陈元俊
 * @date: 2018年7月30日 上午9:15:36
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.container;

import yuanjun.chen.base.exception.QueueOverflowException;
import yuanjun.chen.base.exception.QueueUnderflowException;

/**
 * @ClassName: MyQueueUsing2Stack
 * @Description: 采用2个stack实现的queue，容量为2n，但有条件才能实现
 * @author: 陈元俊
 * @date: 2018年7月30日 上午9:15:36
 */
public class MyQueueUsing2Stack<T extends Object> {
    private MyStack<T> stack1;
    private MyStack<T> stack2;
    private int initSize;
    private static final int INIT_SIZE = 16;

    public MyQueueUsing2Stack(int initSize) {
        if (initSize < 0) {
            initSize = INIT_SIZE;
        }
        this.initSize = initSize;
        stack1 = new MyStack<>(this.initSize);
        stack2 = new MyStack<>(this.initSize);
    }

    public MyQueueUsing2Stack() {
        this.initSize = INIT_SIZE;
        stack1 = new MyStack<>(this.initSize);
        stack2 = new MyStack<>(this.initSize);
    }

    /** 无脑将新元素压入stack1即可 如果stack1满了将无元素可压（可以动stack2的脑筋吗？）. */
    public void enqueue(T item) throws Exception {
        if (stack1.isFull()) {
            throw new QueueOverflowException("queue full");
        }
        stack1.push(item);
    }

    /** ★★★核心思想★★★ 可以将stack2类比为jvm的"老年代",stack1为"伊甸" 能存在于stack2的元素，必然在stack1里面待过，并且年龄不小了. */
    public T dequeue() throws Exception {
        if (stack1.isEmpty() && stack2.isEmpty()) {
            throw new QueueUnderflowException("queue empty");
        }
        if (!stack2.isEmpty()) { // stack2有容量，则直接从stack2弹出来
            return stack2.pop();
        }
        while (!stack1.isEmpty()) { // 说明stack2为空，那么从stack1里面弹出压到stack2，取stack2栈顶即可
            T pp = stack1.pop();
            stack2.push(pp);
        }
        return stack2.pop();
    }

    public static void main(String[] args) throws Exception {
        MyQueueUsing2Stack<Integer> myq2s = new MyQueueUsing2Stack<Integer>(20);
        myq2s.enqueue(12);
        myq2s.enqueue(13);
        myq2s.enqueue(14);
        myq2s.enqueue(15);
        System.out.println(myq2s.dequeue()); // 12
        System.out.println(myq2s.dequeue()); // 13
        System.out.println(myq2s.dequeue()); // 14
        System.out.println(myq2s.dequeue()); // 15
        try {
            myq2s.dequeue();
        } catch (QueueUnderflowException e) {
            System.out.println(e.getMsg());
        }
        myq2s.enqueue(44);
        myq2s.dequeue();
        try {
            myq2s.dequeue();
        } catch (QueueUnderflowException e) {
            System.out.println(e.getMsg());
        }
    }
}
