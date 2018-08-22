/**  
 * @Title: MyOSTree.java   
 * @Package: yuanjun.chen.base.container.RBTree   
 * @Description: 顺序统计树的实现（Dynamic order static tree,OSTree 
 * @author: 陈元俊     
 * @date: 2018年8月21日 下午3:58:12   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.base.container.RBTree;

import static yuanjun.chen.base.container.RBTree.RBTnode.COLOR.*;
import yuanjun.chen.base.common.CommonUtils;
import yuanjun.chen.base.container.MyQueue;

/**   
 * @ClassName: MyOSTree   
 * @Description: 顺序统计树的实现（Dynamic order static tree,OSTree   
 * @author: 陈元俊 
 * @date: 2018年8月21日 下午3:58:12  
 */
public class MyOSTree <T extends Comparable<T>> {
    private OSTnode<T> root;

    private final OSTnode<T> NIL; // 虚空指针

    public MyOSTree() {
        this.NIL = new OSTnode<>(null, BLACK);
        this.NIL.left = NIL;
        this.NIL.right = NIL;
        this.NIL.parent = NIL;
        this.NIL.size = 0;
        this.setRoot(NIL);// 所有null的都需要改为虚空指针NIL
    }
    
    public OSTnode<T> osSelect(int i) {
        return osSelect(this.root, i);
    }

    /**查找节点的排序*/
    public int osRank(OSTnode<T> x) {
        int r = left(x).size + 1;
        OSTnode<T> y = x;
        while (y != this.root) {
            if (y == right(parent(y))) {
                r = r + 1 + left(parent(y)).size;
            }
            y = parent(y);
        }
        return r;
    }
    
    /**
     * 《算法导论》14.1节 OS-SELECT(x,i)函数
     */
    private OSTnode<T> osSelect(OSTnode<T> x, int i) {
        int r = 1;
        if (x.left != NIL) {
            r += x.left.size;
        }
        if (i == r) {
            return x;
        } else if (i < r) {
            return osSelect(x.left, i);
        } else {
            return osSelect(x.right, i - r);
        }
    }

    public StringBuilder inorderTraverse(OSTnode<T> rt) {
        if (rt == NIL) {
            return new StringBuilder("NIL[BLACK]|0->");
        }
        StringBuilder res = inorderTraverse(rt.left);
        res = res.append(rt.val).append("[" + rt.color + "]").append("|" + rt.size).append("->");
        res.append(inorderTraverse(rt.right));
        return res;
    }

