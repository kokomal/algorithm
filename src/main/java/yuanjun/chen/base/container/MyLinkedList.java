/**
 * 
 */
package yuanjun.chen.base.container;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author hp
 *
 */
public class MyLinkedList<T extends Object> {

	private static final Logger logger = LogManager.getLogger(MyLinkedList.class);

	private Node<T> dummy = new Node<T>(null, null, null);
	// private Node<T> tail = null; // dummyHead+头插法可以完全无视tail或者head指针

	public MyLinkedList() {
		super();
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
		while (cur != dummy && cur.val != item) {
			cur = cur.next;
		}
		return cur;
	}

	public void remove(T item) {
		if (isEmpty())
			return;
		Node<T> cur = dummy.next;
		while (cur != dummy && cur.val != item) {
			cur = cur.next;
		}
		if (item.equals(cur.val)) {
			System.out.println("cur = " + cur.val);
			System.out.println("cur.pre = " + cur.pre.val);
			System.out.println("cur.next = " + cur.next.val);
			cur.pre.next = cur.next;
			cur.next.pre = cur.pre;
		}
	}

	// 头插法，遍历则需要逆序，略别扭
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
				sb.append(cur.val + ", ");
				cur = cur.pre;
			}
			str = sb.substring(0, sb.length() - 2);
			str += "]";
		}
		logger.info("outputdata is = " + str);
	}

	public static void main(String[] args) {
		MyLinkedList<Integer> myll = new MyLinkedList<>();
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
	}
}
