/**
 * @Title: MathTool.java
 * @Package: yuanjun.chen.base.common
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年11月21日 下午5:18:12
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.common;

import java.util.Random;

/**
 * @ClassName: MathTool
 * @Description: 数学工具
 * @author: 陈元俊
 * @date: 2018年11月21日 下午5:18:12
 */
public class MathTool {

    public static double newtonInvSqrt(double c) {
        if (c < 0)
            return Double.NaN;
        double err = 1e-7; // 精度不要太高，否则较大数运算时间过长
        double t = c / 2; // t is a number we just guess
        while (Math.abs(c - t * t) > err) {
            t = (c / t + t) / 2.0;
        }
        // System.out.println("ERR=" + err);
        return t;
    }

    public static double fastInvSqrt(double x) {
        double xhalf = 0.5d * x;
        long i = Double.doubleToLongBits(x);
        i = 0x5fe6ec85e7de30daL - (i >> 1);
        x = Double.longBitsToDouble(i);
        x *= (1.5d - xhalf * x * x);
        return 1 / x;
    }

    public static float fastInvSqrt(float x) {
        float xhalf = 0.5f * x;
        int i = Float.floatToIntBits(x);
        i = 0x5f3759df - (i >> 1);
        x = Float.intBitsToFloat(i);
        x *= (1.5f - xhalf * x * x);
        x *= (1.5f - xhalf * x * x); // 牛顿逼近2次已经非常高精度了
        return 1 / x;
    }



    public static void main(String[] args) {
        // System.out.println(newtonInvSqrt(12100d));
        // System.out.println(fastInvSqrt(12100d));
        // System.out.println(fastInvSqrt(12100f));
        Random rd = new Random();
        for (int i = 0; i < 100; i++) {
            int k = rd.nextInt(10000);
            Double dd = rd.nextDouble();
            double x = dd * k;
            double res = newtonInvSqrt(x);
            System.out.println("CALC SQRT(" + x + ") RES = " + res);
            System.out.println("----------------------");
        }
    }
}
