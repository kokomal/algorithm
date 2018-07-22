/**
 * 桶排序实现
 */
package yuanjun.chen.base.sort;

import java.util.Arrays;

import yuanjun.chen.base.common.DispUtil;
import yuanjun.chen.base.common.RandomGenner;

/**
 * 桶排序实现
 *
 */
public class BucketSortAlgo {

	public static class Node {
		public Integer val;
		private Node next;

		/**
		 * @param val
		 * @param next
		 */
		public Node(Integer val, Node next) {
			super();
			this.val = val;
			this.next = next;
		}
	}

	public static void inplaceBucketSort(Integer[] arr) {
		Node[] nodeEntrySet = new Node[32]; // 按照位数划分桶
		for (Integer val : arr) {
			int bucketIdx = calcBucketIdxForVal(val); // 获得每一个数的桶号
			insertBucket(nodeEntrySet, bucketIdx, val);
		}
		concatenate(nodeEntrySet, arr);
	}

	/**
	 * @param nodeEntrySet
	 * @param arr
	 */
	private static void concatenate(Node[] nodeEntrySet, Integer[] arr) {
		Integer[] tmp = new Integer[arr.length];
		int idx = 0;
		int order = 0;
		for (Node node : nodeEntrySet) {
			order++;
			if (node != null) {
				Node tmpNode = node;
				int inner = 0;
				while (tmpNode != null) {
					tmp[idx] = tmpNode.val;
					tmpNode = tmpNode.next;
					idx++;
					inner++;
				}
				System.out.println("fo bucket No." + order + " size=" + inner);
			}
		}
		System.arraycopy(tmp, 0, arr, 0, arr.length);
	}

	/**
	 * 因为是链表，考虑采用插入排序，避免大规模内存拷贝复制
	 * 
	 * @param nodeEntrySet
	 * @param bucketIdx
	 * @param val
	 */
	private static void insertBucket(Node[] nodeEntrySet, int bucketIdx, Integer val) {
		if (nodeEntrySet[bucketIdx] == null) {
			nodeEntrySet[bucketIdx] = new Node(val, null);
		} else {
			Node cur = nodeEntrySet[bucketIdx];
			if (cur.val > val) {
				nodeEntrySet[bucketIdx] = new Node(val, cur);
			} else {
				Node next = cur.next;
				while (next != null && next.val <= val) {
					cur = next;
					next = next.next;
				}
				Node insert = new Node(val, next);
				cur.next = insert;
			}
		}
	}

	/**
	 * @param val
	 * @return
	 */
	private static int calcBucketIdxForVal(Integer val) {
		if (val < 10) {
			return 0;
		} else if (val < 100) {
			return 1;
		} else if (val < 1000) {
			return 2;
		} else if (val < 10000) {
			return 3;
		} else if (val < 100000) {
			return 4;
		} else if (val < 1000000) {
			return 5;
		} else if (val < 10000000) {
			return 6;
		} else if (val < 100000000) {
			return 7;
		} else if (val < 1000000000) {
			return 8;
		} else if (val < 10000000000l) {
			return 9;
		} else {
			return 10;
		}
		
//		int targ = 10;
//		int idx = 0;
//		while (val >= targ) {
//			idx++;
//			targ = targ * 10;
//		}
//		return idx;
	}

	public static void main(String[] args) {
		Integer[] arr = new Integer[] { 12, 32, 32, 323, 23, 1, 22 };
		inplaceBucketSort(arr);
		System.out.println("after--" + Arrays.toString(arr));

		int size = 60000;
		int bound = 4000;
		Integer[] arr2 = RandomGenner.generateRandomIntArray(size, bound);
        DispUtil.embed(50, '*', "BUCKET ASC SORT STARTS");
        long t1 = System.currentTimeMillis();
		inplaceBucketSort(arr2);
        long t2 = System.currentTimeMillis();
		System.out.println("after--" + Arrays.toString(arr2));
        DispUtil.embed(50, '*', "COUNTING SORT ENDS..");
        System.out.println("BUCKET ASC SORT time used " + (t2 - t1) + "ms");
   
	}
}
