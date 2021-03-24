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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;

/**
 * @ClassName: InvestmentConselorAlgo
 * @Description: 投资顾问
 * ---------------------------------------------------------
 * 爬虫爬取了一些货币基金组合，准投金额连续从1万到n万（n可能会比较小）
 * 如果客户有m万金额（m相对于n可能会比较大），如何推荐用户投资，使得收益最大？
 * ---------------------------------------------------------
 * 注意此处用贪心算法是得不到最优解的，简证如下：
 * 投资包金额    1        2       3
 * 投资包收益  0.01    0.8     0.9
 * 如果用户有5万，按照贪心策略，选2个0.8（因为2号的投资收益最大），剩下0.01，收益为1.61，这个收益是小于
 * 2号+3号的投资组合收益的
 * ---------------------------------------------------------
 * {TODO 1： 如果货币基金组合有空洞怎么办？}
 * @author: 陈元俊
 * @date: 2018年9月26日 下午1:28:17
 */
public class InvestmentConselorAlgo {

    private static final String BOTTOM_UP_DP = "【自底向上DP法】";
    private static final String TOP_DOWN_DP = "【自顶向下DP法】";
    private static final String BRUTE = "【暴力法】";
    
    // private static final Logger logger = LogManager.getLogger(InvestmentConselorAlgo.class);
    private static double[] price_table; // 代价数组
    private static double[] revenue; // 收益，对应每一个金额
    private static int[] solutions; // 解决方案
    private static int fullmoney; // 总金额

    private static Map<Integer, Integer> map = new HashMap<>(); // 暴力法的中间最优解寄存map
    
    public static void setRules(final double[] rules, int srcmoney) {
        price_table = new double[rules.length];
        System.arraycopy(rules, 0, price_table, 0, rules.length);
        // revenue = new double[price_table.length + 1];
        fullmoney = srcmoney;
        revenue = new double[fullmoney + 1];
        solutions = new int[fullmoney + 1];
        map.clear();
    }