    public void levelTraverse(OSTnode<T> rt) throws Exception {
        int level = 0;
        if (rt == NIL) {
            System.out.print("#");
            return;
        }
        MyQueue<OSTnode<T>> curqueue = new MyQueue<>(128); // arrayqueue不可能无限大
        curqueue.enqueue(rt);
        while (!curqueue.isEmpty()) {
            level++;
            System.out.println("===========LEVEL" + level + "===========");
            MyQueue<OSTnode<T>> nextqueue = new MyQueue<>(128);
            while (!curqueue.isEmpty()) {
                OSTnode<T> node = curqueue.dequeue();
                System.out.println(node.val + "[" + node.color + "]|" + node.size + "-parent(" + (parent(node) == NIL ? "NIL" : parent(node).val) + ")");
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
    public void leftRotate(OSTnode<T> node) {
        OSTnode<T> cur = node;
        OSTnode<T> y = right(node);
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
        y.size = cur.size;
        updateSize(cur);// 旋转过程中需要更改size
    }
    
    /**
     * 右旋 
     *        [x]                 [y]          
     *       /   \               /   \
     *     [y]    γ    ==>      α    [x]    
     *    /   \                     /   \
     *   α     β                   β     γ         
     */
    public void rightRotate(OSTnode<T> node) {
        OSTnode<T> cur = node;
        OSTnode<T> y = left(node);
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
        cur.size = y.size;
        updateSize(y);// 旋转过程中需要更改size
    }

    /* NEED TRANSPLANT */

    /** 插入调整 ,缺size的改造*/
    public void rbInsertFixup(OSTnode<T> node) {
        OSTnode<T> cur = node;
        while (cur != NIL && cur != root && parent(cur).isRed()) {
            if (parent(cur) == left(parent(parent(cur)))) {
                OSTnode<T> y = right(parent(parent(cur))); // uncle
                if (y.isRed()) { // case 1 叔叔是红色，则父、叔、爷均反色，上台爷爷
                    parent(cur).color = BLACK;
                    y.color = BLACK;
                    parent(parent(cur)).color = RED;
                    cur = parent(parent(cur));
                } else if (cur == right(parent(cur))) { // case 2 叔黑，右子，自身左转
                    cur = parent(cur);
                    leftRotate(cur);
                    // case 3 叔黑，左子，父，爷反色，爷右转
                    parent(cur).color = BLACK;
                    parent(parent(cur)).color = RED;
                    rightRotate(parent(parent(cur)));
                }
            } else {
                OSTnode<T> y = left(parent(parent(cur))); // uncle
                if (y.isRed()) { // case 1
                    parent(cur).color = BLACK;
                    y.color = BLACK;
                    parent(parent(cur)).color = RED;
                    cur = parent(parent(cur));
                } else if (cur == left(parent(cur))) { // case 2
                    cur = parent(cur);
                    rightRotate(cur);
                    // case 3
                    parent(cur).color = BLACK;
                    parent(parent(cur)).color = RED;
                    leftRotate(parent(parent(cur)));
                }
            }
        }
        this.root.color = BLACK;
    }

    public OSTnode<T> treeDelete(OSTnode<T> node) {
        OSTnode<T> y = NIL; // y为待删除的节点

        if (left(node) == NIL || right(node) == NIL) {
            y = node; // 最多一个子女，那么y就是node本身
        } else {
            y = successor(node); // 子女健全，则y为后置
        }

        OSTnode<T> x = NIL; // x为待删除的节点的子女，因为删了之后要善后
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
    private void rbDeleteFixup(OSTnode<T> x) {
        while (x != this.root && x.color.equals(BLACK)) {
            if (x == left(parent(x))) {
                OSTnode<T> w = right(parent(x));
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
                OSTnode<T> w = left(parent(x));
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

    public OSTnode<T> treeInsert(T val) {
        OSTnode<T> ins = new OSTnode<>(val, BLACK);
        ins.left = NIL;
        ins.right = NIL;
        ins.parent = NIL;
        ins.size = 0;
        return treeInsert(ins);
    }

    /**
     * 红黑树插入
     */
    public OSTnode<T> treeInsert(OSTnode<T> node) {
        OSTnode<T> x = this.root;
        OSTnode<T> y = NIL;
        while (x != NIL) {
            y = x;
            if (CommonUtils.more(x.val, node.val)) {
                x.size++; // size的特殊处理
                x = left(x);
            } else {
                x.size++; // size的特殊处理
                x = right(x);
            }
        }
        node.parent = y;
        if (y == NIL) {
            this.root = node;
            updateSize(root);
            return root;
        } else if (CommonUtils.less(node.val, y.val)) {
            y.left = node;
        } else {
            y.right = node;
        }
        node.left = NIL;
        node.right = NIL;
        node.color = RED; // 新插入节点为红色
        rbInsertFixup(node); // 插入调整
        updateSize(node);//更新本节点的size
        return node;
    }

    // 更新节点的size
    private void updateSize(OSTnode<T> z) {
        z.size += 1;
        if (z.left != NIL) {
            z.size += z.left.size;
        }
        if (z.right != NIL) {
            z.size += z.right.size;
        }
    }
    
    /**
     * @Title: predecessor
     * @Description: 取前置
     * @param rt
     * @return: OSTnode
     */
    public OSTnode<T> predecessor(OSTnode<T> rt) {
        if (left(rt) != NIL) {
            return treeMaximum(left(rt));
        }
        OSTnode<T> cur = rt;
        OSTnode<T> parent = cur.parent;
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
     * @return: OSTnode
     */
    public OSTnode<T> successor(OSTnode<T> rt) {
        if (right(rt) != NIL) {
            return treeMinimum(right(rt));
        }
        OSTnode<T> cur = rt;
        OSTnode<T> parent = parent(cur);
        while (parent != NIL && cur == right(parent)) {
            cur = parent;
            parent = parent(parent);
        }
        return parent;
    }

    public OSTnode<T> iterativeSearch(OSTnode<T> rt, T target) {
        OSTnode<T> cur = rt;
        while (cur != NIL && !cur.val.equals(target)) {
            if (CommonUtils.less(cur.val, target)) {
                cur = left(cur);
            } else {
                cur = right(cur);
            }
        }
        return cur;
    }

    public OSTnode<T> recursiveSearch(OSTnode<T> rt, T target) {
        if (rt == NIL || rt.val.equals(target)) {
            return root;
        }
        if (CommonUtils.more(rt.val, target)) {
            return recursiveSearch(left(rt), target);
        } else {
            return recursiveSearch(right(rt), target);
        }
    }

    public OSTnode<T> treeMaximum(OSTnode<T> root) {
        OSTnode<T> cur = root;
        OSTnode<T> res = cur;
        while (cur != NIL) {
            res = cur;
            cur = right(cur);
        }
        return res;
    }

    public OSTnode<T> treeMaximumRoot() {
        return treeMaximum(this.root);
    }

    public OSTnode<T> treeMinimum(OSTnode<T> root) {
        OSTnode<T> cur = root;
        OSTnode<T> res = cur;
        while (cur != NIL) {
            res = cur;
            cur = left(cur);
        }
        return res;
    }

    public OSTnode<T> treeMinimumRoot() {
        return treeMinimum(this.root);
    }

    public OSTnode<T> left(OSTnode<T> node) {
        return node == NIL ? NIL : node.left;
    }

    public OSTnode<T> right(OSTnode<T> node) {
        return node == NIL ? NIL : node.right;
    }

    public OSTnode<T> parent(OSTnode<T> node) {
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
    public OSTnode<T> getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(OSTnode<T> root) {
        this.root = root;
    }
    
    public static void main(String[] args) throws Exception {
        MyOSTree<Integer> tree = new MyOSTree<>();
        tree.treeInsert(26);

        tree.treeInsert(17);
        tree.treeInsert(41);

        tree.treeInsert(14);
        tree.treeInsert(21);
        tree.treeInsert(30);
        tree.treeInsert(47);

        tree.treeInsert(10);
        tree.treeInsert(16);
        tree.treeInsert(19);
        tree.treeInsert(22);
        tree.treeInsert(28);
        tree.treeInsert(38);

        tree.treeInsert(7);
        tree.treeInsert(12);
        tree.treeInsert(15);

        OSTnode<Integer> node20 = tree.treeInsert(20);
        tree.treeInsert(35);
        tree.treeInsert(39);

        tree.treeInsert(3);
        tree.inorderDisplayFromRoot();
        tree.levelTraverse(tree.root);
        
        System.out.println("输出第1、10、20号元素");
        System.out.println(tree.osSelect(1));
        System.out.println(tree.osSelect(10));
        System.out.println(tree.osSelect(20));
        
        System.out.println("Rank for 20 is " + tree.osRank(node20));
    }
}
