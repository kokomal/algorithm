/**  
 * @Title: BTreeNode.java   
 * @Package: yuanjun.chen.advanced.btree   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年11月6日 上午11:30:00   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.advanced.btree;

import java.util.ArrayList;
import java.util.List;
import yuanjun.chen.base.common.CommonUtils;

/**   
 * @ClassName: BTreeNode   
 * @Description: B树的节点及操作，粗略版本  
 * @author: 陈元俊 
 * @date: 2018年11月6日 上午11:30:00  
 */
public class BTreeNode {
    int degree; // 全局的度
    Long pageNo;

    Boolean isLoaded = false;
    
    public BTreeNode(int degree, Long pageNo, Boolean isLeaf, Integer n) {
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
    protected List<BTreeNode> children; // 子节点集合，维度为n+1，那么孩子数目介于t和2t之间，对于2-3-4树而言，就是2-3-4个孩子
    
    String getKeyAt(int i) { // 支持[1,n]的索引访问Key
        return keys.get(i - 1);
    }
    void setKeyAt(int i, String val) { // 支持[1,n]的索引访问Key
        if (keys.size() < i) {
            keys.add(val);
        }
        keys.set(i - 1, val);
    }
    BTreeNode getChildrenAt(int i) { // 支持[1,n+1]的索引访问Key
        return children.get(i - 1);
    }
    
    public BTreeResult search(String tbName, String k) {
        return search(tbName, this, k);
    }
    
    public static BTreeResult search(String tbName, BTreeNode x, String k) {
        if (!x.isLoaded) {
            // 说明读写没有成功
            return null;
        }
        int i = 1;
        while (i <= x.n && CommonUtils.more(k, x.getKeyAt(i))) {
            i = i + 1; // 如果找不到则i=n+1,最右侧，找到了则i<n+1且退出循环
        }
        if (i <= x.n && CommonUtils.eq(k, x.getKeyAt(i))) { // 找到了且位置合法，key相等，则返回
            return new BTreeResult(x, i);
        } else if (x.isLeaf) { // 没有找到，且已经是leaf
            return null;
        } else {
            // diskread x, ci
            BTreeNode selected = x.getChildrenAt(i);
            if (!selected.isLoaded) {
                selected = DiskUtil.diskRead(tbName, x.degree, selected.pageNo);
            }
            return search(tbName, selected, k);
        }
    }
    
    public void modifyChildAt(int i, final BTreeNode modi) {
        if (this.getChildren().size() < i) {
            addChild(modi);
            return;
        }
        BTreeNode selected = this.getChildrenAt(i);
        selected.pageNo = modi.pageNo;
        selected.children = modi.children;
        selected.isLeaf = modi.isLeaf;
        selected.keys = modi.keys;
        selected.isLoaded = true;
        selected.n = modi.n;
    }
    
    public void addChild(final BTreeNode modi) {
        BTreeNode selected = new BTreeNode(degree, modi.getPageNo(), modi.isLeaf, modi.n);
        selected.children = modi.children;
        selected.keys = modi.keys;
        selected.isLoaded = true;
        this.children.add(selected);
    }

    public Long getPageNo() {
        return pageNo;
    }

    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo;
    }

    public Boolean getIsLoaded() {
        return isLoaded;
    }

    public void setIsLoaded(Boolean isLoaded) {
        this.isLoaded = isLoaded;
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

    public List<BTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<BTreeNode> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "BTreeNode [degree=" + degree + ", pageNo=" + pageNo + ", isLoaded=" + isLoaded + ", isLeaf=" + isLeaf
                + ", n=" + n + ", keys=" + keys + ", children=" + children + "]";
    }
}
