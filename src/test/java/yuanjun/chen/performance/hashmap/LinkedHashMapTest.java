/**
 * @Title: LinkedHashMapTest.java
 * @Package: yuanjun.chen.performance.hashmap
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年10月25日 下午2:24:45
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.performance.hashmap;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Test;

/**
 * @ClassName: LinkedHashMapTest
 * @Description: LinkedHashMap 模拟 LRU的实现，注意其重载了removeEldestEntry
 * @author: 陈元俊
 * @date: 2018年10月25日 下午2:24:45
 */
public class LinkedHashMapTest {
    @Test
    public void testLRUusingLinkedHashMap() {
        Map<String, String> hmmap = new LinkedHashMap<String, String>(20, 0.75f, true) {
            private static final long serialVersionUID = 1;
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > 10;
            }
        };
        for (int i = 1; i <= 10; i++) {
            hmmap.put(String.valueOf(i) + "_KEY", String.valueOf(i) + "_VAL");
        }
        disp(hmmap);
        System.out.println("\n---------------------------------");
        for (int i = 11; i <= 20; i++) {
            hmmap.put(String.valueOf(i) + "_KEY", String.valueOf(i) + "_VAL");
        }
        disp(hmmap);
        /* 可见老数据1-10不见了 */
    }

    private void disp(Map<String, String> hmmap) {
        for (Map.Entry<String, String> entry : hmmap.entrySet()) {
            System.out.print(entry.getValue() + "-->");
        }
    }
}
