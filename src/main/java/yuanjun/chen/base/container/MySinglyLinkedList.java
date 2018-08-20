package yuanjun.chen.base.container;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yuanjun.chen.base.exception.IteratorInvalidException;

/** 单链表. */
public class MySinglyLinkedList<T extends Object> {
    private static final Logger logger = LogManager.getLogger(MySinglyLinkedList.class);

    private Node<T> head;
    private Node<T> tail;

    public MySinglyLinkedList() {
        head = null;
        tail = null;
    }

    /**
     * @ClassName: SLLIterator
     * @Description: 添加容器的迭代器
     * @author: 陈元俊
     * @date: 2018年8月20日 下午3:46:14
     * @param <T>
     */
    public static class SLLIterator<T extends Object> {
        public SLLIterator(Node<T> cur) {
            this.cur = cur;
        }

        private Node<T> cur;

        public boolean hasNext() {
            return cur != null;
        }
        // 首次调用next的时候，需要定位到第一个元素，因此要保留cur之后再步进cur
        public Node<T> next() throws IteratorInvalidException {
            if (cur == null)
                throw new IteratorInvalidException();
            Node<T> res = cur;
            cur = cur.next;
            return res;
        }
    }

    public SLLIterator<T> getIterator() {
        return new SLLIterator<T>(head);
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void insert(T item) {
        Node<T> nd = new Node<T>(item, null, null);
        if (isEmpty()) {
            head = nd;
        } else {
            tail.next = nd;
        }
        tail = nd;
    }

    public Node<T> search(T item) {
        Node<T> cur = head;
        while (cur != null && cur.val != item) {
            cur = cur.next;
        }
        return cur;
    }

    public void remove(T item) {
        if (isEmpty()) {
            return;
        }
        Node<T> cur = head;
        if (cur.val.equals(item)) {
            this.head = head.next;
            return;
        }
        while (cur != null && cur.next != null && !cur.next.val.equals(item)) {
            cur = cur.next;
        }
        if (cur.next == null) {
            return;
        }
        cur.next = cur.next.next;
        if (cur.next == null) { // 注意要保护好tail防止被删了后空指针
            tail = cur;
        }
    }

    public void showAll() {
        Node<T> cur = head;
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (isEmpty()) {
            sb.append("]");
            str = sb.toString();
        } else {
            while (cur != null) {
                sb.append(cur.val).append(", ");
                cur = cur.next;
            }
            str = sb.substring(0, sb.length() - 2);
            str += "]";
        }
        logger.info("outputdata is = " + str);
    }

    /** 单链表的原地翻转. */
    public void inplaceReverse() {
        if (isEmpty() || head == tail) {
            return;
        }
        Node<T> cur = head;
        Node<T> newtail = null;
        while (cur != tail) {
            Node<T> tmp = cur.next;
            cur.next = newtail;
            newtail = cur;
            cur = tmp;
        }
        cur.next = newtail;
        head = cur;
    }

    public static void main(String[] args) throws Exception {
        MySinglyLinkedList<Integer> myll = new MySinglyLinkedList<>();
        myll.showAll();
        myll.insert(11);
        myll.showAll();
        myll.insert(22);
        myll.showAll();
        myll.insert(33);
        myll.showAll();
        myll.insert(44);
        myll.showAll();
        myll.remove(22);
        myll.showAll();
        myll.inplaceReverse();
        myll.showAll();

        System.out.println("starting to traverse it all");
        SLLIterator<Integer> iter = myll.getIterator();
        while (iter.hasNext()) {
            System.out.print(iter.next().val + "->");
        }
    }
}
