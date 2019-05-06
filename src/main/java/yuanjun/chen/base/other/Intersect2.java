/**
 * @Title: Intersect2.java
 * @Package: yuanjun.chen.base.other
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2019年5月6日 下午3:47:15
 * @version V1.0
 * @Copyright: 2019 All rights reserved.
 */
package yuanjun.chen.base.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @ClassName: Intersect2
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: 陈元俊
 * @date: 2019年5月6日 下午3:47:15
 */
public class Intersect2 {
    public static int[] intersect(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map1 = new HashMap<>();
        Map<Integer, Integer> map2 = new HashMap<>();
        for (int n1 : nums1) {
            if (map1.containsKey(n1)) {
                map1.put(n1, map1.get(n1) + 1);
            } else {
                map1.put(n1, 1);
            }
        }
        for (int n2 : nums2) {
            if (map2.containsKey(n2)) {
                map2.put(n2, map2.get(n2) + 1);
            } else {
                map2.put(n2, 1);
            }
        }

        List<Integer> lis = new ArrayList<>();
        Iterator<Entry<Integer, Integer>> it = map1.entrySet().iterator();
        while (it.hasNext()) {
            Entry<Integer, Integer> en = it.next();
            if (map2.containsKey(en.getKey())) {
                int min = Math.min(en.getValue(), map2.get(en.getKey()));
                for (int i = 0; i < min; i++) {
                    lis.add(en.getKey());
                }
            }
        }

        if (lis.isEmpty()) {
            return new int[0];
        }
        int[] res = new int[lis.size()];

        for (int i = 0; i < lis.size(); i++) {
            res[i] = lis.get(i);
        }
        return res;
    }
}
