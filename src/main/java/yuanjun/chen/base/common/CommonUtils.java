/**
 * @Title: CommonUtils.java
 * @Package: yuanjun.chen.base.common
 * @Description: 通用util
 * @author: 陈元俊
 * @date: 2018年7月24日 下午5:51:31
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.common;

import java.lang.reflect.Array;

/**
 * @ClassName: CommonUtils
 * @Description: 通用util
 * @author: 陈元俊
 * @date: 2018年7月24日 下午5:51:31
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class CommonUtils {
    public static boolean less(Comparable A, Comparable B) {
        return A.compareTo(B) < 0;
    }

    public static boolean eq(Comparable A, Comparable B) {
        return A.compareTo(B) == 0;
    }

    public static boolean lesseq(Comparable A, Comparable B) {
        return A.compareTo(B) <= 0;
    }

    public static boolean more(Comparable A, Comparable B) {
        return A.compareTo(B) > 0;
    }

    public static boolean moreeq(Comparable A, Comparable B) {
        return A.compareTo(B) >= 0;
    }

    /**
     * @Title: reverseMatrix
     * @Description: 翻转泛型矩阵，相对于f(A)=A^T,不改变原矩阵
     * @param old
     * @return: T[][]
     */
    public static <T> T[][] reverseMatrix(T[][] old) {
        int m = old.length;
        int n = old[0].length;
        T[][] res = (T[][]) Array.newInstance(old[0][0].getClass(), n, m); // 采用reflect机制获得T的类型
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res[j][i] = old[i][j];
            }
        }
        return res;
    }
}
