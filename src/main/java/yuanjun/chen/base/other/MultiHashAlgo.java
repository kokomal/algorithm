/**
 * @Title: MultiHashAlgo.java
 * @Package: yuanjun.chen.base.other
 * @Description: 乘法Hash算法
 * @author: 陈元俊
 * @date: 2018年8月8日 下午2:31:16
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.other;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: MultiHashAlgo
 * @Description: 乘法Hash算法
 * @author: 陈元俊
 * @date: 2018年8月8日 下午2:31:16
 */
public class MultiHashAlgo {
    /** S = 0.618 * 2^32 但由于S会溢出，因此采用了补码. */
    private static final int s = 0x61c88647;

    /** 类似于A = 0.618 Knuth法 w=32, s*k算出2w位值, p=14, m=2^p=16384, s*k取低32位， 再取高14位即为hash */
    public static int hash(int k) {
        int sk = s * k;
        return -sk >>> 18;
    }

    public static void main(String[] args) {
        long l1 = (long) ((1L << 31) * (Math.sqrt(5) - 1));
        System.out.println("as 32 bit unsigned: " + l1);
        int i1 = (int) l1;
        System.out.println("as 32 bit signed: " + i1);
        System.out.println("MAGIC = " + 0x61c88647);
        ConcurrentHashMap<Integer, Integer> ccc = new ConcurrentHashMap<>();
        int collisions = 0;
        for (int i = 0; i < 1 << 14; i++) {
            int key = hash(i);
            if (ccc.containsKey(key)) {
                collisions++;
                ccc.put(key, ccc.get(key) + 1);
            } else {
                ccc.put(key, 1);
            }
        }
        System.out.println("collisions = " + collisions);
        System.out.println("finally show the hash distribution " + ccc);
    }
}
