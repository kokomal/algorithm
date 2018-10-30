/**
 * @Title: FactUtil.java
 * @Package: yuanjun.chen.game.nPuzzle
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年10月29日 下午5:34:21
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.game.nPuzzle;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: FactUtil
 * @Description: 带本地缓存的阶乘
 * @author: 陈元俊
 * @date: 2018年10月29日 下午5:34:21
 */
public class FactUtil {
    private static Map<Integer, BigInteger> cache = new ConcurrentHashMap<>();
    static {
        warmUp(30); // 预热
    }

    @Deprecated
    public static BigInteger fact(int n) { // 尾递归带缓存，似乎性价比不好
        if (cache.containsKey(n)) {
            // System.out.println("cache hit!!!");
            return cache.get(n);
        }
        BigInteger val = fact(n, BigInteger.ONE);
        cache.put(n, val);
        return val;
    }

    @Deprecated
    private static BigInteger fact(int n, BigInteger val) {
        if (n <= 1) {
            return val;
        }
        return fact(n - 1, val.multiply(BigInteger.valueOf(n)));
    }

    public static void warmUp(int limit) {
        limit = limit > 100 ? 100 : limit;
        for (int i = 0; i <= limit; i++) {
            factEx(i);
        }
    }

    public static BigInteger factEx(int n) { // 威力加强版，不带递归，带缓存
        if (cache.containsKey(n)) {
            // System.out.println("cache hit!!!");
            return cache.get(n);
        }
        if (n <= 1) {
            return BigInteger.ONE;
        }
        BigInteger res = BigInteger.valueOf(n);
        for (int i = n - 1; i >= 1; i--) {
            if (cache.containsKey(i)) {
                // System.out.println("SUB hit!");
                res = cache.get(i).multiply(res);
                break;
            } else {
                res = res.multiply(BigInteger.valueOf(i));
            }
        }
        cache.put(n, res);
        return res;
    }

    public static void main(String[] args) {
        System.out.println(cache);
        System.out.println(factEx(33));
        System.out.println(cache);
    }
}
