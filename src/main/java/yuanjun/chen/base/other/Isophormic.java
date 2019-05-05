/**  
 * @Title: Isophormic.java   
 * @Package: yuanjun.chen.base.other   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2019年4月29日 下午4:40:44   
 * @version V1.0 
 * @Copyright: 2019 All rights reserved. 
 */
package yuanjun.chen.base.other;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**   
 * @ClassName: Isophormic   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 陈元俊 
 * @date: 2019年4月29日 下午4:40:44  
 */
public class Isophormic {
    public static boolean isIsomorphic(String s, String t) {
        Set<String> ss1 = genSetX(s);
        Set<String> ss2 = genSetX(t);
        boolean isAll = ss1.size() == ss2.size();
        ss1.removeAll(ss2);
        return isAll && ss1.size() == 0;
    }

    private static Set<String> genSetX(String s) {
        Map<Character, StringBuilder> records = new HashMap<>();
        char[] arrs = s.toCharArray();
        for (int i = 0; i < arrs.length; i++) {
            if (records.containsKey(arrs[i])) {
                records.get(arrs[i]).append('-');
                records.get(arrs[i]).append(Integer.toString(i));
            } else {
                records.put(arrs[i], new StringBuilder(Integer.toString(i)));
            }
        }
        Set<String> res = new HashSet<>();
        for (StringBuilder sb : records.values()) {
            res.add(sb.toString());
        }
        System.out.println("RAW " + s + " -> " + records);
        return res;
    }
    
    public static void main(String[] args) {
        System.out.println(isIsomorphic("china", "japan"));
        System.out.println(isIsomorphic("egg", "add"));
    }
}
