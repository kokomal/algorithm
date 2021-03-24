/**  
 * @Title: BaseBallVorpAlgo.java   
 * @Package: yuanjun.chen.base.dynamicprogramming   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年9月28日 上午10:20:15   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.base.dynamicprogramming.baseball;

import java.util.Arrays;
import java.util.Random;

/**   
 * @ClassName: BaseBallVorpAlgo   
 * @Description:对标CLRS-3课后习题15-12
 * 有N个职位，每个职位有P个候选球员，每个球员有特定的签约费，以及球员价值。
 * 在总预算为X元的情况下，招聘总价值最大的球员。(有些职位可以留空)
 * ★★★令Table B为第i号职位在不大于k的预算下的最大价值★★★ 
 * B[i][k]=max(B[i-1][k], B[i-1][X-cost[i]] + vorp[i])   
 * @author: 陈元俊 
 * @SpecialThanksTo: Michelle Bodnar & Andrew Lohr {虽然此二位的伪代码略别扭（有语法模糊&变量名冲突），但也算网上的较佳方案了}
 * @SpecialThanksTo: walkccc {此方案代码有问题，并且与上方案思路不一致，因此放弃，但也谢过此人的CLRS专题网站}
 * @date: 2018年9月28日 上午10:20:15  
 */
public class BaseBallVorpAlgo {
    /** N*P,总共N个位置，每个位置P个候选人. */
    private static BaseBallPlayer[][] players;
    private static int[][] tableB;
    /** N基本上是固定的,选择枚举的总数. */
    private static int N = BaseBallPos.values().length;
    private static int P;
    private static int X;
    /** N*X 候选人名单. */
    private static int[][] PP;
    /**   
     * @Title: initRandomPlayers   
     * @Description: 生成随机的球员  
     * @param pPlayers 每个位置P个候选人
     * @param budget 总预算 
     * @param BASESIGN 随机签约费用基数 
     * @param BASEVORP 随机VORP基数 
     * @return: void      
     */
    public static void initRandomPlayers(int pPlayers, int budget, int BASESIGN, int BASEVORP) {
        P = pPlayers;
        initBudget(budget);
        Random rd = new Random();
        players = new BaseBallPlayer[N][P];
        for (int i = 0; i < N; i++) {
            for(int j = 0; j < pPlayers; j++) {
                BaseBallPlayer player = new BaseBallPlayer();
                player.setPos(BaseBallPos.values()[i]);
                player.setName("Player" + i + j);
                player.setSignCost(rd.nextInt(BASESIGN) + 1); // 签约费用,[1-BASESIGN]
                player.setVorp(rd.nextInt(BASEVORP) + 1); // vorp,[1-BASEVORP]
                players[i][j] = player;
            }
        }
    }

    public static void resetBudget(int budget) {
        initBudget(budget);
    }
    
    private static void initBudget(int budget) {
        X = budget;
        System.out.printf("每个位置候选人有%d人, 总预算%d万元%n", P, X);
        PP = new int[N][X];
        tableB = new int[N + 1][X + 1]; // 新建tableB并初始化边界
        for(int i = 0; i <= N; i++) {
            tableB[i][0] = 0;
        }
        for (int j = 1; j <= X; j++) {
            tableB[0][j] = 0;
        }
    }
    
    /** Dp算法，X为预算总额. */
    public static void vorpSolve() {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= X; j++) {
                int q = tableB[i - 1][j];
                int p = -1;
                SMALL_LOOP: for (int k = 1; k <= P; k++) { // 逐一遍历第i职位的每一个候选人
                    int playerSignCost = players[i - 1][k - 1].getSignCost(); // 看第k个候选人的签约费
                    if (j < playerSignCost) { // 此时预算小于候选人的签约费，直接pass，否则下面的tableB寻址会报错
                        continue;
                    }
                    // 这是算法的核心，背包问题，看看自己塞入和不塞入，哪种方案最优，不断替换q并记录候选人的号码
                    int candidate = tableB[i - 1][j - playerSignCost] + players[i - 1][k - 1].getVorp();
                    if (candidate > q) {
                        q = candidate;
                        p = k; // 选第k个球员
                    }
                }
                tableB[i][j] = q;
                PP[i - 1][j - 1] = p; // 存候选人号码
            }
        }
        /*System.out.println("CANDIDATES TABLE IS...");
        for (int i = 0; i < N; i++) {
            System.out.println(Arrays.toString(PP[i]));
        }*/
        /*System.out.println("FINAL TABLEB IS...");
        for (int i = 0; i <= N; i++) {
            System.out.println(Arrays.toString(tableB[i]));
        }*/
        int i = N;
        int j = X;
        int C = 0;
        for (int k = 1; k <= N; k++) {
            if (tableB[i][j] != tableB[i - 1][j] && PP[i - 1][j - 1] != -1) {
                BaseBallPlayer chosen = players[i - 1][PP[i - 1][j - 1] - 1];
                int cost = chosen.getSignCost();
                int vorp = chosen.getVorp();
                System.out.println("关于职位 " + BaseBallPos.values()[i - 1] + " 选中的选手为 " + chosen + ", 花费金额 " + cost
                        + "万元, 获得的VORP " + vorp + "万元");
                C += cost;
                j = j - cost;
                System.out.println("预算还剩下: " + j + "万元");
            } else {
                System.out.println("关于职位 " + BaseBallPos.values()[i - 1] + ", 很遗憾将不选择任何人");
            }
            i--;
        }
        System.out.println("===========总开销 " + C + "万元===========");
        System.out.println("===========最大VORP " + tableB[N][X] + "万元===========");
    }

    public static void main(String[] args) {
        int BASESIGN = 40; // 人均签约基数,即实际签约分布在[1-40]
        int BASEVORP = 60; // 人均VORP基数,即实际VORP分布在[1-60]
        initRandomPlayers(10, 50, BASESIGN, BASEVORP); // 每一个位置有10个候选人,预算50万
        displayFullPlayers();
        drawline();
        displayConcisePlayers();
        drawline();
        vorpSolve();
        
        drawline();
        System.out.println("重新设置预算为" + 100 + "万元");
        resetBudget(100);
        vorpSolve();
    }

    private static void drawline() {
        System.out.println("========================================================================================================");
    }

    private static void displayFullPlayers() {
        for (int i = 0; i < BaseBallPos.values().length; i++) {
            System.out.println("职业" + BaseBallPos.values()[i] + ", 候选人名单:" + Arrays.toString(players[i]));
        }
    }
    
    private static void displayConcisePlayers() {
        for (int i = 0; i < BaseBallPos.values().length; i++) {
            System.out.printf("职业%-16s  ", BaseBallPos.values()[i]);
            BaseBallPlayer[] iPlayers = players[i];
            for (BaseBallPlayer p : iPlayers) {
                System.out.printf("%-8s", p.getSignCost() + "/" + p.getVorp());
            }
            System.out.println();
        }
    }
    
}
