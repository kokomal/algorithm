/**
 * 
 */
package yuanjun.chen.base.container;

/**
 * @author 陈元俊
 *
 */
public class Node<T extends Object> {
	protected T val;
	protected Node<T> next;
	protected Node<T> pre;

	/**
	 * @param val
	 * @param next
	 * @param pre
	 */
	public Node(T val, Node<T> next, Node<T> pre) {
		super();
		this.val = val;
		this.next = next;
		this.pre = pre;
	}
	
	/**
	 * @param val
	 * @param next
	 */
	public Node(T val, Node<T> next) {
		super();
		this.val = val;
		this.next = next;
	}
	
	public T getVal() {
		return val;
	}

	public void setVal(T val) {
		this.val = val;
	}

	public Node<T> getNext() {
		return next;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}

	public Node<T> getPre() {
		return pre;
	}

	public void setPre(Node<T> pre) {
		this.pre = pre;
	}

}
