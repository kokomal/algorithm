/**
 * @Title: InvestmentConselorAlgo.java
 * @Package: yuanjun.chen.base.dynamicprogramming
 * @Description: 动态规划经典案例，切钢条
 * @author: 陈元俊
 * @date: 2018年8月22日 下午1:28:17
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.dynamicprogramming;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @ClassName: InvestmentConselorAlgo
 * @Description: 投资顾问
 * @author: 陈元俊
 * @date: 2018年8月22日 下午1:28:17
 */
public class InvestmentConselorAlgo {
    private static final Logger logger = LogManager.getLogger(InvestmentConselorAlgo.class);
    private static double[] price_table;
    private static double[] revenue;
    private static int[] solutions;
    private static int fullmoney;

    private static Map<Integer, Integer> map = new HashMap<>();
    
    public static void setRules(final double[] rules, int srcmoney) {
        price_table = new double[rules.length];
        System.arraycopy(rules, 0, price_table, 0, rules.length);
        // revenue = new double[price_table.length + 1];
        fullmoney = srcmoney;
        revenue = new double[fullmoney + 1];
        solutions = new int[fullmoney + 1];
    }

        /**   
     * @Title: brute   
     * @Description: 暴力法取得最优解 
     * @param money[可用金额(万)]
     * @return: double最优收益，中间保存了解决方案map      
     */
    public static double brute(int money) {
        if (money == 0) {
            return 0;
        }
        double q = Double.MIN_VALUE;
        int rec = 0;
        for (int i = 1; i <= price_table.length && i <= money; i++) {
            double candidate = price_table[i - 1] + brute(money - i);
            if (q < candidate) {
                q = candidate;
                rec = i;
            }
        }
        map.put(money, rec);
        return q;
    }

    public static void dispBrute(int money) {
        while (money > 0) {
            int choice = map.get(money);
            System.out.println("【暴力法】选择资产包# " + choice);
            money -= choice;
        }
    }
    
    public static double topDpCutWrapper(int money) {
        cleanContext();
        double res = topdp(money);
        printCutRodSolution("【自顶向下DP法】", money);
        return res;
    }

    public static double bottomDpCutWrapper(int money) {
        cleanContext();
        double res = bottomdp(money);
        printCutRodSolution("【自底向上DP法】", money);
        return res;
    }

    private static void printCutRodSolution(String method, int money) {
        while (money > 0) {
            System.out.println(method + "选择资产包# " + solutions[money]);
            money = money - solutions[money];
        }
    }

    /**
     * @Title: bottomdp
     * @Description: MEMOIZED_CUT_ROD 自底向上，无递归
     * @param n
     * @return: int
     */
    private static double bottomdp(int money) {
        if (money == 0) {
            return 0;
        }
        revenue[0] = 0;
        for (int j = 1; j <= money; j++) {
            double q = Double.MIN_VALUE;
            for (int i = 1; i <= price_table.length && i <= j; i++) {
                double candidate = price_table[i - 1] + revenue[j - i];
                if (q < candidate) {
                    q = candidate;
                    solutions[j] = i;
                }
            }
            revenue[j] = q; // recipe从1递增，因此这里没有递归
        }
        return revenue[money];
    }

    /**
     * @Title: topdp
     * @Description: 自顶向下，有递归，但次数为O[n]次
     * @param n
     * @return: int
     */
    private static double topdp(int money) {
        if (money == 0) {
            return 0;
        }
        if (money < revenue.length && revenue[money] > Double.MIN_VALUE) {
            return revenue[money];
        }
        double q = Integer.MIN_VALUE;
        for (int i = 1; i <= price_table.length && i <= money; i++) {
            double candidate = price_table[i - 1] + topdp(money - i);
            if (q < candidate) {
                q = candidate;
                solutions[money] = i;
            }
        }
        revenue[money] = q;
        return q;
    }

    private static void cleanContext() {
        for (int i = 0; i < fullmoney; i++) {
            revenue[i] = Double.MIN_VALUE;
            solutions[i] = Integer.MIN_VALUE;
        }
        map.clear();
    }

    private static void expressResult(String method, int money, double rev) {
        System.out.println(method + "最大收益  " + rev);
        double perc = (rev - money) / money;
        System.out.printf(method + "收益率为 %2f%%", perc * 100);
    }
    
    public static void main(String[] args) throws Exception {
        /*  
         *  ┏━━━━━━┳━━━━━┳━━━━━┳━━━━━┳━━━━━┳━━━━━┳━━━━━┳━━━━━┓
         *  ┃投资金额┃  1  ┃  2  ┃  3  ┃  4  ┃  5  ┃  6  ┃  7  ┃
         *  ┣━━━━━━╋━━━━━╋━━━━━╋━━━━━╋━━━━━╋━━━━━╋━━━━━╋━━━━━┫
         *  ┃年化收益┃ 9.3%┃ 9.5%┃8.53%┃9.18%┃ 9.6%┃9.33%┃9.51%┃
         *  ┣━━━━━━╋━━━━━╋━━━━━╋━━━━━╋━━━━━╋━━━━━╋━━━━━╋━━━━━┫
         *  ┃预期收益┃1.093┃2.190┃3.256┃4.367┃5.480┃6.560┃7.666┃
         *  ┗━━━━━━┻━━━━━┻━━━━━┻━━━━━┻━━━━━┻━━━━━┻━━━━━┻━━━━━┛
         */
        double[] rules = new double[] {1.093, 2.190, 3.256, 4.367, 5.480, 6.560, 7.666 };
        System.out.println("输入的预期收益为 " + Arrays.toString(rules));
        int money = 21;
        setRules(rules, money);
        cleanContext();
        
        double rev = brute(money);
        dispBrute(money);
        expressResult("【暴力法】", money, rev);
        
        System.out.println("\n---------------------------------");
        rev = topDpCutWrapper(money);
        expressResult("【自顶向下DP法】", money, rev);
        
        System.out.println("\n---------------------------------");
        rev = bottomDpCutWrapper(money);
        expressResult("【自底向上DP法】", money, rev);
    }

}
