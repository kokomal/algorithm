package yuanjun.chen.base.other;

import java.math.BigInteger;
/*LEETCODE 445*/
public class AddTwo {

	public static class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
		}
	}

	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		String v1 = parse(l1);
		String v2 = parse(l2);
		BigInteger res = new BigInteger(v1).add(new BigInteger(v2));
		String r = res.toString();
		System.out.println("RES==" + r);
		ListNode ret = null;
		ListNode cur = null;
		int i = 0;
		while (i < r.length()) {
			char v = r.charAt(i);
			ListNode nn = new ListNode((int) (v - '0'));
			if (ret == null) {
				ret = nn;
				cur = nn;
			} else {
				cur.next = nn;
				cur = cur.next;
			}
			i++;
		}
		return ret;
	}

	String parse(ListNode l) {
		StringBuilder sb = new StringBuilder();
		ListNode cur = l;
		while (cur != null) {
			sb.append(Integer.toString(cur.val));
			cur = cur.next;
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		AddTwo add = new AddTwo();
		ListNode l1 = new ListNode(7);
		ListNode l12 = new ListNode(2);
		ListNode l13 = new ListNode(4);
		ListNode l14 = new ListNode(3);
		l1.next = l12;
		l12.next = l13;
		l13.next = l14;

		ListNode l2 = new ListNode(5);
		ListNode l21 = new ListNode(6);
		ListNode l22 = new ListNode(4);
		l2.next = l21;
		l21.next = l22;

		ListNode res = add.addTwoNumbers(l1, l2);

		while (res != null) {
			System.out.print(res.val + "->");
			res = res.next;
		}
	}
}
