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

    public static  boolean eq(Comparable A, Comparable B) {
        return A.compareTo(B) == 0;
    }

    public static  boolean lesseq(Comparable A, Comparable B) {
        return A.compareTo(B) <= 0;
    }

    public static  boolean more(Comparable A, Comparable B) {
        return A.compareTo(B) > 0;
    }

    public static  boolean moreeq(Comparable A, Comparable B) {
        return A.compareTo(B) >= 0;
    }
}
