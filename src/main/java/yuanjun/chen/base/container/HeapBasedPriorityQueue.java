/**
 * @Title: HeapBasedPriorityQueue.java
 * @Package: yuanjun.chen.base.container
 * @Description: 基于堆排序的优先级队列
 * @author: 陈元俊
 * @date: 2018年7月18日 上午10:05:25
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.container;

import java.io.Serializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yuanjun.chen.base.common.DispUtil;
import yuanjun.chen.base.common.SortOrderEnum;
import yuanjun.chen.base.sort.HeapSortAlgo;

/**
 * @ClassName: HeapBasedPriorityQueue
 * @Description: 基于堆排序的优先级队列
 * @author: 陈元俊
 * @date: 2018年7月18日 上午10:05:25
 */
public class HeapBasedPriorityQueue implements Serializable {
    private static final Logger logger = LogManager.getLogger(HeapBasedPriorityQueue.class);
    private static final long serialVersionUID = 1L;
    private Integer[] arr;
    private SortOrderEnum order;
    private int cursor;

    /**
     * @param Integer[] initArray
     * @param SortOrderEnum order
     */
    public HeapBasedPriorityQueue(final Integer[] initArray, SortOrderEnum order) {
        this.order = order;
        this.arr = new Integer[initArray.length * 2];
        this.cursor = initArray.length - 1;
        System.arraycopy(initArray, 0, this.arr, 0, initArray.length);
        HeapSortAlgo.buildMaxHeap(this.arr, this.order, initArray.length); // 构建最大堆即可初始化即完成
    }

    /**
     * @comment 打印全部
     * @comment 打印1d二叉树的实例
     */
    public void peakAll1D() {
        if (this.cursor < 0) {
            logger.warn("===THERE IS NOTHING===");
            return;
        }
        int len = this.cursor + 1;
        StringBuilder sb = new StringBuilder();
        sb.append(arr[0]);
        for (int n = 1; n < len; n++) {
            sb.append(",").append(arr[n]);
        }
        logger.info(sb.toString());
    }

    /**
     * @comment 打印全部
     * @comment 比较粗糙的打印2d二叉树的实例
     */
    public void peakAll2D() {
        if (this.cursor < 0) {
            logger.warn("===THERE IS NOTHING===");
            return;
        }
        int perPad = 8;
        int len = this.cursor + 1;
        int padding = perPad * (len / 2); // 3U
        int idx = 0;
        for (int n = 0; idx < len; n++) {
            // gap = 2^n-1 to 2^2-2
            int leftCur = (int) Math.pow(2, n) - 1;
            int rightCur = (int) Math.pow(2, n + 1) - 2;
            int nGaps = rightCur - leftCur + 2;
            int per = padding / nGaps;
            StringBuilder sb = new StringBuilder();
            for (int i = leftCur; i <= rightCur && idx < len; i++) {
                idx++;
                sb.append(DispUtil.splitOne(per, ' '));
                sb.append(arr[i]);
            }
            logger.info(sb + "\n");
        }
    }

    public int size() {
        return cursor + 1;
    }

    public Integer peek() {
        if (this.cursor < 0) {
            return null;
        }
        return arr[0];
    }

    public Integer peekAt(int idx) {
        if (this.cursor < idx) {
            return null;
        }
        return arr[idx];
    }

    /**
     * @comment 插入key，步骤如下：
     * @comment 1. 扩容1个
     * @comment 2. 新key先以MAX或者MIN插入
     * @comment 3. 调用increaseKey或者decreaseKey进行翻转
     */
    public void insertKey(int key) {
        int len = this.arr.length;
        if (this.cursor + 1 == len) {
            Integer[] newArr = new Integer[len * 2];
            System.arraycopy(arr, 0, newArr, 0, len);
            this.arr = newArr;
        }
        this.cursor++;
        protoIncDecKey(cursor, key);
    }

    /**
     * @comment 弹出最前的元素
     * @comment 末尾元素篡位，然后从头开始maxHeapify
     */
    public Integer pop() {
        if (cursor < 0) {
            return null;
        } // 空则返回null
        Integer res = arr[0];
        if (cursor >= 0) {
            arr[0] = arr[cursor--];
            HeapSortAlgo.maxheapify(arr, order, true, 0, cursor + 1);
        }
        return res;
    }

    public Integer deleteKey(int pos) {
        if (this.cursor < pos || pos < 0) {
            return null;
        }
        int res = this.arr[pos];
        this.arr[pos] = this.arr[cursor];
        if (cursor >= 0) {
            cursor--;
            HeapSortAlgo.maxheapify(arr, order, true, pos, cursor + 1);
        }
        return res;
    }

    /**
     * @comment 对于最大Queue的pos位增加权重到newVal（注意newVal是最终值，不是递增量）
     * @comment 如果是最小Queue，或者newVal比原值小，此方法返回false
     * @comment 如果pos越界，也返回false
     */
    public boolean increaseKey(int pos, int newVal) {
        if (SortOrderEnum.DESC.equals(this.order) || this.cursor < pos || this.arr[pos] > newVal) {
            return false;
        }
        protoIncDecKey(pos, newVal);
        return true;
    }

    /**
     * @comment 对于最小Queue的pos位减少权重到newVal（注意newVal是最终值，不是递增量）
     * @comment 如果是最大Queue，或者newVal比原值大，此方法返回false
     * @comment 如果pos越界，也返回false
     */
    public boolean decreaseKey(int pos, int newVal) {
        if (SortOrderEnum.ASC.equals(this.order) || this.cursor < pos || this.arr[pos] < newVal) {
            return false;
        }
        protoIncDecKey(pos, newVal);
        return true;
    }

    private void protoIncDecKey(int pos, int newVal) {
        this.arr[pos] = newVal;
        while (pos != 0) { // pos为0则跳出，否则死循环
            int parent = pos - 1 >>> 1;
            boolean shouldGoUp = arr[parent] < newVal && SortOrderEnum.ASC.equals(this.order);
            shouldGoUp = shouldGoUp || (arr[parent] > newVal && SortOrderEnum.DESC.equals(this.order));
            if (shouldGoUp) {
                arr[pos] = arr[parent];
                pos = parent;
            } else {
                break;
            }
        }
        this.arr[pos] = newVal;
    }
}
