/**
 * @Title: LCSTest.java
 * @Package: yuanjun.chen.base.dynamicprogramming
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年8月29日 下午4:48:12
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.dynamicprogramming;

import java.util.List;
import org.junit.Test;
import yuanjun.chen.base.common.RandomGenner;

/**
 * @ClassName: LCSTest
 * @Description: LCS-dp算法的测试
 * @author: 陈元俊
 * @date: 2018年8月29日 下午4:48:12
 */
public class LCSTest {

    /**
     * @Title: test001
     * @Description: 测试递归表达式的打印，缺点在于空字符串的处理困难
     */
    @Test
    public void test001() {
        String X = RandomGenner.generateDNASeries(18);
        String Y = RandomGenner.generateDNASeries(17);
        System.out.println("X = " + X);
        System.out.println("Y = " + Y);
        LCSAlgo.lcs_legth(X, Y);
        LCSAlgo.showBTable(X, Y);
        LCSAlgo.print_lcs(X, X.length(), Y.length()); // 表达式的方式
        System.out.println();
    }

    /**
     * @Title: test002
     * @Description: 测试带去重功能的LCS，和不带去重功能LCS结果的比较
     */
    @Test
    public void test002() {
        String X = RandomGenner.generateDNASeries(30);
        String Y = RandomGenner.generateDNASeries(40);
        System.out.println("X = " + X);
        System.out.println("Y = " + Y);
        LCSAlgo.lcs_legth(X, Y);
        LCSAlgo.showBTable(X, Y);
        List<String> allres = LCSAlgo.print_lcs_list_unique(X, X.length(), Y.length());
        System.out.println("----去重之后的结果----[" + allres.size() + "] candidate sequences");
        System.out.println(allres);

        List<String> allres2 = LCSAlgo.print_lcs_list(X, X.length(), Y.length());
        System.out.println("----没有去重的结果----[" + allres2.size() + "] candidate sequences");
        System.out.println(allres2);

    }
}