        /**   
     * @Title: brute   
     * @Description: 暴力法取得最优解 
     * @param money[可用金额(万)]
     * @return: double最优收益，中间保存了解决方案map      
     */
    private static double brute(int money) {
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

    /** 暴力法的disp方法 */
    private static void dispBrute(int money) {
        Map<Integer, Integer> packages = new TreeMap<>();
        while (money > 0) {
            int choice = map.get(money);
            System.out.println(BRUTE + "选择资产包# " + choice);
            collectPackages(packages, choice);
            money -= choice;
        }
        viewTotalPackage(packages);
    }

    private static void collectPackages(Map<Integer, Integer> packages, int choice) {
        if (!packages.containsKey(choice)) {
            packages.put(choice, 1);
        } else {
            packages.put(choice, packages.get(choice) + 1);
        }
    }

    private static void viewTotalPackage(Map<Integer, Integer> packages) {
        Iterator<Entry<Integer, Integer>> kv = packages.entrySet().iterator();
        System.out.println("===========开始汇总资产包的选择===========");
        while (kv.hasNext()) {
            Entry<Integer, Integer> v = kv.next();
            System.out.println("资产包# " + v.getKey() + " 选择" + v.getValue() + "个");
        }
        System.out.println("===========汇总资产包的选择完成===========");
    }
    
    public static double bruteWrapper(int money) {
        cleanContext();
        double res = brute(money);
        dispBrute(money);
        return res;
    }

    public static double topDpCutWrapper(int money) {
        cleanContext();
        double res = topdp(money);
        printCutRodSolution(TOP_DOWN_DP, money);
        return res;
    }

    public static double bottomDpCutWrapper(int money) {
        cleanContext();
        double res = bottomdp(money);
        printCutRodSolution(BOTTOM_UP_DP, money);
        return res;
    }

    private static void printCutRodSolution(String method, int money) {
        Map<Integer, Integer> packages = new TreeMap<>();
        while (money > 0) {
            System.out.println(method + "选择资产包# " + solutions[money]);
            collectPackages(packages, solutions[money]);
            money = money - solutions[money];
        }
        viewTotalPackage(packages);
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
            revenue[j] = q; // revenue从1递增，因此这里没有递归
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
        System.out.printf(method + "最大总收益 %2.4f万元 \n", rev);
        double perc = (rev - money) / money;
        System.out.printf(method + "收益率为 %2f%%", perc * 100);
    }
    
    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        /*  
         *  ┏━━━━━━┳━━━━━┳━━━━━┳━━━━━┳━━━━━┳━━━━━┳━━━━━┳━━━━━┓
         *  ┃投资金额┃  1  ┃  2  ┃  3  ┃  4  ┃  5  ┃  6  ┃  7  ┃
         *  ┣━━━━━━╋━━━━━╋━━━━━╋━━━━━╋━━━━━╋━━━━━╋━━━━━╋━━━━━┫
         *  ┃年化收益┃9.13%┃9.21%┃9.53%┃9.54%┃9.56%┃9.57%┃9.55%┃
         *  ┣━━━━━━╋━━━━━╋━━━━━╋━━━━━╋━━━━━╋━━━━━╋━━━━━╋━━━━━┫
         *  ┃预期收益┃1.091┃2.184┃3.286┃4.382┃5.478┃6.574┃7.669┃
         *  ┗━━━━━━┻━━━━━┻━━━━━┻━━━━━┻━━━━━┻━━━━━┻━━━━━┻━━━━━┛
         */
        double[] yearIncome = new double[] {0.0913, 0.0921, 0.0953, 0.0954, 0.0956, 0.0957, 0.0955};
        double[] rules = new double[yearIncome.length];
        for (int i = 0; i < yearIncome.length; i++) {
            rules[i] = (i + 1) * (1 + yearIncome[i]);
        }
        drawTable(yearIncome, rules);
        
        int money = 32;
        System.out.println("客户持有资金 ￥" + money + "万元 ");
        setRules(rules, money);
        double rev;
        long t1, t2;
        t1 = System.currentTimeMillis();
        rev = bruteWrapper(money);
        expressResult(BRUTE, money, rev);
        t2 = System.currentTimeMillis();
        System.out.println(" 耗时" + (t2 - t1) + "ms");
           
        System.out.println("---------------------------------");
        System.out.println("接下来测试" + TOP_DOWN_DP);
        new Scanner(System.in);
        //input.next();
        
        t1 = System.currentTimeMillis();
        rev = topDpCutWrapper(money);
        expressResult(TOP_DOWN_DP, money, rev);
        t2 = System.currentTimeMillis();
        System.out.println(" 耗时" + (t2 - t1) + "ms");
        
        System.out.println("---------------------------------");
        System.out.println("接下来测试" + BOTTOM_UP_DP);
        new Scanner(System.in);
        //input.next();
        
        t1 = System.currentTimeMillis();
        rev = bottomDpCutWrapper(money);
        expressResult(BOTTOM_UP_DP, money, rev);
        t2 = System.currentTimeMillis();
        System.out.println(" 耗时" + (t2 - t1) + "ms");
    }

    private static void drawTable(double[] yearIncome, double[] rules) {
        StringBuilder table = new StringBuilder();
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < rules.length * 7; i++) {
            line.append("=");
        }
        table.append(line);
        table.append("\n┃投资金额┃  1  ┃  2  ┃  3  ┃  4  ┃  5  ┃  6  ┃  7  ┃");
        table.append("\n").append(line);
        table.append("\n┃年化收益┃");
        for (int i = 0; i < yearIncome.length; i++) {
            table.append(String.format("%2.2f%%", 100 * yearIncome[i])).append("┃");
        }
        table.append("\n" + line);
        table.append("\n┃预期收益┃");
        for (int i = 0; i < rules.length; i++) {
            table.append(String.format("%1.3f", rules[i])).append("┃");
        }
        table.append("\n").append(line);
        System.out.println(table.toString());
    }

}
