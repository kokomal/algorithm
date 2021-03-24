/**
 * @Title: GeneratePlayerTask.java
 * @Package: yuanjun.chen.game.mmorpgpoints
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年10月12日 上午10:41:49
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.game.mmorpgpoints;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName: GeneratePlayerTask
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: 陈元俊
 * @date: 2018年10月12日 上午10:41:49
 */
public class GenerateRandomPlayerTask implements Runnable {
    private CountDownLatch couter;
    private final Player[] massive;
    private int i;
    private int maxSkillPoints;

    public GenerateRandomPlayerTask(Player[] massive, CountDownLatch couter, int i, int maxSkillPoints) {
        super();
        this.couter = couter;
        this.massive = massive;
        this.i = i;
        this.maxSkillPoints = maxSkillPoints;
    }

    @Override
    public void run() {
        Player pp = Player.generateRandomPlayer(i, maxSkillPoints);
        synchronized (massive) {
            massive[i] = pp;
        }
        this.couter.countDown();
    }

}
