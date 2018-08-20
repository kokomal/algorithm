/**
 * @Title: MyRBTree.java
 * @Package: yuanjun.chen.base.container
 * @Description: 红黑树
 * @author: 陈元俊
 * @date: 2018年8月17日 下午2:01:52
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.container;

import yuanjun.chen.base.common.CommonUtils;
import static yuanjun.chen.base.container.RBTnode.COLOR.*;
/**
 * @ClassName: MyRBTree
 * @Description: 红黑树
 * @author: 陈元俊
 * @date: 2018年8月17日 下午2:01:52
 */
public class MyRBTree<T extends Comparable<?>> {
    private RBTnode<T> root;

    private final RBTnode<T> NIL; // 虚空指针

    public MyRBTree() {
        this.NIL = new RBTnode<>(null, BLACK);
        this.NIL.left = NIL;
        this.NIL.right = NIL;
        this.NIL.parent = NIL;
        this.setRoot(NIL);// 所有null的都需要改为虚空指针NIL
    }

    public StringBuilder inorderTraverse(RBTnode<T> rt) {
        if (rt == NIL) {
            return new StringBuilder("NIL[BLACK]->");
        }
        StringBuilder res = inorderTraverse(rt.left);
        res = res.append(rt.val).append("[" + rt.color + "]").append("->");
        res.append(inorderTraverse(rt.right));
        return res;
    }

    public void levelTraverse(RBTnode<T> rt) throws Exception {
        int level = 0;
        if (rt == NIL) {
            System.out.print("#");
            return;
        }
        MyQueue<RBTnode<T>> curqueue = new MyQueue<>(128); // arrayqueue不可能无限大
        curqueue.enqueue(rt);
        while (!curqueue.isEmpty()) {
            level++;
            System.out.println("===========LEVEL" + level + "===========");
            MyQueue<RBTnode<T>> nextqueue = new MyQueue<>(128);
            while (!curqueue.isEmpty()) {
                RBTnode<T> node = curqueue.dequeue();
                System.out.println(node.val + "[" + node.color + "]" + "-parent(" + (parent(node) == NIL ? "NIL" : parent(node).val) + ")");
                if (left(node) != NIL) {
                    nextqueue.enqueue(left(node));
                }
                if (right(node) != NIL) {
                    nextqueue.enqueue(right(node));
                }
            }
            curqueue = nextqueue;
        }
        System.out.println("===========TRAVERSE ENDS===========");
    }
    
    /**
     * 左旋 
     *       [x]                   [y]              
     *      /   \                 /   \
     *     α    [y]    ==>      [x]    γ
     *         /   \            /  \
     *        β     γ          α    β
     */
    public void leftRotate(RBTnode<T> node) {
        RBTnode<T> cur = node;
        RBTnode<T> y = right(node);
        if (y == NIL) { // 左旋的话，右侧需要有意义
            return;
        }
        cur.right = y.left; // 把原来y的左子女β移交给x的右子女
        left(y).parent = cur; // β的父亲变成x
        y.parent = parent(cur); // y的父亲变成x的父亲
        if (parent(cur) == NIL) { // 如果影响到root，则变更
            this.root = y;
        } else if (cur == left(parent(cur))) { // x的父亲与y父亲的子女交接
            cur.parent.left = y;
        } else {
            cur.parent.right = y;
        }
        y.left = cur;
        cur.parent = y;
    }
    
    /**
     * 右旋 
     *        [x]                 [y]          
     *       /   \               /   \
     *     [y]    γ    ==>      α    [x]    
     *    /   \                     /   \
     *   α     β                   β     γ         
     */
    public void rightRotate(RBTnode<T> node) {
        RBTnode<T> cur = node;
        RBTnode<T> y = left(node);
        if (y == NIL) { // 右旋的话，左侧需要有意义
            return;
        }
        cur.left = y.right; // 把原来y的右子女β移交给x的左子女
        right(y).parent = cur; // β的父亲变成x
        y.parent = parent(cur); // y的父亲变成x的父亲
        if (parent(cur) == NIL) { // 如果影响到root，则变更
            this.root = y;
        } else if (cur == left(parent(cur))) { // x的父亲与y父亲的子女交接
            cur.parent.left = y;
        } else {
            cur.parent.right = y;
        }
        y.right = cur;
        cur.parent = y;
    }

    /* NEED TRANSPLANT */

