/**
 * @Title: BinarySearchTree.java
 * @Package: yuanjun.chen.base.container
 * @Description: 二叉搜索树
 * @author: 陈元俊
 * @date: 2018年8月17日 下午2:01:52
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.container;

import java.util.Random;
import yuanjun.chen.base.common.CommonUtils;

/**
 * @ClassName: BinarySearchTree
 * @Description: 二叉搜索树
 * @author: 陈元俊
 * @date: 2018年8月17日 下午2:01:52
 */
public class BinarySearchTree<T extends Comparable<?>> {
    private BSTnode<T> root;

    public BinarySearchTree(BSTnode<T> root) {
        this.setRoot(root);
    }

    public StringBuilder inorderTraverse(BSTnode<T> rt) {
        if (rt == null) {
            return new StringBuilder();
        }
        StringBuilder res = inorderTraverse(rt.left);
        res = res.append(rt.val).append("->");
        res.append(inorderTraverse(rt.right));
        return res;
    }

    public void levelTraverse(BSTnode<T> rt) throws Exception {
        if (rt == null) {
            System.out.print("#");
            return;
        }
        MyQueue<BSTnode<T>> curqueue = new MyQueue<>(128); // arrayqueue不可能无限大
        curqueue.enqueue(rt);
        while (!curqueue.isEmpty()) {
            System.out.println("=====================");
            MyQueue<BSTnode<T>> nextqueue = new MyQueue<>(128);
            while (!curqueue.isEmpty()) {
                BSTnode<T> node = curqueue.dequeue();
                System.out.println(node.val + "-parent(" + (parent(node) == null ? "null" : parent(node).val) + ")");
                if (left(node) != null) {
                    nextqueue.enqueue(left(node));
                }
                if (right(node) != null) {
                    nextqueue.enqueue(right(node));
                }
            }
            curqueue = nextqueue;
        }
    }

    /*NEED TRANSPLANT*/
    
    /**
     * BST的删除节点操作，略繁琐 考虑被删除的节点是否有子女节点， 如果最多1个，那么直接删除
     * 如果有2个，则最复杂，需要找到SUCCESSOR，将SUCCESSOR的数据拷贝覆盖过来后，删除SUCCESSOR 被删除的node返回的结果可能仍然保留着前置和后置的节点单向指针
     */
    public BSTnode<T> treeDelete(BSTnode<T> node) {
        if (node == null)
            return node;
        BSTnode<T> y = null; // y为待删除的节点
        if (left(node) == null || right(node) == null) {
            y = node; // 最多一个子女，那么y就是node本身
        } else {
            y = successor(node); // 子女健全，则y为后置
        }
        BSTnode<T> x = null; // x为待删除的节点的子女，因为删了之后要善后
        if (left(y) != null) {
            x = left(y);
        } else {
            x = right(y);
        }
        if (x != null) { // 子女不为空，则进行替换
            x.parent = parent(y);
        }
        if (parent(y) == null) { // y是root？
            this.root = x;
        } else if (y == left(parent(y))) { // 需要用==，因为是比较指针
            parent(y).left = x;// y是左孩子，那么y的父的左孩子变成x了
        } else {
            parent(y).right = x;// y是右孩子，那么y的父的右孩子变成x了
        }
        if (y != node) { // 对应子女健全，y为后置的情况
            node.val = y.val; // 把待删除的卫星数据全部拷贝过来
        }
        return y; // 返回删除了的node
    }

    public void treeInsert(T val) {
        BSTnode<T> ins = new BSTnode<>();
        ins.val = val;
        treeInsert(ins);
    }

    /**
     * 插入节点，先查找最佳插入点，然后进行插入，置node的parent和parent的left/right. 前提是其不能含有脏数据（例如残留的left，right）
     */
    public BSTnode<T> treeInsert(BSTnode<T> node) {
        if (node == null || node.val == null) {
            return node;
        }
        node.left = node.right = null;
        BSTnode<T> cur = this.root;
        BSTnode<T> record = null;
        while (cur != null) {
            record = cur;
            if (CommonUtils.more(cur.val, node.val)) {
                cur = left(cur);
            } else {
                cur = right(cur);
            }
        }
        node.parent = record;
        if (record == null) {
            this.root = node;
        } else if (CommonUtils.less(node.val, record.val)) {
            record.left = node;
        } else {
            record.right = node;
        }
        return node;
    }

