/**
 * @Title: MoveDir.java
 * @Package: yuanjun.chen.game.ndigits
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年10月29日 上午9:22:58
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.game.nPuzzle;

/**
 * @ClassName: MoveDir
 * @Description: 移动方向，以空格的视角
 * @author: 陈元俊
 * @date: 2018年10月29日 上午9:22:58
 */
public enum MoveDir {
    UP, DOWN, LEFT, RIGHT, INIT;
    MoveDir reverse() {
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                return INIT;
        }
    }
    public static MoveDir[] legalMoves() {
        return new MoveDir[] {
              UP, DOWN, LEFT, RIGHT  
        };
    }
}
