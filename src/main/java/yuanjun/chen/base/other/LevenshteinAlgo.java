/**
 * @Title: LevenshteinAlgo.java
 * @Package: yuanjun.chen.base.other
 * @author: 陈元俊
 * @date: 2019年4月29日 上午10:22:11
 * @version V1.0
 * @Copyright: 2019 All rights reserved.
 */
package yuanjun.chen.base.other;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: LevenshteinAlgo
 * @Description: 最小编辑距离,递归的slow方法 动态规划可以用打表的方式进行，递归虽然优雅，但性能太low,加了cache之后性能有了突破
 * @author: 陈元俊
 * @date: 2019年4月29日 上午10:22:11
 */
public class LevenshteinAlgo {
    private static final int DELETE_PRICE = 1;
    private static final int MODIFY_PRICE = 1;
    private static final int INSERT_PRICE = 1;

    private static Map<String, Integer> cache = new HashMap<>();
    private static Map<String, char[]> cache2 = new HashMap<>();
    
    /* 取某字符串的[1-n]尾巴,例如ahead -> head, 输入为空或者仅为1个元素则返回null */
    public static char[] tail(char[] x) {
        // if (x == null || x.length <= 1) {
        // return null;
        // }
        String key = new String(x);
        if (cache2.containsKey(key)) {
            return cache2.get(key);
        }
        char[] dest = new char[x.length - 1];
        System.arraycopy(x, 1, dest, 0, dest.length);
        cache2.put(key, dest);
        return dest;
    }

    public static int minEditDistance(char[] src, char[] targ) {
        if (src == null && targ == null) {
            return 0;
        }
        if (src == null || src.length == 0) {
            return targ.length;
        }
        if (targ == null || targ.length == 0) {
            return src.length;
        }
        /* a --> b 如果a[0] == b[0] 比较delete，insert，不变 如果a[0] != b[0] 比较delete，insert，modify */
        String key = genKey(src, targ);
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        int modifyOrUnchangePrice = 0; // 此变量仅考虑修改还是不变
        if (src[0] == targ[0]) {
            modifyOrUnchangePrice = minEditDistance(tail(src), tail(targ)); // 首项相等,则后移一格,不修改
        } else {
            modifyOrUnchangePrice = MODIFY_PRICE + minEditDistance(tail(src), tail(targ)); // 不等,则修改a[0]->b[0]
        }
        int ins = INSERT_PRICE + minEditDistance(src, tail(targ)); // 插入的代价,
        int delete = DELETE_PRICE + minEditDistance(tail(src), targ); // 删除的代价,

        int res = Math.min(modifyOrUnchangePrice, Math.min(ins, delete));
        cache.put(key, res);
        return res;
    }

    public static String genKey(char[] src, char[] targ) {
        StringBuilder complexKey = new StringBuilder();
        complexKey.append(src);
        complexKey.append((char) 27); // 特意加上ascii码的27，避免任何可读字符串的混淆
        complexKey.append(targ);
        return complexKey.toString();
    }

    public static int minEditDistance(String src, String targ) {
        return minEditDistance(src.toCharArray(), targ.toCharArray());
    }

    public static void main(String[] args) {
        String src = "";
        String targ = "";
        src = "i am a stupid boy";
        
        targ = "you see a stupid boy";
        long t1 = System.currentTimeMillis();
        System.out.println("FROM {" + src + "} TO {" + targ + "}, likelyhood is " + minEditDistance(src, targ));
        long t2 = System.currentTimeMillis();
        System.out.println("TIME is " + (t2 - t1) + "ms");
        
        targ = "you see a smart boy";
        t1 = System.currentTimeMillis();
        System.out.println("FROM {" + src + "} TO {" + targ + "}, likelyhood is " + minEditDistance(src, targ));
        t2 = System.currentTimeMillis();
        System.out.println("TIME is " + (t2 - t1) + "ms");
        
        targ = "are you a stupid boy";
        t1 = System.currentTimeMillis();
        System.out.println("FROM {" + src + "} TO {" + targ + "}, likelyhood is " + minEditDistance(src, targ));
        t2 = System.currentTimeMillis();
        System.out.println("TIME is " + (t2 - t1) + "ms");
    }
}
