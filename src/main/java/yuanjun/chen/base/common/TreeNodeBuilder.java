package yuanjun.chen.base.common;

import java.util.ArrayList;
import java.util.List;
import yuanjun.chen.base.container.MyQueue;
import yuanjun.chen.base.container.MyStack;
import yuanjun.chen.base.container.TreeNode;

/**
 * @ClassName: TreeNodeBuilder
 * @Description: 二叉树相关生成和遍历
 * @author: 陈元俊
 * @date: 2018年7月24日 下午12:24:24
 */
public class TreeNodeBuilder {
    public static <T extends Object> TreeNode<T> buildABinaryTree(T[] vals) {
        int len = vals.length;
        if (len < 1) {
            return null;
        }
        TreeNode<T> root = new TreeNode<>();
        root.setVal(vals[0]);
        List<TreeNode<T>> thisLoop = new ArrayList<>();
        thisLoop.add(root);
        int i = 0;
        do {
            List<TreeNode<T>> nextLoop = new ArrayList<>();
            for (TreeNode<T> t : thisLoop) {
                i++;
                if (i < len) {
                    TreeNode<T> tt = new TreeNode<>();
                    tt.setVal(vals[i]);
                    t.setLeft(tt);
                    nextLoop.add(tt);
                } else {
                    return root;
                }
                i++;
                if (i < len) {
                    TreeNode<T> tt = new TreeNode<>();
                    tt.setVal(vals[i]);
                    t.setRight(tt);
                    nextLoop.add(tt);
                } else {
                    return root;
                }
                thisLoop = nextLoop;
            }
        } while (true);
    }

    /** 前序遍历[递归]. */
    public static <T extends Object> void DLRtraverseRecursive(TreeNode<T> t) {
        if (t == null) {
            return;
        }
        System.out.println("node--" + t.getVal());
        DLRtraverseRecursive(t.getLeft());
        DLRtraverseRecursive(t.getRight());
    }

    /**
     * 前序遍历[非递归].
     * 
     * @throws Exception
     */
    public static <T extends Object> void DLRtraverse(TreeNode<T> t) throws Exception {
        if (t == null) {
            return;
        }
        MyStack<TreeNode<T>> stack = new MyStack<>();
        stack.push(t);
        while (!stack.isEmpty()) {
            TreeNode<T> tr = stack.pop();
            System.out.println("node--" + tr.getVal());
            if (tr.getRight() != null) {
                stack.push(tr.getRight());
            }
            if (tr.getLeft() != null) {
                stack.push(tr.getLeft());
            }
        }
    }

    /** 中序遍历[递归]. */
    public static <T extends Object> void LDRtraverseRecursive(TreeNode<T> t) {
        if (t == null) {
            return;
        }
        LDRtraverseRecursive(t.getLeft());
        System.out.println("node--" + t.getVal());
        LDRtraverseRecursive(t.getRight());
    }

    /**
     * 中序遍历[非递归] 先找到极左，然后逐步遍历栈顶，找到每一个栈顶的右侧，入栈.
     * 
     * @throws Exception
     */
    public static <T extends Object> void LDRtraverse(TreeNode<T> t) throws Exception {
        if (t == null) {
            return;
        }
        MyStack<TreeNode<T>> stack = new MyStack<>();
        stack.push(t);
        while (!stack.isEmpty()) {
            while (stack.peek().getLeft() != null) {
                stack.push(stack.peek().getLeft());
            }
            while (!stack.isEmpty()) {
                TreeNode<T> tr = stack.peek();
                System.out.println("node---" + tr.getVal());
                stack.pop();
                if (tr.getRight() != null) {
                    stack.push(tr.getRight());
                    break;
                }
            }
        }
    }

    /** 后序遍历[递归]. */
    public static <T extends Object> void LRDtraverseRecursive(TreeNode<T> t) {
        if (t == null) {
            return;
        }
        LRDtraverseRecursive(t.getLeft());
        LRDtraverseRecursive(t.getRight());
        System.out.println("node--" + t.getVal());
    }

    /**
     * 后序遍历[非递归].
     * 
     * @throws Exception
     */
    public static <T extends Object> void LRDtraverse(TreeNode<T> t) throws Exception {
        if (t == null) {
            return;
        }
        MyStack<TreeNode<T>> stack = new MyStack<>();
        TreeNode<T> lastPop = null;
        stack.push(t);
        while (!stack.isEmpty()) {
            while (stack.peek().getLeft() != null) {
                stack.push(stack.peek().getLeft());
            }
            while (!stack.isEmpty()) {
                // 右侧为空，或者上次就是pop的右侧，则root可以出来了
                if (stack.peek().getRight() == null || lastPop == stack.peek().getRight()) {
                    System.out.println("node---" + stack.peek().getVal());
                    lastPop = stack.pop();
                } else { // 否则右侧进栈
                    stack.push(stack.peek().getRight());
                    break;
                }
            }
        }
    }

    /**
     * 层次遍历,注意层次遍历无递归方法.
     * 
     * @throws Exception
     */
    public static <T extends Object> void leveltraverse(TreeNode<T> t) throws Exception {
        if (t == null) {
            return;
        }
        MyQueue<TreeNode<T>> queue = new MyQueue<>();
        queue.enqueue(t);
        int level = 1;
        while (!queue.isEmpty()) {
            MyQueue<TreeNode<T>> nextQueue = new MyQueue<>();
            int len = queue.size();
            System.out.println("---tree level " + (level++) + " with " + len + " elements---");
            for (int i = 0; i < len; i++) {
                TreeNode<T> tr = queue.dequeue();
                System.out.print("node--" + tr.getVal() + " ");
                if (tr.getLeft() != null) {
                    nextQueue.enqueue(tr.getLeft());
                }
                if (tr.getRight() != null) {
                    nextQueue.enqueue(tr.getRight());
                }
            }
            queue = null;
            queue = nextQueue;
            System.out.println();
        }
    }
    
    public static <T extends Object> void mirror(TreeNode<T> t) {
        if (t == null) {
            return;
        }
        TreeNode<T> tmp = t.getLeft();
        t.setLeft(t.getRight());
        t.setRight(tmp);
        mirror(t.getLeft());
        mirror(t.getRight());
    }
    
    public static <T extends Object> int depth(TreeNode<T> t) {
        if (t == null) return 0;
        return 1 + Math.max(depth(t.getLeft()), depth(t.getRight()));
    }

    public static void main(String[] args) throws Exception {
        Integer[] tt = new Integer[] {12, 40, 38, 65, 1124, 73, 69};
        TreeNode<Integer> t = buildABinaryTree(tt);
        System.out.println("------------begin DLRtraverseRecursive------------");
        DLRtraverseRecursive(t);
        System.out.println("------------begin DLRtraverse------------");
        DLRtraverse(t);
        System.out.println("------------begin LDRtraverseRecursive------------");
        LDRtraverseRecursive(t);
        System.out.println("------------begin LDRtraverse------------");
        LDRtraverse(t);
        System.out.println("------------begin LRDtraverseRecursive------------");
        LRDtraverseRecursive(t);
        System.out.println("------------begin LRDtraverse------------");
        LRDtraverse(t);
        System.out.println("------------leveltraverse------------");
        leveltraverse(t);
        System.out.println("------------MIRROR------------");
        mirror(t);
        System.out.println("------------leveltraverse------------");
        leveltraverse(t);
        System.out.println("------------MIRROR BACK------------");
        mirror(t);
        System.out.println("------------leveltraverse------------");
        leveltraverse(t);
        System.out.println("TREE DEPTH IS " + depth(t));
    }
}
