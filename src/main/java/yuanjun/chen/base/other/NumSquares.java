/**
 * @Title: NumSquares.java
 * @Package: yuanjun.chen.base.other
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2019年5月6日 下午1:47:35
 * @version V1.0
 * @Copyright: 2019 All rights reserved.
 */
package yuanjun.chen.base.other;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: NumSquares
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: 陈元俊
 * @date: 2019年5月6日 下午1:47:35
 */
public class NumSquares {
    private static Map<Integer, Integer> cache = new HashMap<>();
    static {
        cache.put(0, 1);
        cache.put(1, 1);
    }
    
    public static int numSquares(int n) {
        if (cache.containsKey(n)) return cache.get(n);
        int len = 0;
        int[] price = new int[n + 1]; // price[0]没有用
        int val = 0;
        while (val <= n) {
            price[val] = 1;
            len++;
            val = len * len;
        }
        --len;
        if (len * len == n) return 1; // 完全平方直接返回
        // System.out.println(Arrays.toString(price));
        for (int j = 2; j <= n; j++) {
            // price j 需要遍历1到j-1的所有
            if (price[j] == 1)
                continue; // 完全平方直接略过
            for (int k = 1; k < j; k++) { // 分裂成k 和 j-k
                int newP = price[k] + price[j - k];
                if (0 == price[j] || newP < price[j]) {
                    price[j] = newP;
                }
            }
        }
        // System.out.println(Arrays.toString(price));
        cache.put(n, price[n]);
        return price[n];
    }
    
    
    public static int numSquares2(int n) {
        if (cache.containsKey(n)) return cache.get(n);
        int len = 0;
        int val = 0;
        while (val <= n) {
            len++;
            val = len * len;
            cache.put(val, 1);
        }
        --len;
        if (len * len == n) {
            cache.put(n, 1);
            // System.out.println(n + " VAL=1");
            return 1; // 完全平方直接返回
        }
        for (int j = 2; j <= n; j++) {
            // price j 需要遍历1到j-1的所有
            if (cache.containsKey(j) && cache.get(j) == 1)
                continue; // 完全平方直接略过
            int candiJ = Integer.MAX_VALUE;
            for (int k = 1; k < j; k++) { // 分裂成k 和 j-k
                int newP = numSquares2(k) + numSquares2(j - k);
                if (newP < candiJ) {
                    candiJ = newP;
                }
            }
            // System.out.println("Cache=" + cache);
            // System.out.println("PUT KEY " + j + " VAL " + candiJ);
            cache.put(j, candiJ);
        }
        // System.out.println(n + " ===> " + cache.get(n));
        return cache.get(n);
    }
    
    public static int numSquares3(int n) {
        Map<Integer, Integer> cache2 = new HashMap<>();
        int len = 0;
        int val = 0;
        while (val <= n) {
            len++;
            val = len * len;
            cache2.put(val, 1);
        }
        --len;
        if (len * len == n) {
            cache2.put(n, 1);
            return 1; // 完全平方直接返回
        }
        for (int j = 2; j <= n; j++) {
            if (cache2.containsKey(j) && cache2.get(j) == 1)
                continue; // 完全平方直接略过
            int candiJ = Integer.MAX_VALUE;
            for (int k = 1; k < j; k++) { // 分裂成k 和 j-k
                int newP = cache2.get(k) + cache2.get(j - k);
                if (newP < candiJ) {
                    candiJ = newP;
                }
            }
            cache2.put(j, candiJ);
        }
        return cache2.get(n);
    }

    public static int smart(int n) {
        int[] nums = new int[n + 1];
        nums[0] = 0;
        nums[1] = 1;
        for (int i = 2; i <= n; i++) {
            int temp = Integer.MAX_VALUE;
            // i以内的平方数
            for (int j = 1; j * j <= i; j++) {
                temp = Math.min(temp, nums[i - j * j]);
            }
            nums[i] = temp + 1;
        }
        return nums[n];
    }
    
    public static void main(String[] args) {
        System.out.println(numSquares3(13));
        System.out.println(smart(13));
    }
}
