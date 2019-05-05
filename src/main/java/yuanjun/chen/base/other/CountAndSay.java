/**
 * @Title: CountAndSay.java
 * @Package: yuanjun.chen.base.other
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2019年5月5日 下午3:28:40
 * @version V1.0
 * @Copyright: 2019 All rights reserved.
 */
package yuanjun.chen.base.other;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: CountAndSay
 * @Description: LeetCode 38
 * 报数序列是一个整数序列，按照其中的整数的顺序进行报数，得到下一个数。其前五项如下：
    1.     1
    2.     11
    3.     21
    4.     1211
    5.     111221
    1 被读作  "one 1"  ("一个一") , 即 11。
    11 被读作 "two 1s" ("两个一"）, 即 21。
    21 被读作 "one 2",  "one 1" （"一个二" ,  "一个一") , 即 1211。
    给定一个正整数 n（1 ≤ n ≤ 30），输出报数序列的第 n项。
    注意：整数顺序将表示为一个字符串。
 * @author: 陈元俊
 * @date: 2019年5月5日 下午3:28:40
 */
public class CountAndSay {
    private static Map<Integer, String> cache = new HashMap<>();
    static {
        cache.put(1, "1");
    }

    public static String countAndSay(int n) {
        if (cache.containsKey(n)) {
            return cache.get(n);
        }
        String pre = countAndSay(n - 1);
        int len = pre.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len;) {
            char x1 = pre.charAt(i);
            int count = 1;
            for (int j = i + 1; j < len; j++) {
                char x2 = pre.charAt(j);
                if (x1 == x2) {
                    count++;
                } else {
                    break;
                }
            }
            sb.append(Integer.toString(count));
            sb.append(x1);
            i += count;
        }
        cache.put(n, sb.toString());
        return sb.toString();
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 5; i++) {
            System.out.println(countAndSay(i));
        }
        // System.out.println(countAndSay(3));
    }
}
