package yuanjun.chen.base.sort;

import java.util.Arrays;

import yuanjun.chen.base.common.RandomGenner;

/**
 * 基数排序
 **/
public class RadixSortAlgo {
	
	public static void radixSort(Integer[] arr) {
        int max = 0;
        for (int x : arr) { // 比较丑陋的选择最大值,O[n]
            if (x > max) {
                max = x;
            }
        }
        if (max == 0) return;
        int idx = 0;
        while (max > 0) {
        	idx++;
        	max = max / 10;
        }
        innerRadixSort(arr, idx);
        
	}
	
	/**
	 * @param Integer[] arr
	 * @param int totalDigits
	 **/
	public static void innerRadixSort(Integer[] arr, int totalDigits) {
		for (int i = 1; i <= totalDigits; i++) {
			// 针对每一位进行基数排序
			sortOnDigit(arr, i);
			// System.out.println("after--" + i + "th, " + Arrays.toString(arr));
		}
	}

	/**
	 * 按位进行排序，这里可以考虑计数排序，申请额外的空间O[n+m]
	 * TODO 顺序和逆序支持
	 * @param arr
	 * @param i
	 */
	private static void sortOnDigit(Integer[] arr, int idx) {
		int len = arr.length;
		Integer[] B = new Integer[len];
		int[] C = new int[10]; // 10个位置
		for (int a : arr) {
			int valAtI = getValAtI(a, idx);
			C[valAtI]++;
		}
		for (int i = 1; i <= 9; i++) {
			C[i] = C[i] + C[i - 1];
		}
        for (int j = len - 1; j >= 0; j--) {
        	int idd = getValAtI(arr[j], idx);
            B[C[idd] - 1] = arr[j]; // 这里减一是因为A和B的索引是从0开始的
            C[idd]--;
        }
        System.arraycopy(B, 0, arr, 0, len);
	}

	/**
	 * @param a
	 * @param i
	 * @return
	 */
	private static int getValAtI(int a, int i) {
		if (i == 1) {
			return a % 10;
		}
		return a % (int) Math.pow(10, i) / (int) Math.pow(10, i - 1);
	}
	
	public static void main(String[] args) {
		Integer[] arr = new Integer[] { 329, 457, 657, 839, 436, 720, 355 };
		radixSort(arr);
		System.out.println("finally--" + Arrays.toString(arr));
		
        int size = 65536 * 3; 
        int bound = 4000;
        Integer[] arr2 = RandomGenner.generateRandomIntArray(size, bound);
		radixSort(arr2);
		System.out.println("finally--" + Arrays.toString(arr2));
	}
}
