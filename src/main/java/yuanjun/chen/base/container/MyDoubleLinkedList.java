package yuanjun.chen.base.container;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** 双向链表. */
public class MyDoubleLinkedList<T extends Object> {
    private static final Logger logger = LogManager.getLogger(MyDoubleLinkedList.class);

    private Node<T> dummy = new Node<T>(null, null, null);

    /** Private Node<T> tail = null; // dummyHead+头插法可以完全无视tail或者head指针. */

    public MyDoubleLinkedList() {
        dummy.next = dummy; // 需要先new然后知道自己地址，再将next和pre指向自己
        dummy.pre = dummy;
    }

    public DLLIterator<T> getIterator() { // 永远指向dummy下一个
        return new DLLIterator<T>(this.dummy);
    }

    public DLLReverseIterator<T> getReverseIterator() { // 永远指向dummy上一个
        return new DLLReverseIterator<T>(this.dummy);
    }
    
    public static class DLLIterator<T extends Object> {
        public DLLIterator(Node<T> cur) {
            this.cur = cur;
            this.dummy = cur;
        }
        private Node<T> cur;
        private Node<T> dummy;
        public boolean hasNext() { // 双链表永远是有下一个,除非是dummy
            return cur.next != dummy;
        }
        // 首次调用next的时候，需要定位到第一个元素，因此要保留cur之后再步进cur
        public Node<T> next() {
            cur = cur.next;
            if (cur.val == null) { // 跳过拧巴的dummy
                cur = cur.next;
            }
            return cur;
        }
        public void delete() {
            cur.pre.next = cur.next;
            cur.next.pre = cur.pre;
        }
    }

    public static class DLLReverseIterator<T extends Object> {
        public DLLReverseIterator(Node<T> cur) {
            this.cur = cur;
            this.dummy = cur;
        }
        private Node<T> cur;
        private Node<T> dummy;
        public boolean hasPrevious() { // 双链表永远是有下一个,除非是dummy
            return cur.pre != dummy;
        }
        // 首次调用next的时候，需要定位到第一个元素，因此要保留cur之后再步进cur
        public Node<T> previous() {
            cur = cur.pre;
            if (cur.val == null) { // 跳过拧巴的dummy
                cur = cur.pre;
            }
            return cur;
        }
        public void delete() {
            cur.pre.next = cur.next;
            cur.next.pre = cur.pre;
        }
    }

    
    public boolean isEmpty() {
        return dummy.next == dummy;
    }

    // 头插法
    public void insert(T item) {
        Node<T> nd = new Node<T>(item, null, null);
        nd.next = dummy.next;
        nd.next.pre = nd;
        dummy.next = nd;
        nd.pre = dummy;
    }
    
    // 尾插法
    public void insertRear(T item) {
        Node<T> nd = new Node<T>(item, null, null);
        dummy.pre.next = nd;
        nd.pre = dummy.pre;
        nd.next = dummy;
        dummy.pre = nd;
    }

    public Node<T> search(T item) {
        Node<T> cur = dummy.next;
        while (cur != dummy && !cur.val.equals(item)) {
            cur = cur.next;
        }
        return cur;
    }

    public void remove(T item) {
        if (isEmpty()) {
            return;
        }
        Node<T> cur = dummy.next;
        while (cur != dummy && !cur.val.equals(item)) {
            cur = cur.next;
        }
        if (item.equals(cur.val)) {
            cur.pre.next = cur.next;
            cur.next.pre = cur.pre;
        }
    }

    /** 头插法，遍历则需要逆序，略别扭. */
    public void showAll() {
        Node<T> cur = dummy.next;
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (isEmpty()) {
            sb.append("]");
            str = sb.toString();
        } else {
            while (cur != dummy) {
                sb.append(cur.val).append(", ");
                cur = cur.next;
            }
            str = sb.substring(0, sb.length() - 2);
            str += "]";
        }
        logger.info("outputdata is = " + str);
    }

    public void showAllReverse() {
        Node<T> cur = dummy.pre;
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (isEmpty()) {
            sb.append("]");
            str = sb.toString();
        } else {
            while (cur != dummy) {
                sb.append(cur.val).append(", ");
                cur = cur.pre;
            }
            str = sb.substring(0, sb.length() - 2);
            str += "]";
        }
        logger.info("outputdata is = " + str);
    }

    public void inplaceReverse() {
        if (isEmpty() || dummy.next == dummy.pre) {
            return;
        }
        Node<T> cur = dummy.next; // 首元素
        Node<T> tail = dummy.pre;
        Node<T> newtail = dummy;
        while (cur != tail) {
            Node<T> tmp = cur.next; // 保护首元素的下一个节点
            cur.next = newtail; // 逐次把首元素移到尾部
            newtail.pre = cur; // 重新指向pre和next
            newtail = cur;
            cur = tmp;
        }
        cur.next = newtail;
        newtail.pre = cur;
        dummy.next = cur;
        cur.pre = dummy;
    }

    public static void main(String[] args) {
        MyDoubleLinkedList<Integer> myll = new MyDoubleLinkedList<>();
        int m = 10;
        int n = 3;
        for (int i = 0; i < m; i++) {
            myll.insertRear(i);
        }
        myll.showAll();
        
        DLLReverseIterator<Integer> driter = myll.getReverseIterator();
        while(driter.hasPrevious()) {
            System.out.println(driter.previous().val);
        }
        
        System.out.println("===" + driter.previous().val);
        
        DLLIterator<Integer> diter = myll.getIterator();
        while(diter.hasNext()) {
            for (int i = 0; i < n; i++) {
                diter.next();
            }
            System.out.println(diter.cur.val + "-out");
            diter.delete();
        }
    }
}
