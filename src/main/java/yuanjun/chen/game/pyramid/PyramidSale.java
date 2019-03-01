/**
 * @Title: PyramidSale.java
 * @Package: yuanjun.chen.game.pyramid
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2019年3月1日 上午9:44:39
 * @version V1.0
 * @Copyright: 2019 All rights reserved.
 */
package yuanjun.chen.game.pyramid;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @ClassName: PyramidSale
 * @Description: 简单的展现一下传销的恐怖数学模型
 * @author: 陈元俊
 * @date: 2019年3月1日 上午9:44:39
 */
public class PyramidSale {
    private static double RATIO = 0.25; // 每一级要上交25%的利润给上级，留75%给自己
    private static int MAXLEVEL = 6; // 最大level,国家-省-市-区-镇-村
    private static double AMWAY = 1000.00; // 货物单价
    private static double REVENUE = 0.75; // 货物毛利率，即100元的东西，成本25元，以此类推
    private static int GANGMEMBERS = 25; // 每一level发展25个下线

    private static DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();

    public static void main(String[] args) {
        sale(MAXLEVEL);
    }

    // 输入level，0为最底层肉鸡
    // 输出贡献给上级的收益
    private static double sale(int level) {
        if (level == 0) {
            return AMWAY * REVENUE; // 最底层传销成员(肉鸡)，买1000元的东西，贡献750元给卖家
        }
        double totalRev = GANGMEMBERS * sale(level - 1); // 汇总所有下一级帮会成员的总收益
        if (level == MAXLEVEL) {
            System.out.println("Level-" + MAXLEVEL + "总头目获得利润" + df.format(totalRev) + "元");
            return totalRev;
        } else {
            System.out.println("Level-" + level + "头目获得自己的利润" + df.format(totalRev * (1 - RATIO)) + "元, 上交利润"
                    + df.format(totalRev * RATIO) + "元");
            return totalRev * RATIO;
        }
    }

}
