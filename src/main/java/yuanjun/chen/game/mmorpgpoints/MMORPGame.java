/**
 * @Title: MMORPGame.java
 * @Package: yuanjun.chen.game.mmorpgpoints
 * @Description: 随机生成的Player可能在分布上有奇异的地方
 * @author: 陈元俊
 * @date: 2018年10月12日 上午10:05:37
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.game.mmorpgpoints;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: MMORPGame
 * @Description: 随机生成的Player可能在分布上有奇异的地方
 * @author: 陈元俊
 * @date: 2018年10月12日 上午10:05:37
 */
public class MMORPGame {
    private Player[] players;

    public MMORPGame(Player[] players) {
        super();
        this.players = players;
    }

    public void tournament() {
        for (int i = 0; i < players.length - 1; i++) {
            for (int j = i + 1; j < players.length; j++) {
                players[i].fight(players[j]);
            }
        }
        Arrays.sort(players, new Player.PlayerComparator());
        System.out.println("===output top 20 players===");
        for (int i = 0; i < 20; i++) {
            System.out.println("The winner Score is " + players[i].getScore() + " and the skills are "
                    + Arrays.toString(players[i].getSkillPoints()));
        }
        System.out.println("===output top 100 players by score desc===");
        for (int i = 0; i < 100; i++) {
            System.out.println(players[i].getScore());
        }
    }

    public static void main(String[] args) throws Exception {
         Long t1 = System.currentTimeMillis();
         Player[] massive = genRandomPlayers();
         Long t2 = System.currentTimeMillis();
         System.out.println("Generate Time is " + (t2 - t1) + "ms");
         MMORPGame game = new MMORPGame(massive);
         game.tournament();
         Long t3 = System.currentTimeMillis();
         System.out.println("Game Time is " + (t3 - t2) + "ms");
    }

    private static Player[] genRandomPlayers() throws InterruptedException {
        int mmo = 30000;
        int maxSkillPoints = 10000;
        ExecutorService cc = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Player[] massive = new Player[mmo];
        CountDownLatch couter = new CountDownLatch(mmo);
        for (int i = 0; i < mmo; i++) {
            cc.submit(new GenerateRandomPlayerTask(massive, couter, i, maxSkillPoints));
        }
        cc.shutdown();
        couter.await();
        return massive;
    }

}
