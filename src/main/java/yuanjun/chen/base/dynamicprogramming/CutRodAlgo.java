/**
 * @Title: CutRodAlgo.java
 * @Package: yuanjun.chen.base.dynamicprogramming
 * @Description: 动态规划经典案例，切钢条
 * @author: 陈元俊
 * @date: 2018年8月22日 下午1:28:17
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.dynamicprogramming;

import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yuanjun.chen.base.common.RandomGenner;

/**
 * @ClassName: CutRodAlgo
 * @Description: 动态规划经典案例，切钢条
 * @author: 陈元俊
 * @date: 2018年8月22日 下午1:28:17
 */
public class CutRodAlgo {
    private static final Logger logger = LogManager.getLogger(CutRodAlgo.class);
    /*--------------------------------------------------1--2--3--4---5---6---7---8---9---10*/
    private static int[] price_table;
    private static int[] recipe;
    
    public static void setRules(final int [] rules) {
        price_table = new int[rules.length];
        System.arraycopy(rules, 0, price_table, 0, rules.length);
        recipe = new int[price_table.length];
    }
    
    public static int brute(int n) {
        if (n == 0)
            return 0;
        int q = Integer.MIN_VALUE;
        int rec = 0;
        for (int i = 1; i <= price_table.length && i <= n; i++) {
            int candidate = price_table[i - 1] + brute(n - i);
            if (q < candidate) {
                q = candidate;
                rec = i;
            }
        }
        //logger.info("for " + n + " first cut = " + rec);
        return q;
    }

    public static int topDpCutWrapper(int n) {
        cleanRecipe();
        return topdp(n);
    }

    public static int bottomDpCutWrapper(int n) {
        cleanRecipe();
        return bottomdp(n);
    }

    /**
     * @Title: bottomdp
     * @Description: MEMOIZED_CUT_ROD 自底向上，无递归
     * @param n
     * @return: int
     */
    private static int bottomdp(int n) {
        if (n == 0)
            return 0;
        recipe[0] = 0;
        int rec = 0;
        for (int j = 1; j <= n; j++) {
            int q = Integer.MIN_VALUE;
            for (int i = 1; i <= j; i++) {
                int candidate = price_table[i - 1] + recipe[j - i];
                if (q < candidate) {
                    q = candidate;
                    rec = i;
                }
            }
            recipe[j] = q; // recipe从1递增，因此这里没有递归
        }
        logger.info("for " + n + " first cut = " + rec);
        return recipe[n];
    }

    /**   
     * @Title: topdp   
     * @Description: 自顶向下，有递归，但次数为O[n]次  
     * @param n
     * @return: int      
     */
    private static int topdp(int n) {
        if (n == 0)
            return 0;
        if (recipe[n] > Integer.MIN_VALUE) {
            return recipe[n];
        }
        int q = Integer.MIN_VALUE;
        int rec = 0;
        for (int i = 1; i <= price_table.length && i <= n; i++) {
            int candidate = price_table[i - 1] + topdp(n - i);
            if (q < candidate) {
                q = candidate;
                rec = i;
            }
        }
        recipe[n] = q;
        logger.info("for " + n + " first cut = " + rec);
        return q;
    }

    private static void cleanRecipe() {
        for (int i = 0; i < price_table.length; i++) {
            recipe[i] = Integer.MIN_VALUE;
        }
    }

    public static void main(String[] args) throws Exception {
        Integer[] arr1 = RandomGenner.generateRandomTArray(30, 1, 200, Integer.class);
        Arrays.sort(arr1);
        int[] rules= Arrays.stream(arr1).mapToInt(Integer::valueOf).toArray(); // java8
        logger.info("rules -- " + Arrays.toString(rules));
        setRules(rules);
        int n = 8;
        int q = brute(n);
        logger.info("brute " + n + " the max revenue is " + q);
        logger.info("------------------------------");
        q = topDpCutWrapper(n);
        logger.info("topDpCutWrapper " + n + " the max revenue is " + q);
        logger.info("------------------------------");
        q = bottomDpCutWrapper(n);
        logger.info("bottomDpCutWrapper " + n + " the max revenue is " + q);
    }
}