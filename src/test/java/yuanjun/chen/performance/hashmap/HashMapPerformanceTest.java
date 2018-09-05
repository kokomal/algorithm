/**
 * @Title: HashMapPerformanceTest.java
 * @Package: yuanjun.chen.performance.hashmap
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年9月5日 上午9:05:23
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.performance.hashmap;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 * @ClassName: HashMapPerformanceTest
 * @Description: 测试HashMap的性能，初始化
 * @author: 陈元俊
 * @date: 2018年9月5日 上午9:05:23
 */
public class HashMapPerformanceTest {
    @Test
    public void testInitHashMap() {
        int loops = 10000000; // 一千万的容量

        Map<Integer, Integer> map = new HashMap<>();
        long s1 = System.currentTimeMillis();
        for (int i = 0; i < loops; i++) {
            map.put(i, i);
        }
        long s2 = System.currentTimeMillis();
        System.out.println("未初始化容量，耗时 ： " + (s2 - s1) + "ms");

        Map<Integer, Integer> map1 = new HashMap<>(loops / 2);
        long s5 = System.currentTimeMillis();
        for (int i = 0; i < loops; i++) {
            map1.put(i, i);
        }
        long s6 = System.currentTimeMillis();
        System.out.println("初始化容量" + (loops / 2) + "，耗时 ： " + (s6 - s5) + "ms");

        Map<Integer, Integer> map2 = new HashMap<>(loops);
        long s3 = System.currentTimeMillis();
        for (int i = 0; i < loops; i++) {
            map2.put(i, i);
        }
        long s4 = System.currentTimeMillis();
        System.out.println("初始化容量为" + loops + "，耗时 ： " + (s4 - s3) + "ms");
    }
}
