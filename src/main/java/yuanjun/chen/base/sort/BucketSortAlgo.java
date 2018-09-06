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
import java.util.Collections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yuanjun.chen.base.common.DispUtil;
import yuanjun.chen.base.common.MyArrayUtils;
import yuanjun.chen.base.common.MyPair;
import yuanjun.chen.base.common.RandomGenner;
import yuanjun.chen.base.common.SortOrderEnum;
import static yuanjun.chen.base.common.CommonUtils.*;

/**
 * @ClassName: BucketSortAlgo
 * @Description: 桶排序实现
 * @author: 陈元俊
 * @date: 2018年7月24日 上午9:29:57
 */
public class BucketSortAlgo {
    private static final Logger logger = LogManager.getLogger(BucketSortAlgo.class);

    private static boolean showDebug;

    public static class Node<T extends Comparable<?>> {
        public T val;
        private Node<T> next;

        public Node(T val, Node<T> next) {
            this.val = val;
            this.next = next;
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T extends Comparable<T>> void inplaceBucketSort(T[] arr, SortOrderEnum order) {
        MyPair<T> maxAndMin = MyArrayUtils.fetchMinAndMax(arr);
        // 获得最大的值
        if (maxAndMin == null || lesseq(maxAndMin.getMax(), maxAndMin.getMin())) {
            return; // 全一样，不用排了
        }
        Number maxIdx = (Number) maxAndMin.getMax();
        Number minIdx = (Number) maxAndMin.getMin();
        Node[] nodeEntrySet = new Node[maxIdx.intValue() - minIdx.intValue() + 1]; // 按照位数划分桶
        for (Comparable val : arr) {
            insertBucket(nodeEntrySet, minIdx.intValue(), val); // 根据bucket的索引进行插入操作
        }
        sortEachEntry(nodeEntrySet, order);
        concatenateInplace(nodeEntrySet, arr, order);
    }

    public static <T extends Comparable<T>> void reviewMe(Node<T> node) {
        if (node != null) {
            Node<T> cur = node;
            while (cur != null) {
                System.out.print(cur.val + "-->");
                cur = cur.next;
            }
            System.out.println("null");
        }
    }

    /**
     * 遍历每一个entry，进行排序.
     * 
     * @param nodeEntrySet
     * @param order
     */
    private static <T extends Comparable<T>> void sortEachEntry(Node<T>[] nodeEntrySet, SortOrderEnum order) {
        int len = nodeEntrySet.length;
        for (int i = 0; i < len; i++) {
            Node<T> head = nodeEntrySet[i];
            if (head != null && head.next != null) { // 逐一对每一个entry进行插入排序,前提是此entry有大于1个的元素
                // 创造一个新的链表头部
                Node<T> pre = new Node<T>(null, null);
                // 用一个临时变量保存头节点
                Node<T> ans = pre;
                // cur是原链表上的指针
                Node<T> cur = head;
                while (cur != null) {
                    // 每次循环前重置pre为头结点，这样保证每次都从头往后遍历
                    pre = ans;
                    // 当pre.next.val大于cur.val时停止循环
                    while (pre.next != null && ((less(pre.next.val, cur.val) && SortOrderEnum.ASC.equals(order))
                            || (more(pre.next.val, cur.val) && SortOrderEnum.DESC.equals(order)))) {
                        pre = pre.next;
                    }
                    // pre.next.val 大于 cur.val，此时应该把cur插入到pre后
                    // 保存原链表当前节点的下一节点
                    Node<T> tmp = cur.next;
                    // 把cur插入到pre之后
                    cur.next = pre.next;
                    pre.next = cur;
                    // cur指针后移一位
                    cur = tmp;
                }
                nodeEntrySet[i] = ans.next;
            }
        }
    }

    /**
     * 遍历每一个槽，取出数据拼装成完整的数组并原地拷贝.
     * 
     * @param nodeEntrySet
     * @param arr
     * @param order
     */
    @SuppressWarnings({"unchecked"})
    private static <T extends Comparable<T>> void concatenateInplace(Node<T>[] nodeEntrySet, T[] arr,
            SortOrderEnum order) {
        T[] tmp = (T[]) new Comparable[arr.length];
        int idx = 0;
        int buckNo = 0;
        int len = nodeEntrySet.length;
        for (int i = 0; i < len; i++) {
            int ido = i;
            if (SortOrderEnum.DESC.equals(order)) {
                ido = len - 1 - ido;
            }
            idx = concatEachNode(tmp, idx, buckNo++, nodeEntrySet[ido]);
        }
        System.arraycopy(tmp, 0, arr, 0, arr.length);
    }

    private static <T extends Comparable<T>> int concatEachNode(T[] tmp, int idx, int buckNo, Node<T> node) {
        if (node != null) {
            Node<T> tmpNode = node;
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
     * 因为是链表，考虑采用插入排序，避免大规模内存拷贝复制.
     * 
     * @param nodeEntrySet
     * @param val
     */
    private static <T extends Comparable<T>> void insertBucket(Node<T>[] nodeEntrySet, int min, T val) {
        Number valIdx = (Number) val;
        int idx = valIdx.intValue() - min; // 强制置为int，意味着所有类型的Comparable都要进行类型转换
        if (nodeEntrySet[idx] == null) { // 如果没有值则新建Node
            nodeEntrySet[idx] = new Node<T>(val, null);
        } else { // 头插法
            Node<T> oldNode = nodeEntrySet[idx];
            Node<T> newNode = new Node<T>(val, oldNode);
            nodeEntrySet[idx] = newNode;
        }
    }

    public static void showDebug() {
        showDebug = true;
    }

    public static void hideDebug() {
        showDebug = false;
    }

    public static void main(String[] args) throws Exception {
        // showDebug();
        int size = 30;
        int bound = 7000;
        Float[] arr2 = RandomGenner.generateRandomTArray(size, 0, bound, Float.class);
        Float[] arr3 = new Float[size];
        System.arraycopy(arr2, 0, arr3, 0, size);
        DispUtil.embed(50, '*', "BUCKET ASC SORT STARTS");
        logger.info("before--" + Arrays.toString(arr2));
        long t1 = System.currentTimeMillis();
        inplaceBucketSort(arr2, SortOrderEnum.DESC);
        long t2 = System.currentTimeMillis();
        logger.info("after--" + Arrays.toString(arr2));
        DispUtil.embed(50, '*', "COUNTING SORT ENDS..");
        logger.info("BUCKET ASC SORT time used " + (t2 - t1) + "ms");
        logger.info("before--" + Arrays.toString(arr3));
        Arrays.sort(arr3, Collections.reverseOrder());
        logger.info("after--" + Arrays.toString(arr3));
    }
}
