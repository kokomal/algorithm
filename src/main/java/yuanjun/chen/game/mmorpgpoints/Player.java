/**
 * @Title: Player.java
 * @Package: yuanjun.chen.game.mmorpgpoints
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年10月12日 上午9:25:02
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.game.mmorpgpoints;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * @ClassName: Player
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: 陈元俊
 * @date: 2018年10月12日 上午9:25:02
 */
public class Player {

    // 从高到低
    public static class PlayerComparator implements Comparator<Player> {
        @Override
        public int compare(Player o1, Player o2) {
            if (o1.score > o2.score) {
                return -1;
            } else if (o1.score < o2.score){
                return 1;
            }
            return 0;
        }
    }
    public static Player generateSpecifiedPlayer(int index, int[] skills) {
        Player player = new Player(index);
        player.skillPoints = new int[skills.length];
        System.arraycopy(skills, 0, player.skillPoints, 0, skills.length);
        return player;
    }
    /**   
     * @Title: generateRandomPlayer   
     * @Description: 按照最大技能点进行随机加点  
     * @param index
     * @param fullSkillPts
     * @return: Player      
     */
    public static Player generateRandomPlayer(int index, Integer fullSkillPts) {
        Player player = new Player(index);
        Random rd = new Random();
        while (true) {
            int sum = 0;
            for (int i = 0; i < SkillEnum.values().length; i++) {
                int randomSkill = rd.nextInt(fullSkillPts) + 1;
                player.skillPoints[i] = randomSkill;
                sum += randomSkill;
            }
            int randX = rd.nextInt(SkillEnum.values().length); // 选择一个randX
            sum -= player.skillPoints[randX];
            if (sum <= fullSkillPts) {
                player.skillPoints[randX] = fullSkillPts - sum;
                break;
            }
        }
        return player;
    }
    
    public static void main(String[] args) {
        Long t1 = System.currentTimeMillis();
        for (int i = 0; i < 2000; i++) {
            Player pp = generateRandomPlayer(1, 1000);
            System.out.println(Arrays.toString(pp.skillPoints));
        }
        Long t2 = System.currentTimeMillis();
        System.out.println("Time is " + (t2 - t1) + "ms");
    }
    
    private int index;
    private int[] skillPoints = new int[SkillEnum.values().length];
    private Long score = 0L;
    
    private Integer fetchRandomSkill() {
        Random rd = new Random();
        int idx = rd.nextInt(SkillEnum.values().length);
        return skillPoints[idx];
    }

    public void fight(Player other) {
        if (fetchRandomSkill() > other.fetchRandomSkill()) {
            win();
        } else if (fetchRandomSkill() < other.fetchRandomSkill()) {
            other.win();
        } else {
            draw();
            other.draw();
        }
    }

    public void win() {
        score += 3L;
    }

    public void draw() {
        //score += 1L;
    }

    public Player(int index) {
        super();
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int[] getSkillPoints() {
        return skillPoints;
    }

    public void setSkillPoints(int[] skillPoints) {
        this.skillPoints = skillPoints;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

}
