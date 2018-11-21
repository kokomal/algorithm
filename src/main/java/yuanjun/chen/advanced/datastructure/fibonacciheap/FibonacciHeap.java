/**
 * @Title: FibonacciHeap.java
 * @Package: yuanjun.chen.advanced.datastructure.fibonacciheap
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年11月21日 上午9:35:39
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.advanced.datastructure.fibonacciheap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import yuanjun.chen.base.common.CommonUtils;

/**
 * @ClassName: FibonacciHeap
 * @Description: 主类，相对于holder
 * @author: 陈元俊
 * @date: 2018年11月21日 上午9:35:39
 */
public class FibonacciHeap<T extends Comparable<T>> {
    
    private static double phi = (Math.sqrt(5) + 1) / 2.0;
    private static double logPhi = Math.log(phi);
    int n = 0; // 总共多少元素
    List<FibonacciNode<T>> root = new ArrayList<>();
    FibonacciNode<T> min = null; // 最小的那个node

    /* FIB-HEAP-UNION */
    public void fibHeapUnion(final FibonacciHeap<T> other) {
        this.root.addAll(other.root);
        if ((this.min == null) || (other.min != null && other.min.key < this.min.key)) {
            this.min = other.min;
        }
        this.n += other.n;
    }

    /* FIB-HEAP-INSERT */
    public void fibHeapInsert(FibonacciNode<T> x) { // 假设x.key有值
        x.degree = 0;
        x.parent = null;
        x.child = new ArrayList<>();
        x.mark = false;
        if (min == null) {
            addRoot(x);
            this.min = x;
        } else {
            addRoot(x);
            if (x.key < this.min.key) {
                this.min = x;
            }
        }
        this.n++;
    }

    public FibonacciNode<T> peekMin() {
        return min;
    }
    
    /* FIB-HEAP-EXTRACT-MIN */
    public FibonacciNode<T> extractMin() {
        FibonacciNode<T> z = this.min;
        if (z != null) {
            for (FibonacciNode<T> node : z.child) {
                addRoot(node);
                node.parent = null;
            }
            int pos = root.indexOf(z);
            removeRoot(z);
            boolean zAlone = root.isEmpty();
            if (zAlone) {
                this.min = null;
            } else {
                this.min = root.get(pos % root.size()); // 获得链表的下一个，TODO可以优化，不要用链表的索引
                consolidate();
            }
            this.n--;
        }
        return z;
    }

    /* FIB-HEAP-LINK */
    public void fibHeapLink(FibonacciNode<T> y, FibonacciNode<T> x) {
        removeRoot(y);
        x.addChild(y);
        y.parent = x;
        y.mark = false;
        x.degree++;
    }

    private void addRoot(FibonacciNode<T> nd) {
        if (nd == null) {
            return;
        }
        int minPos = this.root.indexOf(min); // 习惯插入在min左侧
        if (minPos < 0) {
            this.root.add(nd);
        } else {
            this.root.add(minPos, nd);
        }
    }

    private void removeRoot(FibonacciNode<T> nd) {
        if (root == null || nd == null) {
            return;
        }
        this.root.remove(nd);
    }

    /** CONSOLIDATE */
    @SuppressWarnings("unchecked")
    private void consolidate() {
        int D = calcD();
        System.out.println("D=" + D);
        FibonacciNode<T>[] A = (FibonacciNode<T>[]) Array.newInstance(root.get(0).getClass(), D + 1);
        for (int i = 0; i <= D; i++) {
            A[i] = null;
        }
        int len = this.root.size();
        for (int i = 0; i < len; i++) {
            FibonacciNode<T> x = root.get(i);
            int d = x.degree;
            while (A[d] != null) {
                FibonacciNode<T> y = A[d];
                if (x.key > y.key) {
                    FibonacciNode<T> tmp = x;
                    x = y;
                    y = tmp;
                }
                fibHeapLink(y, x);
                A[d] = null;
                d++;
            }
            A[d] = x;
        }
        this.min = null;
        for (int i = 0; i < D; i++) {
            if (A[i] != null) {
                if (this.min == null) {
                    this.root = new ArrayList<>();
                    root.add(A[i]);
                    this.min = A[i];
                } else {
                    this.root.add(A[i]);
                    if (this.min.key > A[i].key) {
                        this.min = A[i];
                    }
                }
            }
        }
    }

    private int calcD() {
        int d = (int) Math.floor(Math.log(this.n) / logPhi);
        return d;
    }
    
    public void printAll() {
        System.out.println("ROOT HAS " + root.size() + " ELEMENTS");
        for (FibonacciNode<T> nd : this.root) {
            nd.print();
        }
    }
    
    public static void main(String[] args) {
        FibonacciHeap<Integer> holder = new FibonacciHeap<>();
        FibonacciNode<Integer> cc = new FibonacciNode<>();
        cc.key = 22;
        cc.val = 99;
        holder.fibHeapInsert(cc);
        FibonacciNode<Integer> dd = new FibonacciNode<>();
        dd.key = 33;
        dd.val = 88;
        holder.fibHeapInsert(dd);
        FibonacciNode<Integer> ee = new FibonacciNode<>();
        ee.key = 11;
        ee.val = 77;
        holder.fibHeapInsert(ee);
        holder.printAll();
        System.out.println("=====================");
        System.out.println(holder.extractMin().key);
        holder.printAll();
    }
}
