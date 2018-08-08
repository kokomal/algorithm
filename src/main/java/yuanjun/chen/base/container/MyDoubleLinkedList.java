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

    public boolean isEmpty() {
        return dummy.next == dummy;
    }

    public void insert(T item) {
        Node<T> nd = new Node<T>(item, null, null);
        nd.next = dummy.next;
        nd.next.pre = nd;
        dummy.next = nd;
        nd.pre = dummy;
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

    public void showAllReverse() {
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
        myll.showAll();
        myll.insert(11);
        myll.showAll();
        myll.insert(22);
        myll.showAll();
        myll.insert(33);
        myll.showAll();
        myll.insert(44);
        myll.showAll();
        myll.remove(44);
        myll.showAll(); // 11, 33, 44
        myll.showAllReverse(); // 44, 33, 11
        myll.inplaceReverse(); // 开始本地逆转
        myll.showAll(); // 44, 33, 11
        myll.showAllReverse(); // 11, 33, 44
    }
}
