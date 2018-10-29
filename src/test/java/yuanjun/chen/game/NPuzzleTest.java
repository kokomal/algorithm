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

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import yuanjun.chen.game.nPuzzle.NPuzzleAlgo;

/**   
 * @ClassName: NDigitsTest   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 陈元俊 
 * @date: 2018年10月29日 上午9:09:36  
 */
public class NPuzzleTest {
    @Test
    public void testBasic() {
        NPuzzleAlgo main = new NPuzzleAlgo();
        main.generateRandomGame(12); // 测试随机生成game
    }
    
    @Test
    public void testRawList() {
        NPuzzleAlgo main2 = new NPuzzleAlgo(); // 测试带参数生成game
        List<Integer> rawList = new ArrayList<>();
        rawList.add(1);
        rawList.add(2);
        rawList.add(3);
        rawList.add(4);
        rawList.add(5);
        rawList.add(6);
        rawList.add(7);
        rawList.add(8);
        rawList.add(9);
        rawList.add(10);
        rawList.add(11);
        rawList.add(12);
        rawList.add(13);
        rawList.add(14);
        rawList.add(15);
        rawList.add(0);
        main2.generateWithNsizeRawList(rawList);
        main2.solve(); // 测试曼哈顿距离
    }
    
    @Test
    public void testRawList2() {
        NPuzzleAlgo main2 = new NPuzzleAlgo(); // 测试带参数生成game
        List<Integer> rawList = new ArrayList<>();
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
    public void testRawList3() {
        NPuzzleAlgo main2 = new NPuzzleAlgo(); // 测试带参数生成game
        List<Integer> rawList = new ArrayList<>();
        rawList.add(6);
        rawList.add(7);
        rawList.add(1);
        rawList.add(8);
        rawList.add(2);
        rawList.add(4);
        rawList.add(0);
        rawList.add(5);
        rawList.add(3);
        main2.generateWithNsizeRawList(rawList);
        main2.solve();
    }
}