    /** 插入调整 */
    public void rbInsertFixup(RBTnode<T> node) {
        RBTnode<T> cur = node;
        while (cur != NIL && cur != root && parent(cur).isRed()) {
            if (parent(cur) == left(parent(parent(cur)))) {
                RBTnode<T> y = right(parent(parent(cur))); // uncle
                if (y.isRed()) { // case 1 叔叔是红色，则父、叔、爷均反色，上台爷爷
                    parent(cur).color = BLACK;
                    y.color = BLACK;
                    parent(parent(cur)).color = RED;
                    cur = parent(parent(cur));
                } else {
                    if (cur == right(parent(cur))) { // case 2 叔黑，右子，自身左转
                        cur = parent(cur);
                        leftRotate(cur);
                    }
                    // case 3 叔黑，左子，父，爷反色，爷右转
                    parent(cur).color = BLACK;
                    parent(parent(cur)).color = RED;
                    rightRotate(parent(parent(cur)));
                }
            } else {
                RBTnode<T> y = left(parent(parent(cur))); // uncle
                if (y.isRed()) { // case 1
                    parent(cur).color = BLACK;
                    y.color = BLACK;
                    parent(parent(cur)).color = RED;
                    cur = parent(parent(cur));
                } else {
                    if (cur == left(parent(cur))) { // case 2
                        cur = parent(cur);
                        rightRotate(cur);
                    }
                    // case 3
                    parent(cur).color = BLACK;
                    parent(parent(cur)).color = RED;
                    leftRotate(parent(parent(cur)));

                }
            }
        }
        this.root.color = BLACK;
    }

    public RBTnode<T> treeDelete(RBTnode<T> node) {
        RBTnode<T> y = NIL; // y为待删除的节点

        if (left(node) == NIL || right(node) == NIL) {
            y = node; // 最多一个子女，那么y就是node本身
        } else {
            y = successor(node); // 子女健全，则y为后置
        }

        RBTnode<T> x = NIL; // x为待删除的节点的子女，因为删了之后要善后
        if (left(y) != NIL) {
            x = left(y);
        } else {
            x = right(y);
        }

        x.parent = parent(y); // RBtree特有，强制把parent塞入

        if (parent(y) == NIL) { // y是root？
            this.root = x;
        } else if (y == left(parent(y))) { // 需要用==，因为是比较指针
            parent(y).left = x;// y是左孩子，那么y的父的左孩子变成x了
        } else {
            parent(y).right = x;// y是右孩子，那么y的父的右孩子变成x了
        }

        if (y != node) { // 对应子女健全，y为后置的情况
            node.val = y.val; // 把待删除的卫星数据全部拷贝过来
            node.color = y.color;
        }

        if (y.color.equals(BLACK)) {
            rbDeleteFixup(x);
        }

        return y; // 返回删除了的node
    }

    /** 删除调整 */
    private void rbDeleteFixup(RBTnode<T> x) {
        while (x != this.root && x.color.equals(BLACK)) {
            if (x == left(parent(x))) {
                RBTnode<T> w = right(parent(x));
                if (w.isRed()) { // case 1
                    parent(x).color = RED;
                    leftRotate(parent(x));
                    w = right(parent(x));
                }
                if (left(w).isBlack() && right(w).isBlack()) { // case 2
                    w.color = RED;
                    x = parent(x);
                } else {
                    if (right(w).isBlack()) { // case 3
                        left(w).color = BLACK;
                        w.color = RED;
                        rightRotate(w);
                        w = right(parent(x));
                    }
                    w.color = parent(x).color; // case 4
                    parent(x).color = BLACK;
                    right(w).color = BLACK;
                    leftRotate(parent(x));
                    x = this.root;
                }
            } else {
                RBTnode<T> w = left(parent(x));
                if (w.color.equals(RED)) { // case 1
                    parent(x).color = RED;
                    rightRotate(parent(x));
                    w = left(parent(x));
                }
                if (right(w).isBlack() && left(w).isBlack()) { // case 2
                    w.color = RED;
                    x = parent(x);
                } else {
                    if (left(w).isBlack()) { // case 3
                        right(w).color = BLACK;
                        w.color = RED;
                        leftRotate(w);
                        w = left(parent(x));
                    }
                    w.color = parent(x).color; // case 4
                    parent(x).color = BLACK;
                    left(w).color = BLACK;
                    rightRotate(parent(x));
                    x = this.root;
                }
            }
        }
        x.color = BLACK;
    }

    public RBTnode<T> treeInsert(T val) {
        RBTnode<T> ins = new RBTnode<>(val, RED);
        ins.left = NIL;
        ins.right = NIL;
        ins.parent = NIL;
        return treeInsert(ins);
    }

