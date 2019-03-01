/**
 * @Title: BTreeResult.java
 * @Package: yuanjun.chen.advanced.btree
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年11月6日 下午12:31:30
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.advanced.datastructure.btree;

/**
 * @ClassName: BTreeResultLite
 * @Description: 简化版的BTree
 * @author: 陈元俊
 * @date: 2018年11月6日 下午12:31:30
 */
public class BTreeResultLite {
    protected BTreeNodeLite node;
    protected int index;

    public BTreeResultLite(BTreeNodeLite node, int index) {
        this.node = node;
        this.index = index;
    }

    @Override
    public String toString() {
        return "BTreeResultLite [node=" + node + ", index=" + index + "]";
    }
}
