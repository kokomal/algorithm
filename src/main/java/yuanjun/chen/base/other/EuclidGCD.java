/**
 * @Title: EuclidGCD.java
 * @Package: yuanjun.chen.base.other
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年10月16日 下午2:56:49
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.other;

/**
 * @ClassName: EuclidGCD
 * @Description: 欧几里得辗转相除求最大公约数
 * @author: 陈元俊
 * @date: 2018年10月16日 下午2:56:49
 */
public class EuclidGCD {
    /**
     * @Title: getGcd
     * @Description: 经典的欧氏辗转求最大公约数法，缺点是采用取余算法，对大数的性能比较糟糕
     * @param a
     * @param b
     * @return: Integer
     */
    public static Long getGcd(Long a, Long b) {
        if (a == 0) {
            return b;
        }
        if (b == 0) {
            return a;
        }
        Long big = Math.max(a, b);
        Long small = Math.min(a, b); // 不要耍小聪明用a+b-big,大数极有可能溢出
        do {
            Long res = big % small;
            if (res == 0) {
                return small;
            } else {
                big = small;
                small = res;
            }
        } while (true);
    }

    /**
     * @Title: steinGcd
     * @Description: Stein取余法，递归方式并且采用位移和减法，针对大数效率极高
     * @param a
     * @param b
     * @return: Integer
     */
    public static Long steinGcd(Long a, Long b) {
        if (a == 0) {
            return b;
        }
        if (b == 0) {
            return a;
        }
        if (a % 2 != 0) {
            if (b % 2 == 0) { // 仅b为偶数
                return steinGcd(a, b >> 1);
            } else { // 全是奇数，取差和最小值的gcd
                return steinGcd(Math.abs(a - b), Math.min(a, b));
            }
        } else if (b % 2 == 0) { // 2个都是偶数
            return 2 * steinGcd(a >> 1, b >> 1);
        } else { // 仅a为偶数
            return steinGcd(a >> 1, b);
        }
    }

    public static void main(String[] args) throws Exception {
        Long a = 3123120832L;
        Long b = 65536L;
        System.out.printf("GCD of %d & %d is %d\n", a, b, getGcd(a, b));
        System.out.printf("GCD of %d & %d is %d\n", a, b, steinGcd(a, b));
    }
}
