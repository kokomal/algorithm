/**
 * @Title: RBTnode.java
 * @Package: yuanjun.chen.base.container
 * @Description: 红黑树节点
 * @author: 陈元俊
 * @date: 2018年8月20日 上午8:58:18
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.container;

/**
 * @ClassName: RBTnode
 * @Description: 红黑树节点
 * @author: 陈元俊
 * @date: 2018年8月20日 上午8:58:18
 */
public class RBTnode <T extends Comparable<?>> {
    public static enum COLOR {
        RED, BLACK;
    }
    protected T val;
    protected RBTnode<T> parent;
    protected RBTnode<T> left;
    protected RBTnode<T> right;
    protected COLOR color = COLOR.BLACK;

    public RBTnode(T val, COLOR color) {
        super();
        this.val = val;
        this.color = color;
    }
    
    public boolean isRed() {
        return this.color.equals(COLOR.RED);
    }
    
    public boolean isBlack() {
        return this.color.equals(COLOR.BLACK);
    }
}
