/**  
 * @Title: DPKnapsackAlgo.java   
 * @Package: yuanjun.chen.base.greedy.knapsack   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年10月19日 下午3:35:43   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.base.greedy.knapsack;

import java.util.Arrays;

/**   
 * @ClassName: DPKnapsackAlgo   
 * @Description: 动态规划完全背包算法 ，同一类型的金块无穷) 
 * @author: 陈元俊 
 * @date: 2018年10月19日 下午3:35:43  
 */
public class DPFullKnapsackAlgo {
    private static GoldBar<Integer>[] goldbars; // 待塞入背包的奇形怪状的金块列表
    private static int[] weight_value_table; // 容量为CAPACITY
    private static int[] best_choice;
    
    public static void init(final GoldBar<Integer>[] bars, Integer capacity) {
        goldbars = bars;
        // 背包容量
        weight_value_table = new int[capacity];
        best_choice = new int[capacity];
        int nGoldBars = goldbars.length;
        for (GoldBar<Integer> goldbar : goldbars) {
            int weight = goldbar.weight;
            int value = goldbar.value;

            if (weight > capacity) { // 单体直接撑破，无视之
                continue;
            }
            // challenge table
            int valueAtIdx = getValueAtIndex(weight);
            if (valueAtIdx < value) {
                setValueAtIndex(weight, value);
                setChoiceAtIndex(weight, weight); // 某个weight上的选择是自己
            }
            for (int k = weight + 1; k <= capacity; k++) {
                int s = k - weight;
                int challenger = getValueAtIndex(s) + value;
                if (challenger > getValueAtIndex(k)) {
                    setValueAtIndex(k, challenger);
                    setChoiceAtIndex(k, weight); // 大于自己的，如果逆袭，则更新为自己
                }
            }
        }
        
        System.out.println("WEIGHT_VALUE ARRAY: " +Arrays.toString(weight_value_table));
        System.out.println("BEST CHOICE ARRAY: " + Arrays.toString(best_choice));
        
        int wt = getChoiceAtIndex(capacity);
        System.out.println("MAX VALUE is " + getValueAtIndex(capacity));
        int cur = capacity; // 目前重量
        while(wt > 0) {
            System.out.println("CHOOSE WT." + wt + " AND VALUE is " + findValueByWeight(wt));
            cur = cur - wt;
            if (cur < 1) {
                break;
            }
            wt = getChoiceAtIndex(cur);
        }
    }
    
    static Integer getValueAtIndex(int idx) {
        return weight_value_table[idx - 1];
    }
    
    static void setValueAtIndex(int idx, Integer value) {
        weight_value_table[idx - 1] = value;
    }
    
    static Integer getChoiceAtIndex(int idx) {
        return best_choice[idx - 1];
    }
    
    static void setChoiceAtIndex(int idx, Integer value) {
        best_choice[idx - 1] = value;
    }
    
    static int findValueByWeight(int wt) { // 低效了一点，存hashmap稍微好一点，但空间就多了
        int nGoldBars = goldbars.length;
        for (GoldBar<Integer> goldbar : goldbars) {
            if (wt == goldbar.weight) {
                return goldbar.value;
            }
        }
        return 0;
    }
    
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        GoldBar<Integer>[] goldbars = new GoldBar[3];
        goldbars[0] = new GoldBar<>();
        goldbars[0].value = 300;
        goldbars[0].weight = 14;
        
        goldbars[1] = new GoldBar<>();
        goldbars[1].value = 98;
        goldbars[1].weight = 5;
        
        goldbars[2] = new GoldBar<>();
        goldbars[2].value = 20;
        goldbars[2].weight = 1;
        
        init(goldbars, 20);
    }
}
