/**
 * @Title: MyDeque.java
 * @Package: yuanjun.chen.base.container
 * @Description: 双端队列
 * @author: 陈元俊
 * @date: 2018年7月30日 上午8:35:44
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.container;

import yuanjun.chen.base.exception.QueueOverflowException;
import yuanjun.chen.base.exception.QueueUnderflowException;

/**
 * @ClassName: MyDeque
 * @Description: 双端队列
 * @author: 陈元俊
 * @date: 2018年7月30日 上午8:35:44
 */
public class MyDeque<T extends Object> extends MyQueue<T> {
    public MyDeque() {
    }

    public MyDeque(int initSize) {
        super(initSize);
    }

    /** EnqueueHead影响head. */
    public void enqueueHead(T item) throws QueueOverflowException {
        if (isFull()) {
            throw new QueueOverflowException("queue full");
        }
        this.head = stepBack(head);
        this.vals[this.head] = item;
    }

    /** DequeueTail影响tail. */
    public T dequeueTail() throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException("queue underflow");
        }
        int real_tail = stepBack(this.tail);
        T x = this.vals[real_tail];
        this.tail = real_tail;
        return x;
    }

    public static void main(String[] args) throws Exception {
        MyDeque<Integer> myqueue = new MyDeque<>(4); // 实际上是5个空间
        myqueue.enqueue(12);
        myqueue.enqueue(13);
        myqueue.enqueue(14);
        myqueue.enqueue(15);
        System.out.println(myqueue.dequeue()); // 12
        System.out.println(myqueue.dequeue()); // 13
        System.out.println(myqueue.dequeue()); // 14
        System.out.println(myqueue.dequeue()); // 15
        myqueue.enqueueHead(33);
        myqueue.enqueueHead(44);
        System.out.println(myqueue.dequeue()); // 44
        System.out.println(myqueue.dequeue()); // 33
        /* --------------------------------------------- */
        myqueue.enqueue(55);
        myqueue.enqueueHead(66); // 66---55
        myqueue.enqueue(77); // 66---55---77
        myqueue.enqueueHead(88); // 88---66---55---77
        while (!myqueue.isEmpty()) {
            System.out.print("-->" + myqueue.dequeueTail()); // -->77-->55-->66-->88
        }
        /* --------------------------------------------- */
        myqueue.enqueue(55);
        myqueue.enqueueHead(66); // 66---55
        myqueue.enqueue(77); // 66---55---77
        myqueue.enqueueHead(88); // 88---66---55---77
        System.out.println();
        while (!myqueue.isEmpty()) {
            System.out.print("-->" + myqueue.dequeue()); // -->88-->66-->55-->77
        }
    }
}
