/**
 * @Title: Coordinate.java
 * @Package: yuanjun.chen.game.ndigits
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年10月29日 上午8:53:13
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.game.nPuzzle;

/**
 * @ClassName: Coordinate
 * @Description: 坐标
 * @author: 陈元俊
 * @date: 2018年10月29日 上午8:53:13
 */
public class Coordinate {
    private int X;
    private int Y;

    public int distance(Coordinate co) {
        return Math.abs(co.X - X) + Math.abs(co.Y - Y);
    }
    
    public Coordinate(int x, int y) {
        super();
        X = x;
        Y = y;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    @Override
    public String toString() {
        return "Coordinate [X=" + X + ", Y=" + Y + "]";
    }
    
    public static int distance(final Coordinate c1, final Coordinate c2) {
        return Math.abs(c1.X - c2.X) + Math.abs(c1.Y - c2.Y);
    }
}
