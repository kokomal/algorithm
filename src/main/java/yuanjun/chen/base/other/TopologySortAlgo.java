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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: TopologySortAlgo
 * @Description: 拓扑排序示范代码，采用朴素算法解决
 * @author: 陈元俊
 * @date: 2019年4月22日 下午1:45:13
 */
public class TopologySortAlgo {
    private static Map<String, Set<String>> dps = new HashMap<>();

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
        //init(dependants);
        //solve();

        System.out.println("=================");

        init("deps.txt");
        solve();
    }
}
