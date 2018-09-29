/**
 * @Title: BaseBallPlayer.java
 * @Package: yuanjun.chen.base.dynamicprogramming.baseball
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年9月28日 上午10:50:50
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.dynamicprogramming.baseball;

/**
 * @ClassName: BaseBallPlayer
 * @Description: 球员
 * @author: 陈元俊
 * @date: 2018年9月28日 上午10:50:50
 */
public class BaseBallPlayer {
    private String name;
    private BaseBallPos pos; // 打球的位置（职业）
    private int signCost; // 签约
    private int vorp; // Value over replacement player球员替换价值

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BaseBallPos getPos() {
        return pos;
    }

    public void setPos(BaseBallPos pos) {
        this.pos = pos;
    }

    public int getSignCost() {
        return signCost;
    }

    public void setSignCost(int signCost) {
        this.signCost = signCost;
    }

    public int getVorp() {
        return vorp;
    }

    public void setVorp(int vorp) {
        this.vorp = vorp;
    }

    @Override
    public String toString() {
        return "球员资料{姓名:" + name + /*", 位置:" + pos +*/ ", 签约费:" + signCost + ", Vorp:" + vorp + "}";
    }

}
