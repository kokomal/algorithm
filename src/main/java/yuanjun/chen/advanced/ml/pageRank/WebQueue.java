package yuanjun.chen.advanced.ml.pageRank;

import java.util.LinkedList;

/*
 * code by 邦柳
 * 
 * 爬虫的队列
 */
public class WebQueue {
    private LinkedList<Object> queue = new LinkedList<>();

    public void enQueue(Object t) {
        queue.addLast(t);
    }

    public Object deQueue() {
        return queue.removeFirst();
    }

    public boolean isQueueEmpty() {
        return queue.isEmpty();
    }

    public boolean contians(Object t) {
        return queue.contains(t);
    }

    public boolean empty() {
        return queue.isEmpty();
    }

    public int getNum() {
        return queue.size();
    }
}
