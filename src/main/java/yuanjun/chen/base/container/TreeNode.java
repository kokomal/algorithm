/**
 * @Title: TreeNode.java
 * @Package: yuanjun.chen.base.container
 * @Description: 二叉树的简单Node
 * @author: 陈元俊
 * @date: 2018年8月2日 下午6:09:15
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.container;

/**
 * @ClassName: TreeNode
 * @Description: 二叉树的简单Node
 * @author: 陈元俊
 * @date: 2018年8月2日 下午6:09:15
 */
public class TreeNode<T extends Object> {
    protected TreeNode<T> left;
    protected TreeNode<T> right;
    protected T val;

    public TreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(TreeNode<T> left) {
        this.left = left;
    }

    public TreeNode<T> getRight() {
        return right;
    }

    public void setRight(TreeNode<T> right) {
        this.right = right;
    }

}
