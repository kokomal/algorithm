/**
 * @Title: CantorUtil.java
 * @Package: yuanjun.chen.game.nPuzzle
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年10月30日 上午9:30:10
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.game.nPuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: CantorUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: 陈元俊
 * @date: 2018年10月30日 上午9:30:10
 */
public class CantorUtil {

    private static Map<String, Long> cache = new HashMap<>();
    
    public static Long cantor(int[][] a) { // n*n才管用
        int len = a.length;
        int[] v = new int[len * len];
        for (int i = 0; i < len; i++) {
            System.arraycopy(a[i], 0, v, i * len, len);
        }
        return cantor(v); // 康托展开值
    }

    public static Long cantorWithInnerCache(int[] a) {
        String key = Arrays.toString(a);
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        Long cantor = cantor(a);
        cache.put(key, cantor);
        return cantor; // 康托展开值
    }
    
    public static Long cantor(int[] a) {
        int n = a.length;
        Long cantor = 0L;
        for (int i = 0; i < n; ++i) {
            int smaller = 0; // 在当前位之后小于其的个数,数据量大的话要用线段树
            for (int j = i + 1; j < n; ++j) {
                if (a[j] < a[i])
                    smaller++;
            }
            cantor += FactUtil.factEx(n - i - 1).longValue() * smaller; // 康托展开累加
        }
        return cantor; // 康托展开值
    }
    
    public static int[] decantor(Long val, int len) {
        List<Integer> targ = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            targ.add(i);
        }
        int[] res = new int[len];
        for (int i = len; i > 0; i--) {
            Long fact = FactUtil.factEx(i-1).longValue();
            Long r = val % fact;
            int t = (int)(val / fact);
            val = r;
            res[len-i] = targ.get(t);
            targ.remove(t);
        }
        return res;
    }

    public static void main(String[] args) {
        long k = cantor(new int[] {1, 2, 3, 4, 6, 5, 0, 7, 8});
        System.out.println(k);
        
        long xx = cantor(new int[][] {{1, 2, 3}, {4, 6, 5}, {0, 7, 8}});
        System.out.println(xx);
        
        System.out.println(Arrays.toString(decantor(xx, 9)));
    }
}