    /**
     * @Title: predecessor
     * @Description: 取前置
     * @param rt
     * @return: BSTnode
     */
    public BSTnode<T> predecessor(BSTnode<T> rt) {
        if (left(rt) != null) {
            return treeMaximum(left(rt));
        }
        BSTnode<T> cur = rt;
        BSTnode<T> parent = cur.parent;
        while (parent != null && cur == left(parent)) {
            cur = parent;
            parent = parent.parent;
        }
        return parent;
    }

    /**
     * @Title: successor
     * @Description: 取后置
     * @param rt
     * @return: BSTnode
     */
    public BSTnode<T> successor(BSTnode<T> rt) {
        if (right(rt) != null) {
            return treeMinimum(right(rt));
        }
        BSTnode<T> cur = rt;
        BSTnode<T> parent = parent(cur);
        while (parent != null && cur == right(parent)) {
            cur = parent;
            parent = parent(parent);
        }
        return parent;
    }

    public BSTnode<T> iterativeSearch(BSTnode<T> rt, T target) {
        BSTnode<T> cur = rt;
        while (cur != null && !cur.val.equals(target)) {
            if (CommonUtils.less(cur.val, target)) {
                cur = left(cur);
            } else {
                cur = right(cur);
            }
        }
        return cur;
    }

    public BSTnode<T> recursiveSearch(BSTnode<T> rt, T target) {
        if (rt == null || rt.val.equals(target)) {
            return root;
        }
        if (CommonUtils.more(rt.val, target)) {
            return recursiveSearch(left(rt), target);
        } else {
            return recursiveSearch(right(rt), target);
        }
    }

    public BSTnode<T> treeMaximum(BSTnode<T> root) {
        BSTnode<T> cur = root;
        BSTnode<T> res = cur;
        while (cur != null) {
            res = cur;
            cur = right(cur);
        }
        return res;
    }

    public BSTnode<T> treeMaximumRoot() {
        return treeMaximum(this.root);
    }

    public BSTnode<T> treeMinimum(BSTnode<T> root) {
        BSTnode<T> cur = root;
        BSTnode<T> res = cur;
        while (cur != null) {
            res = cur;
            cur = left(cur);
        }
        return res;
    }

    public BSTnode<T> treeMinimumRoot() {
        return treeMinimum(this.root);
    }

    public BSTnode<T> left(BSTnode<T> node) {
        return node == null ? null : node.left;
    }

    public BSTnode<T> right(BSTnode<T> node) {
        return node == null ? null : node.right;
    }

    public BSTnode<T> parent(BSTnode<T> node) {
        return node == null ? null : node.parent;
    }

    /** 从root中序遍历 */
    public void inorderDisplayFromRoot() {
        StringBuilder sb = inorderTraverse(this.root);
        String res = sb.subSequence(0, sb.length() - 2).toString();
        System.out.println(res);
    }

    /**
     * @return the root
     */
    public BSTnode<T> getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(BSTnode<T> root) {
        this.root = root;
    }

    public static void main(String[] args) throws Exception {
        Random rd = new Random();
        int inserted = 339;
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(null);
        for (int i = 10; i < 18; i++) {
            tree.treeInsert(1 + rd.nextInt(800));
        }
        tree.inorderDisplayFromRoot();
        BSTnode<Integer> xx = new BSTnode<>();
        xx.val = inserted;
        tree.treeInsert(xx);
        System.out.println("after insert " + inserted);
        tree.inorderDisplayFromRoot();
        System.out.println("insert more");
        for (int i = 10; i < 18; i++) {
            tree.treeInsert(1 + rd.nextInt(800));
        }
        tree.inorderDisplayFromRoot();
        tree.levelTraverse(tree.root);

        BSTnode<Integer> br = tree.predecessor(xx);
        if (br != null) {
            System.out.println(xx.val + "前置为" + br.val);
        }
        BSTnode<Integer> br2 = tree.successor(xx);
        if (br2 != null) {
            System.out.println(xx.val + "后置为" + br2.val);
        }

        System.out.println("tree最小值" + tree.treeMinimumRoot().val);
        System.out.println("tree最大值" + tree.treeMaximumRoot().val);

        BSTnode<Integer> v = tree.treeDelete(xx); // 注意v已经从tree里面删除，但其指针仍然有效
        System.out.println("after delete " + v.val); // 此时只有val是准确的
        tree.inorderDisplayFromRoot();
    }
}
