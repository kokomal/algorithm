/**
 * @Title: OSTnode.java
 * @Package: yuanjun.chen.base.container
 * @Description: 红黑树节点
 * @author: 陈元俊
 * @date: 2018年8月20日 上午8:58:18
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.container.RBTree;

/**
 * @ClassName: OSTnode
 * @Description: 红黑树节点，添加size
 * @author: 陈元俊
 * @date: 2018年8月20日 上午8:58:18
 */
public class OSTnode<T extends Comparable<T>> extends RBTnode<T> {
    protected int size = 0;
    protected OSTnode<T> parent;
    protected OSTnode<T> left;
    protected OSTnode<T> right;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public OSTnode(T val, COLOR color) {
        super(val, color);
    }

    @Override
    public String toString() {
        return "OSTnode [size=" + size + ", val=" + val + "]";
    }
}
