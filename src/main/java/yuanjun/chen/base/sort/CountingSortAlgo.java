/**
 * @Title: CountingSortAlgo.java
 * @Package: yuanjun.chen.base.sort
 * @Description: 计数排序
 * @author: 陈元俊
 * @date: 2018年7月20日 上午11:29:57
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.sort;

import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yuanjun.chen.base.common.MyArrayUtils;
import yuanjun.chen.base.common.MyPair;
import yuanjun.chen.base.common.RandomGenner;
import yuanjun.chen.base.common.SortOrderEnum;

/**
 * @ClassName: CountingSortAlgo
 * @Description: 计数排序，特点是空间换时间，非原地排序
 * 因为其不是比较排序，因此对数据的类型将比较picky
 * [重要]计数排序的C不宜太大，否则占用太多空间，因此对于float或者double类型的数据，
 * 此时k_range的计算将毫无意义，因此，将仅支持正Integer类型的排序
 * @author: 陈元俊
 * @date: 2018年7月20日 上午11:29:57
 */
public class CountingSortAlgo {
    private static final Logger logger = LogManager.getLogger(CountingSortAlgo.class);

    /**
     * 通用正数的排序
     **/
    public static Integer[] genericCountingSort(final Integer[] A, SortOrderEnum order) {
        MyPair<Integer> maxAndMin = MyArrayUtils.fetchMinAndMax(A);
        return countingSort(A, maxAndMin.getMax() + 1, order);
    }

    /**
     * 计数排序，A为原始输入数组，B为返回排好序的数组， k_range表示数据的上限，即所有输入均在[0, k_range]之间
     * 
     * @param Integer[] A
     * @param Integer k_range
     * @param SortOrderEnum order
     * @return Integer[]
     **/
    public static Integer[] countingSort(final Integer[] A, Integer k_range, SortOrderEnum order) {
        int len = A.length;
        Integer[] B = new Integer[len];
        int[] C = new int[k_range + 1]; // 对象new，初始化是null, int初始化是0, C是辅助数组，维度取决于k_range
        // 第一步是初始化C，记录每一个元素i出现的次数
        for (int ele : A) {
            C[ele]++;
        }
        logger.info("after record each occurrence, C=" + Arrays.toString(C));
        // 第二步递增C，记录小于等于i的元素个数
        for (int i = 1; i <= k_range; i++) {
            C[i] = C[i] + C[i - 1];
        }
        logger.info("after superposition occurrences, C=" + Arrays.toString(C));
        // 第三步开始组装结果数据
        if (SortOrderEnum.DESC.equals(order)) {
            for (int j = 0; j <= len - 1; j++) { // 保持逆序的稳定性，需要在循环体和置位的地方都修改
                B[len - C[A[j]]] = A[j]; // 逆序就用len-C[A[j]],顺序用C[A[j]]-1
                C[A[j]]--;
            }
        } else {
            for (int j = len - 1; j >= 0; j--) {
                B[C[A[j]] - 1] = A[j]; // 这里减一是因为A和B的索引是从0开始的
                C[A[j]]--;
            }
        }
        return B;
    }

    public static void main(String[] args) throws Exception {
        int size = 65536 * 3;
        int bound = 4000;
        Integer[] arr = RandomGenner.generateRandomTArray(size, bound, Integer.class);
        logger.info("before--" + Arrays.toString(arr));
        Integer[] res1 = countingSort(arr, bound - 1, SortOrderEnum.DESC);
        logger.info("after desc--" + Arrays.toString(res1));
        Integer[] res2 = countingSort(arr, bound - 1, SortOrderEnum.ASC);
        logger.info("after asc--" + Arrays.toString(res2));
    }
}
