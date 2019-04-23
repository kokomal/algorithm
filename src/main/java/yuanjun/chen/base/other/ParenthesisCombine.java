/**
 * @Title: ParenthesisCombine.java
 * @Package: yuanjun.chen.base.other
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2019年4月23日 下午4:13:53
 * @version V1.0
 * @Copyright: 2019 All rights reserved.
 */
package yuanjun.chen.base.other;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ParenthesisCombine
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: 陈元俊
 * @date: 2019年4月23日 下午4:13:53
 */
public class ParenthesisCombine {
    public static List<String> generateParenthesis(int n) {
        List<String> list = new ArrayList<>();
        doadd(n, n, list, "");
        return list;
    }

    public static void doadd(int left, int right, List<String> list, String path) {
        System.out.println("Left " + left + " Right " + right + " Path " + path);
        if (left == 0 && right == 0) {
            list.add(path);
            return;
        }
        if (left != 0) {
            doadd(left - 1, right, list, path + "(");
        }
        if (right != 0 && right > left) {
            doadd(left, right - 1, list, path + ")");
        }
    }

    public static void main(String[] args) {
        System.out.println(generateParenthesis(3));
    }
}
