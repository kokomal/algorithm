/**
 * @Title: BTreeNode.java
 * @Package: yuanjun.chen.advanced.btree
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年11月6日 上午11:30:00
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.advanced.btreelite;

import java.util.List;

/**
 * @ClassName: BTreeNode
 * @Description: B树的节点及操作,没有递归自引用,所有算法逻辑全部上抬至Holder
 * @author: 陈元俊
 * @date: 2018年11月6日 上午11:30:00
 */
public class BTreeNodeLite {
    int degree; // 全局的度
    Long pageNo;

    public BTreeNodeLite(int degree, Long pageNo, Boolean isLeaf, Integer n) {
        this.degree = degree;
        this.pageNo = pageNo;
        this.isLeaf = isLeaf;
        this.n = n;
    }

    public boolean isFull() {
        return this.degree * 2 - 1 == this.n;
    }

    protected Boolean isLeaf = true; // 默认是叶子节点
    protected Integer n; // 当前关键字个数，度为t的话，那么n介于t-1和2t-1之间
    protected List<String> keys; // Key个数为n∈[t-1,2t-1],对于2-3-4树而言，就是1-2-3个元素
    protected List<Long> children; // 子节点集合，维度为n+1，那么孩子数目介于t和2t之间，对于2-3-4树而言，就是2-3-4个孩子

    String getKeyAt(int i) { // 支持[1,n]的索引访问Key
        return keys.get(i - 1);
    }

    void setKeyAt(int i, String val) { // 支持[1,n]的索引访问Key
        if (keys.size() < i) {
            keys.add(val);
        }
        keys.set(i - 1, val);
    }

    Long getChildrenAt(int i) { // 支持[1,n+1]的索引访问Key
        return this.children.get(i - 1);
    }

    public void modifyChildAt(int i, final Long modi) {
        if (this.getChildren().size() < i) {
            addChild(modi);
            return;
        }
        this.children.set(i - 1, modi);
    }

    public void addChild(final Long modi) {
        this.children.add(modi);
    }

    public Long getPageNo() {
        return pageNo;
    }

    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo;
    }

    public Boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public List<Long> getChildren() {
        return children;
    }

    public void setChildren(List<Long> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "BTreeNode [degree=" + degree + ", pageNo=" + pageNo + ", isLeaf=" + isLeaf
                + ", n=" + n + ", keys=" + keys + ", children=" + children + "]";
    }
}