    /**
     * 红黑树插入
     */
    public RBTnode<T> treeInsert(RBTnode<T> node) {
        RBTnode<T> x = this.root;
        RBTnode<T> y = NIL;
        while (x != NIL) {
            y = x;
            if (CommonUtils.more(x.val, node.val)) {
                x = left(x);
            } else {
                x = right(x);
            }
        }
        node.parent = y;
        if (y == NIL) {
            this.root = node;
        } else if (CommonUtils.less(node.val, y.val)) {
            y.left = node;
        } else {
            y.right = node;
        }
        node.left = NIL;
        node.right = NIL;
        node.color = RED; // 新插入节点为红色
        rbInsertFixup(node); // 插入调整
        return node;
    }

    /**
     * @Title: predecessor
     * @Description: 取前置
     * @param rt
     * @return: RBTnode
     */
    public RBTnode<T> predecessor(RBTnode<T> rt) {
        if (left(rt) != NIL) {
            return treeMaximum(left(rt));
        }
        RBTnode<T> cur = rt;
        RBTnode<T> parent = cur.parent;
        while (parent != NIL && cur == left(parent)) {
            cur = parent;
            parent = parent.parent;
        }
        return parent;
    }

    /**
     * @Title: successor
     * @Description: 取后置
     * @param rt
     * @return: RBTnode
     */
    public RBTnode<T> successor(RBTnode<T> rt) {
        if (right(rt) != NIL) {
            return treeMinimum(right(rt));
        }
        RBTnode<T> cur = rt;
        RBTnode<T> parent = parent(cur);
        while (parent != NIL && cur == right(parent)) {
            cur = parent;
            parent = parent(parent);
        }
        return parent;
    }

    public RBTnode<T> iterativeSearch(RBTnode<T> rt, T target) {
        RBTnode<T> cur = rt;
        while (cur != NIL && !cur.val.equals(target)) {
            if (CommonUtils.less(cur.val, target)) {
                cur = left(cur);
            } else {
                cur = right(cur);
            }
        }
        return cur;
    }

    public RBTnode<T> recursiveSearch(RBTnode<T> rt, T target) {
        if (rt == NIL || rt.val.equals(target)) {
            return root;
        }
        if (CommonUtils.more(rt.val, target)) {
            return recursiveSearch(left(rt), target);
        } else {
            return recursiveSearch(right(rt), target);
        }
    }

    public RBTnode<T> treeMaximum(RBTnode<T> root) {
        RBTnode<T> cur = root;
        RBTnode<T> res = cur;
        while (cur != NIL) {
            res = cur;
            cur = right(cur);
        }
        return res;
    }

    public RBTnode<T> treeMaximumRoot() {
        return treeMaximum(this.root);
    }

    public RBTnode<T> treeMinimum(RBTnode<T> root) {
        RBTnode<T> cur = root;
        RBTnode<T> res = cur;
        while (cur != NIL) {
            res = cur;
            cur = left(cur);
        }
        return res;
    }

    public RBTnode<T> treeMinimumRoot() {
        return treeMinimum(this.root);
    }

    public RBTnode<T> left(RBTnode<T> node) {
        return node == NIL ? NIL : node.left;
    }

    public RBTnode<T> right(RBTnode<T> node) {
        return node == NIL ? NIL : node.right;
    }

    public RBTnode<T> parent(RBTnode<T> node) {
        return node == NIL ? NIL : node.parent;
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
    public RBTnode<T> getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(RBTnode<T> root) {
        this.root = root;
    }

    public static void main(String[] args) throws Exception {
        MyRBTree<Integer> tree = new MyRBTree<>();
        tree.treeInsert(11);
        RBTnode<Integer> node3 = tree.treeInsert(2);
        tree.treeInsert(14);
        tree.treeInsert(1);
        tree.treeInsert(7);
        tree.treeInsert(15);
        tree.treeInsert(5);
        tree.treeInsert(8);
        System.out.println("PREPARE TO INORDER-TRAVERSE");
        tree.inorderDisplayFromRoot();
        tree.levelTraverse(tree.root);
        tree.treeInsert(4);
        System.out.println("PREPARE TO INORDER-TRAVERSE");
        tree.inorderDisplayFromRoot();
        tree.levelTraverse(tree.root);

        tree.treeDelete(node3);
        System.out.println("PREPARE TO INORDER-TRAVERSE");
        tree.inorderDisplayFromRoot();
        tree.levelTraverse(tree.root);
    }
}
