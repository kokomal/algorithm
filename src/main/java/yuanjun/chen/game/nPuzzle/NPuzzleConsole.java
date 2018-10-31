/**
 * @Title: NPuzzleConsole.java
 * @Package: yuanjun.chen.game.nPuzzle
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年10月30日 下午4:41:26
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.game.nPuzzle;

import java.util.Scanner;

/**
 * @ClassName: NPuzzleConsole
 * @Description: 控制台
 * @author: 陈元俊
 * @date: 2018年10月30日 下午4:41:26
 */
public class NPuzzleConsole {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        NPuzzleEasyAlgo game = new NPuzzleEasyAlgo();
        String read = "init";

        while (!read.equals("q")) {
            System.out.println(
                    "PLEASE INPUT THE HARD LEVEL: (e) for Easy, (m) for Medium, (h) for Hard or Random. Press(q) to quit.");
            Scanner scan = new Scanner(System.in);
            read = scan.nextLine().trim();
            int level = 0;
            if (read.equals("q")) {
                System.out.println("GOODBYE.");
                System.exit(0);
            }
            if (read.equalsIgnoreCase("e")) {
                level = 25;
            } else if (read.equalsIgnoreCase("m")) {
                level = 35;
            } 
            else {
                level = 100;
            }
            game.generateRandomGame(4, level); // level 4默认
            System.out.println("GAME GENERATED, Press any key to continue.");
            read = scan.nextLine().trim();
            long t1 = System.currentTimeMillis();
            game.solve();
            long t2 = System.currentTimeMillis();
            System.out.println("PROBLEM SOLVED, using " + (t2 - t1) + "ms.");
        }
    }
}
