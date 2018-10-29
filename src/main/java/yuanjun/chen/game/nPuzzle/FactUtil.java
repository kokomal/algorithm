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
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: FactUtil
 * @Description: 带本地缓存的阶乘
 * @author: 陈元俊
 * @date: 2018年10月29日 下午5:34:21
 */
public class FactUtil {
    private static Map<Integer, BigInteger> cache = new HashMap<>();

    public static BigInteger fact(int n) {
        if (cache.containsKey(n)) {
            System.out.println("cache hit!!!");
            return cache.get(n);
        }
        BigInteger val = fact(n, BigInteger.valueOf(1L));
        cache.put(n, val);
        return val;
    }

    private static BigInteger fact(int n, BigInteger val) {
        if (n <= 1)
            return val;
        return fact(n - 1, val.multiply(BigInteger.valueOf(n)));
    }
    
    public static void main(String[] args) {
        System.out.println(fact(33));
        System.out.println(fact(32));
    }
}
