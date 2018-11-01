/**
 * @Title: PrintNeatTest.java
 * @Package: yuanjun.chen.base.dynamicprogramming
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年9月13日 下午4:30:03
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.dynamicprogramming;

import java.util.Arrays;
import org.junit.Test;
import yuanjun.chen.base.common.RandomGenner;

/**
 * @ClassName: PrintNeatTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: 陈元俊
 * @date: 2018年9月13日 下午4:30:03
 */
public class PrintNeatTest {
    @Test
    public void testNeatEasy() {
        String[] nations = new String[] {"china", "philipines", "usa", "india", "laos", "mongolia", "uk", "thailand"};
        PrintNeatlyAlgo.init(" ", 12, nations);
        int res = PrintNeatlyAlgo.neatly();
        System.out.println("MinQ = " + res);
        System.out.println("---The chapter is shown below---");
        PrintNeatlyAlgo.printTheChapter();
    }

    @Test
    public void testNeatHard() {
        int legalLen = 144; // 每行最多144格
        int maxWordLen = 45;
        String[] nations = RandomGenner.generateRandomStrings(100, maxWordLen); // 初始化100个长度为[1-45]的字符串
        System.out.println("raw words = " + Arrays.toString(nations));
        PrintNeatlyAlgo.init("▮", legalLen, nations);
        int res = PrintNeatlyAlgo.neatly();
        System.out.println("MinQ = " + res);
        StringBuilder splitter = new StringBuilder();
        for (int i = 0; i < legalLen; i++) {
            splitter.append("=");
        }
        System.out.println(splitter);// 标杆144行
        PrintNeatlyAlgo.printTheChapter();
        System.out.println(splitter);// 标杆144行
    }

    @Test
    public void testNeatHardTibetan() {
        int legalLen = 144; // 每行最多144格
        int maxWordLen = 80;
        String[] psalms = RandomGenner.generateRandomTibetan(100, maxWordLen); // 初始化100个长度为[1-80]的字符串
        System.out.println("raw words = \n" + Arrays.toString(psalms));
        PrintNeatlyAlgo.init(",", legalLen, psalms);
        int res = PrintNeatlyAlgo.neatly();
        System.out.println("MinQ = " + res);
        StringBuilder splitter = new StringBuilder();
        for (int i = 0; i < legalLen; i++) {
            splitter.append("=");
        }
        System.out.println(splitter);// 标杆144行
        PrintNeatlyAlgo.printTheChapter();
        System.out.println(splitter);// 标杆144行
    }
}
