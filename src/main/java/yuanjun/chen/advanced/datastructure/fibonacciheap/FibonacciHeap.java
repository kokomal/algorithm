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

/**
 * @ClassName: FibonacciHeap
 * @Description: 主类，相对于holder
 * @author: 陈元俊
 * @date: 2018年11月21日 上午9:35:39
 */
public class FibonacciHeap<T extends Comparable<T>> {
    
    private static double phi = (Math.sqrt(5) + 1) / 2.0; // 1.618... Golden ratio
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

    public void fibHeapInsert(int key, T val) {
        FibonacciNode<T> cc = new FibonacciNode<>();
        cc.key = key;
        cc.val = val;
        fibHeapInsert(cc);
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
    
    /* FIB-HEAP-EXTRACT-MIN O[logN] */
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
        FibonacciNode<T>[] A = (FibonacciNode<T>[]) Array.newInstance(root.get(0).getClass(), D + 1);
        for (int i = 0; i <= D; i++) {
            A[i] = null;
        }
        int len = this.root.size();
        List<FibonacciNode<T>> bkp = new ArrayList<>(root); // 拷贝一份root的备份
        
        for (int i = 0; i < len; i++) {
            FibonacciNode<T> x = bkp.get(i); // 获得root的节点可能有问题，因为每次循环会操作root这个链表，影响不定
            if (!root.contains(x)) { // root已经除名，那就跳过
                continue;
            }
            int d = x.degree;
            while (A[d] != null) {
                FibonacciNode<T> y = A[d];
                if (x.key > y.key) { // swap x,y 保证y的key大，导致y附属于x，这样可以保证小根堆的性质
                    FibonacciNode<T> tmp = x;
                    x = y;
                    y = tmp;
                }
                fibHeapLink(y, x); // y可能在x后也看在前， 此时y已经从root除名
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
        return (int) Math.floor(Math.log(this.n) / logPhi);
    }
    
    public void printAll() {
        if (this.root.isEmpty()) {
            System.out.println("THE FIBO HEAP IS EMPTY!");
            return;
        }
        System.out.println("ALL MEMEMBERS ARE " + n);
        System.out.println("THE MIN KEY IS " + min.key + " AND VAL IS " + min.val);
        System.out.println("ROOT HAS " + root.size() + " ELEMENTS");
        for (FibonacciNode<T> nd : this.root) {
            nd.print();
        }
    }
    
    /* FIB-HEAP-DECREASE-KEY O[1]的摊还代价 */
    public void fibHeapDecreaseKey(FibonacciNode<T> x, int k) {
        if (x.key < k) {
            System.out.println("NEW KEY IS GREATER THAN CURRENT KEY");
            return;
        }
        x.key = k;
        FibonacciNode<T> y = x.parent;
        if (y != null && x.key < y.key) {
            cut(x, y);
            cascadingCut(y);
        }
        if (x.key < min.key) {
            min = x;
        }
    }
    
    /* FIB-HEAP-DELETE */
    public FibonacciNode<T> fibHeapDelete(FibonacciNode<T> x) {
        fibHeapDecreaseKey(x, Integer.MIN_VALUE);
        return extractMin();
    }
    
    private void cascadingCut(FibonacciNode<T> y) {
        FibonacciNode<T> z = y.parent;
        if (z != null) {
            if (!z.mark) { // mark=true触发条件有3，即1-root，2-child，3-一个孩子被切掉
                z.mark = true;
            } else { // 如果到这里，z.mark=true的状态，说明有2个孩子被切掉了
                cut(y, z);
                cascadingCut(z);
            }
        }
    }

    // 把x从y的child里面除名，送到root
    private void cut(FibonacciNode<T> x, FibonacciNode<T> y) {
        y.child.remove(x);
        addRoot(x);
        x.parent = null;
        x.mark = false;
    }

    public static void main(String[] args) {
        FibonacciHeap<Integer> holder = new FibonacciHeap<>();
        FibonacciNode<Integer> xx = new FibonacciNode<>();
        xx.key = 55;
        xx.val = 100;
        holder.fibHeapInsert(xx);
        holder.fibHeapInsert(22, 99); // K:22 V:99
        holder.fibHeapInsert(33, 88);
        holder.fibHeapInsert(11, 77);
        holder.printAll();
        System.out.println("=====================");
        System.out.println(holder.extractMin().key);
        holder.printAll();
        System.out.println("=====================");
        holder.fibHeapDelete(xx);
        holder.printAll();
    }
}
