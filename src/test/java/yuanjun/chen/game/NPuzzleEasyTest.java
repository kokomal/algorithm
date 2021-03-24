/**  
 * @Title: NDigitsTest.java   
 * @Package: yuanjun.chen.game   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年10月29日 上午9:09:36   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.game;

import org.junit.Test;
import yuanjun.chen.game.nPuzzle.NPuzzleEasyAlgo;

import java.util.ArrayList;
import java.util.List;

/**   
 * @ClassName: NPuzzleTest   
 * @Description: 全部测试N-Puzzle，及其cache的策略  
 * @author: 陈元俊 
 * @date: 2018年10月29日 上午9:09:36  
 */
public class NPuzzleEasyTest {
    @Test
    public void testBasic() {
        NPuzzleEasyAlgo main = new NPuzzleEasyAlgo();
        main.generateRandomGame(12, 2000); // 测试随机生成game
    }
    
    @Test
    public void testRawList2() {
        NPuzzleEasyAlgo main2 = new NPuzzleEasyAlgo();
        List<Integer> rawList = new ArrayList<>(9);
        rawList.add(5);
        rawList.add(3);
        rawList.add(0);
        rawList.add(6);
        rawList.add(7);
        rawList.add(1);
        rawList.add(8);
        rawList.add(2);
        rawList.add(4);
        main2.generateWithNsizeRawList(rawList);
        main2.solve();
    }
    
    @Test
    public void test4DRawUsingNoCache() {
        /*|  11 |  15 |   5 |  12 |
          |   1 |   7 |   4 |   6 |
          |  13 |   3 |   2 |  14 |
          |   9 |   8 |  10 |     |*/
        NPuzzleEasyAlgo main2 = new NPuzzleEasyAlgo();
        List<Integer> rawList = new ArrayList<>(16);
        rawList.add(11);
        rawList.add(15);
        rawList.add(5);
        rawList.add(12);
        rawList.add(1);
        rawList.add(7);
        rawList.add(4);
        rawList.add(6);
        rawList.add(13);
        rawList.add(3);
        rawList.add(2);
        rawList.add(14);
        rawList.add(9);
        rawList.add(8);
        rawList.add(10);
        rawList.add(0);
        main2.generateWithNsizeRawList(rawList);
        main2.solve();
    }
    
//    
//    @Test
//    public void testRandom5DNocache() {
//        NPuzzleAlgo main = new NPuzzleAlgo();
//        main.generateRandomGame(5, 40); // DANGER
//        main.solve();
//    }
}
