/**
 * @Title: MinMutation.java
 * @Package: yuanjun.chen.base.other
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2019年5月9日 下午3:01:16
 * @version V1.0
 * @Copyright: 2019 All rights reserved.
 */
package yuanjun.chen.base.other;

import java.util.*;

/**
 * @ClassName: MinMutation
 * @author: 陈元俊
 * @date: 2019年5月9日 下午3:01:16
 */
public class MinMutation {
    public static int minMutation(String start, String end, String[] bank) {
        Set<String> sbank = new HashSet<>();
        sbank.addAll(Arrays.asList(bank));
        int val = minMutation(start, end, sbank);
        return  val == Integer.MAX_VALUE ? -1 : val;
    }

    private static Map<String, Integer> cache = new HashMap<>();

    public static int minMutation(String start, String end, Set<String> bank) {
        // System.out.println("START = " + start + " END = " + end + " AND BANK is " + bank);
        if (start.equals(end)) {
            return 0;
        }
        if (!bank.contains(end)) {
            return Integer.MAX_VALUE;
        }

        if (bank.contains(end) && gap(start, end) == 1) {
            return 1;
        }
        
        // System.out.println("GOTTA REMOVE " + end);
        bank.remove(end);
        
        List<String> candiList = new ArrayList<>();
        for (String candi : bank) {
            if (gap(candi, end) == 1) {
                candiList.add(candi);
            }
        }
        // System.out.println("CANDILIST " + candiList);
        if (candiList.size() == 0) return Integer.MAX_VALUE;
        
        int minDist = Integer.MAX_VALUE;
        for (String candi : candiList) {
            Set<String> nbank = new HashSet<>(bank);
            // System.out.println("NBANK " + nbank);
            int dist = minMutation(start, candi, nbank);
            if (dist < minDist) {
                minDist = dist;
            }
        }
        return minDist == Integer.MAX_VALUE ? Integer.MAX_VALUE : 1 + minDist;
    }

    private static int gap(String a, String b) {
        if (cache.containsKey(a + "-" + b)) {
            return cache.get(a + "-" + b);
        }
        int val = 0;

        int len = a.length();
        for (int i = 0; i < len; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                val++;
            }
        }
        cache.put(a + "-" + b, val);
        cache.put(b + "-" + a, val);
        return val;
    }

    public static void main(String[] args) {
        String start = "CCGGTT";
        String end = "ACGGTA";
        String[] bank = new String[] {"CCGGTA", "CCGCTA", "ACGGTA"};
        System.out.println(minMutation(start, end, bank));
    }
}
