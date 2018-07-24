/**
 * @Title: BucketSortAlgo.java
 * @Package: yuanjun.chen.base.sort
 * @Description: 桶排序实现
 * @author: 陈元俊
 * @date: 2018年7月24日 上午9:29:57
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.sort;

import java.util.Arrays;
import org.apache.log4j.Logger;
import yuanjun.chen.base.common.DispUtil;
import yuanjun.chen.base.common.MyArrayUtils;
import yuanjun.chen.base.common.MyPair;
import yuanjun.chen.base.common.RandomGenner;
import yuanjun.chen.base.common.SortOrderEnum;

/**
 * @ClassName: BucketSortAlgo
 * @Description: 桶排序实现
 * @author: 陈元俊
 * @date: 2018年7月24日 上午9:29:57
 */
public class BucketSortAlgo {
    private static final Logger logger = Logger.getLogger(BucketSortAlgo.class);
    
    private static boolean showDebug = false;
    
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
	
	/**
	 * 桶排序的重要参数
	 * 
	 **/
	public static class BucketConfig {
	    /**
         * @param margin
         */
        public BucketConfig(int margin) {
            this.margin = margin;
        }
        int margin;

        /**
         * 根据步长和margin获得相应的步长
         * 如果输入<=0则采用sqrt模式
         * 否则不进行修改
         **/
        public int getGapBySlotStep(int slotStep) {
            return slotStep <= 0 ? (int) Math.sqrt(margin) : slotStep;
        }

        public int getNBucketsBySlotStep(int slotStep) {
            return margin / getGapBySlotStep(slotStep) + 1;
        }
	}

	public static void inplaceBucketSort(Integer[] arr, int slotStep, SortOrderEnum order) {
        MyPair<Integer> maxAndMin = MyArrayUtils.fetchMinAndMax(arr);
        // 获得最大的值
        if (maxAndMin.getMax() <= maxAndMin.getMin()) {
            return; // 全一样，不用排了
        }
        int margin = maxAndMin.getMax() - maxAndMin.getMin(); // margin = 最大-最小
        BucketConfig bucketConfig = new BucketConfig(margin);
        
        int gap = bucketConfig.getGapBySlotStep(slotStep);
        int nbuckets = bucketConfig.getNBucketsBySlotStep(slotStep);

        logger.info("gap = " + gap + ", and nbuckets = " + nbuckets);
		Node[] nodeEntrySet = new Node[nbuckets]; // 按照位数划分桶
		for (Integer val : arr) {
			insertBucket(nodeEntrySet, (val - maxAndMin.getMin()) / gap, val, order); // 根据bucket的索引进行插入操作
		}
		concatenateInplace(nodeEntrySet, arr, order);
	}

	/**
	 * 遍历每一个槽，取出数据拼装成完整的数组并原地拷贝
	 * @param nodeEntrySet
	 * @param arr
	 * @param order 
	 */
	private static void concatenateInplace(Node[] nodeEntrySet, Integer[] arr, SortOrderEnum order) {
        Integer[] tmp = new Integer[arr.length];
        int idx = 0;
        int buckNo = 0;
        int len = nodeEntrySet.length;
        for (int i = 0; i < len; i++) {
            int ido = i;
            if (SortOrderEnum.DESC.equals(order)) {
                ido = len - 1 - ido;
            }
            idx = concatEachNode(tmp, idx, buckNo, nodeEntrySet[ido]);
        }
        System.arraycopy(tmp, 0, arr, 0, arr.length);
    }

    /**   
     * @Title: concatEachNode   
     * @Description: 处理每一个entry的所有node 
     * @param: @param tmp
     * @param: @param idx
     * @param: @param buckNo
     * @param: @param node
     * @param: @return      
     * @return: int      
     * @throws   
     */
    private static int concatEachNode(Integer[] tmp, int idx, int buckNo, Node node) {
        buckNo++;
        if (node != null) {
        	Node tmpNode = node;
        	int inner = 0;
        	while (tmpNode != null) {
        		tmp[idx] = tmpNode.val;
        		tmpNode = tmpNode.next;
        		idx++;
        		inner++;
        	}
        	if (showDebug) {
        	    logger.debug("for bucket No." + buckNo + " size=" + inner);
        	}
        }
        return idx;
    }

	/**
	 * 因为是链表，考虑采用插入排序，避免大规模内存拷贝复制
	 * @param nodeEntrySet
	 * @param bucketIdx
	 * @param val
	 */
	private static void insertBucket(Node[] nodeEntrySet, int bucketIdx, Integer val, SortOrderEnum order) {
		if (nodeEntrySet[bucketIdx] == null) {
			nodeEntrySet[bucketIdx] = new Node(val, null);
		} else {
			Node cur = nodeEntrySet[bucketIdx];
			boolean shouldHeadInsertAsc = cur.val > val && SortOrderEnum.ASC.equals(order);
			boolean shouldHeadInsertDesc = cur.val < val && SortOrderEnum.DESC.equals(order);
			if (shouldHeadInsertAsc || shouldHeadInsertDesc) {
				nodeEntrySet[bucketIdx] = new Node(val, cur);
			} else {
				Node next = cur.next;
                while (next != null && ((next.val <= val && SortOrderEnum.ASC.equals(order))
                        || (next.val >= val && SortOrderEnum.DESC.equals(order)))) {
                    cur = next;
                    next = next.next;
                }
				Node insert = new Node(val, next);
				cur.next = insert;
			}
		}
	}
    
    public static void showDebug() {
        showDebug = true;
    }
    
    public static void hideDebug() {
        showDebug = false;
    }
    
	public static void main(String[] args) {
		int size = 160;
		int bound = 400;
		Integer[] arr2 = RandomGenner.generateRandomIntArray(size, bound);
        DispUtil.embed(50, '*', "BUCKET ASC SORT STARTS");
        logger.info("before--" + Arrays.toString(arr2));
        long t1 = System.currentTimeMillis();
		inplaceBucketSort(arr2, 2, SortOrderEnum.DESC);
        long t2 = System.currentTimeMillis();
        logger.info("after--" + Arrays.toString(arr2));
        DispUtil.embed(50, '*', "COUNTING SORT ENDS..");
        logger.info("BUCKET ASC SORT time used " + (t2 - t1) + "ms");
   
	}
}
