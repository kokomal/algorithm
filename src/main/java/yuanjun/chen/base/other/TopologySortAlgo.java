/**
 * @Title: TopologySortAlgo.java
 * @Package: yuanjun.chen.base.other
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2019年4月22日 下午1:45:13
 * @version V1.0
 * @Copyright: 2019 All rights reserved.
 */
package yuanjun.chen.base.other;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @ClassName: TopologySortAlgo
 * @Description: 拓扑排序示范代码，采用朴素算法解决
 * @author: 陈元俊
 * @date: 2019年4月22日 下午1:45:13
 */
public class TopologySortAlgo {
    private static Map<String, Set<String>> dps = new HashMap<>();

    private static Integer[][] adjs = null; // 相邻矩阵
    private static Map<String, Integer> seqs = new HashMap<>(); // 记录元素序号和值的映射表

    public static void init(String filename) {
        dps.clear();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(filename);
        Set<String> whole = new HashSet<>();
        try (FileReader reader = new FileReader(url.getFile()); BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] kv = line.split(":");
                // System.out.println(Arrays.toString(kv));
                String key = kv[0];
                Set<String> set = new HashSet<>();
                String[] vals = kv[1].split(",");
                Collections.addAll(set, vals); // V进入dps的value
                dps.put(key, set);
                whole.add(key); // key记录到whole
                Collections.addAll(whole, vals); // 同时记录一下所有的KV到whole
            }
        } catch (IOException e) {
        }

        whole.stream().filter(ele -> !dps.containsKey(ele)).forEach(ele -> dps.put(ele, new HashSet<>()));
        /*
         * 理论上，准备工作已经做完，但这里仍然采用相邻矩阵的方法建模
         * 
         */
        adjs = new Integer[whole.size()][whole.size()];
        seqs.clear();
        for (int j = 0; j < whole.size(); j++) {
            Arrays.fill(adjs[j], 0);
        }
        int i = 0;
        for (String ele : whole) {
            seqs.put(ele, i);
            i++;
        }
        System.out.println(seqs);
        for (String ele : whole) {
            Set<String> vals = dps.get(ele);
            int start = seqs.get(ele);
            for (String val : vals) {
                int end = seqs.get(val);
                adjs[end][start] += 1;
            }
            i++;
        }
        for (Integer[] arr : adjs) {
            System.out.println(Arrays.toString(arr));
        }

    }

    public static void init(final Map<String, Set<String>> inputDeps) {
        dps.clear();
        dps.putAll(inputDeps);
    }

    public static void solve() {
        Set<String> output = new LinkedHashSet<>();
        Set<String> input = new HashSet<>();
        dps.keySet().stream().forEach(ele -> input.add(ele));
        Iterator<String> it = input.iterator();
        while (!input.isEmpty()) {
            String key = it.next();
            Set<String> vals = dps.get(key);
            if (vals.isEmpty() || output.containsAll(vals)) { // 孤单节点 or 安全节点
                output.add(key);
                it.remove();
                it = input.iterator();
                continue;
            }
        }
        for (String ele : output) {
            System.out.print(ele + "->");
        }
        System.out.println();
    }
    
    public static void solveGraph() {
        // 屡次遍历各个列，找出入度为0的，剔除后继续 
        // adjs[][] 为相邻矩阵，seqs<String Int>为字符串:序列映射表
        Set<String> output = new LinkedHashSet<>();
        int iter = seqs.size();
        while (iter > 0) {
            String legalOne = findNextInDegreeZeroColumn(output);
            clearOneLine(seqs.get(legalOne));
            output.add(legalOne);
            iter--;
        }
        System.out.println(output);
    }

    private static void clearOneLine(int legalOne) {
        Arrays.fill(adjs[legalOne], 0);
    }

    private static String findNextInDegreeZeroColumn(Set<String> exclude) {
        Set<Entry<String, Integer>> entrySet = seqs.entrySet();
        Iterator<Entry<String, Integer>> iter = entrySet.iterator();
        while (iter.hasNext()) {
            Entry<String, Integer> ent = iter.next();
            if (!exclude.contains(ent.getKey())) {
                Integer idx = ent.getValue();
                if (judgeColumnIsAllZero(idx)) {
                    return ent.getKey();
                }
            }
        }
        return null;
    }

    /* 找到idx所在列是否为0入度 */
    private static boolean judgeColumnIsAllZero(Integer idx) {
        int size = adjs[0].length;
        int i = 0;
        while (i < size) {
            if (adjs[i][idx] != 0) {
                return false;
            }
            i++;
        }
        return true;
    }

    public static void main(String[] args) {
        Map<String, Set<String>> dependants = new HashMap<>();
        String A1 = "A1";
        Set<String> listA1 = new HashSet<>();
        String A2 = "A2";
        Set<String> listA2 = new HashSet<>();
        String A3 = "A3";
        Set<String> listA3 = new HashSet<>();
        listA3.add("A1");
        String A4 = "A4";
        Set<String> listA4 = new HashSet<>();
        listA4.add("A2");
        String A5 = "A5";
        Set<String> listA5 = new HashSet<>();
        listA5.add("A4");
        listA5.add("A3");
        String A6 = "A6";
        Set<String> listA6 = new HashSet<>();
        listA6.add("A5");
        dependants.put(A1, listA1);
        dependants.put(A2, listA2);
        dependants.put(A3, listA3);
        dependants.put(A4, listA4);
        dependants.put(A5, listA5);
        dependants.put(A6, listA6);
        // init(dependants);
        // solve();

        System.out.println("=================");

        init("deps.txt");
        // solve();
        solveGraph();
    }
}
