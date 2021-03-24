/**
 * @Title: MakeChangeAlgo.java
 * @Package: yuanjun.chen.base.greedy.makechange
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年10月25日 上午8:53:06
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.greedy.makechange;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: MakeChangeAlgo
 * @Description: 找零钱问题 参见CLRS-3 16-2
 * @author: 陈元俊
 * @date: 2018年10月25日 上午8:53:06
 */
public class MakeChangeAlgo {

    /* coinsSet为零钱集合， v为待凑金额总额 */
    public int solve(Set<Integer> coinsSet, int v) {
        Set<Integer> uniques = new HashSet<>();
        // 代表i金额需要的最小硬币数 v+1维
        int[] numCoins = new int[v + 1];
        // 结果数组 v维
        int[] choice = new int[v + 1];
        for (int i = 1; i <= v; i++) {
            int bestCoin = -1;
            int bestNum = Integer.MAX_VALUE;
            for (int coin : coinsSet) {
                if (coin <= i && numCoins[i - coin] != Integer.MAX_VALUE) { // coin面值太大不考虑，前置不可解也不考虑
                    int challenge = 1 + numCoins[i - coin];
                    if (challenge < bestNum) {
                        bestNum = challenge;
                        bestCoin = coin;
                    }
                }
            }
            numCoins[i] = bestNum;
            choice[i] = bestCoin;
        }
        // System.out.println(Arrays.toString(choice));
        int iter = v;
        if (choice[iter] == -1) {
            System.out.println("IMPOSSIBLE!");
            return -1;
        }
        int count = 0;
        while(iter > 0) {
            System.out.println("CHOOSE COIN " + choice[iter]);
            uniques.add(choice[iter]);
            iter = iter - choice[iter];
            count++;
        }
        System.out.println("IN ALL USE " + count + " COINS.");
        return uniques.size();
    }
    
    public static void main(String[] args) {
        MakeChangeAlgo algo = new MakeChangeAlgo(); // 28 41. 44. 54 57 
        Set<Integer> coinsSet = new HashSet<>();
        coinsSet.add(3);
        coinsSet.add(4);
        coinsSet.add(6);
        coinsSet.add(8);
        coinsSet.add(13);
        
        algo.solve(coinsSet, 28);
        algo.solve(coinsSet, 41);
        algo.solve(coinsSet, 44);
        algo.solve(coinsSet, 54);
        algo.solve(coinsSet, 57);
    }
}
