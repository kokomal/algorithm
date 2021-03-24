package yuanjun.chen.base.other;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ThreeSumMulti {
	private static int MOD = 1000000000 + 7;
	private static Map<Integer, Long> rec = new HashMap<>();

	public static void init(int[] A) {
		for (int i : A) {
			if (rec.containsKey(i)) {
				rec.put(i, rec.get(i) + 1L);
			} else {
				rec.put(i, 1L);
			}
		}
	}

	public static int threeSumMulti(int[] A, int target) {
		if (A.length == 0)
			return 0;
		init(A);
		System.out.println(rec);
		Arrays.sort(A);
		System.out.println(Arrays.toString(A));
		return packRes(find3(A, 0, target, rec));
	}

	private static long find3(int[] A, int idx, int target, Map<Integer, Long> rec) {
		if (idx > A.length - 1)
			return 0;
		long sum = 0L;
		for (int j = idx; j < A.length;) {
			int next = (int) (j + rec.get(A[j]));
			if (rec.get(A[j]) > 2) { // 有三个及以上
				if (A[j] * 3 == target) {
					long delta = cn3(rec.get(A[j]));
					sum += delta;
				} else {
					long in1 = rec.get(A[j]) * find2(A, next, target - A[j], rec);
					long in2 = cn2(rec.get(A[j])) * find1(A, next, target - 2 * A[j], rec);
					sum += in1 + in2;
				}
			} else if (rec.get(A[j]) == 2) { // 有二个
				long in1 = 2 * find2(A, next, target - A[j], rec);
				long in2 = find1(A, next, target - 2 * A[j], rec);
				sum += in1 + in2;
			} else { // 仅此一个
				long in1 = find2(A, next, target - A[j], rec);
				sum += in1;
			}
			j = next;
		}
		return sum;
	}

	private static long find1(int[] A, int idx, int target, Map<Integer, Long> rec) {
		if (idx > A.length - 1)
			return 0;
		long sum = 0L;
		for (int j = idx; j < A.length; j++) {
			if (A[j] == target)
				sum++;
		}
		return sum;
	}

	public static long find2(int[] A, int idx, int target, Map<Integer, Long> rec) {
		if (idx > A.length - 1)
			return 0;
		long sum = 0L;
		for (int j = idx; j < A.length;) {
			int next = (int) (j + rec.get(A[j]));
			if (rec.get(A[j]) >= 2) { // 有2个及以上
				if (A[j] * 2 == target) {
					sum += cn2(rec.get(A[j]));
				} else {
					long in1 = rec.get(A[j]) * find1(A, next, target - A[j], rec);
					sum += in1;
				}
			} else if (rec.get(A[j]) == 1) { // 有1个
				long in1 = find1(A, next, target - A[j], rec);
				sum += in1;
			}
			j = next;
		}
		return sum;
	}

	private static int packRes(long sum) {
		return (int) (sum % MOD);
	}

	private static long cn3(long n) {
		return n * (n - 1) * (n - 2) / 6;
	}

	private static long cn2(long n) {
		return n * (n - 1) / 2;
	}

	public static void main(String[] args) {
		// System.out.println(threeSumMulti(new int[] { 1, 1, 2, 2, 2, 2 }, 5));

		int[] A = new int[] { 0, 2, 0, 0 };
		System.out.println(threeSumMulti(A, 2));
	}
}
