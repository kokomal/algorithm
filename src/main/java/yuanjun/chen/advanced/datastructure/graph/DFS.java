/**
 * @Title: DFS.java
 * @Package: yuanjun.chen.advanced.datastructure.graph
 * @Description:
 * @author: 陈元俊
 * @date: 2019年12月28日 上午11:20:31
 * @version V1.0
 * @Copyright: 2019 All rights reserved.
 */
package yuanjun.chen.advanced.datastructure.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.collections4.CollectionUtils;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: DFS
 * @Description: DFS的特点为： @1 无需起始节点，所有的点都可以被访问到 但不是每一个点都有pre @2
 *               每一个点的d和f都有值并且闭包[1,2N]，d和f指定了节点被访问的起始时间 使用递归和栈的方式，都可以实现DFS，栈的方式更加优雅
 * @author: 陈元俊
 * @date: 2019年12月28日 上午11:20:31
 */
public class DFS {

    private static List<List<Integer>> adjList = new ArrayList<>(); // adj不可以再存一份拷贝，应该只存索引,从1开始
    private static List<TREENODE> nodes = new ArrayList<>(); // set不方便进行随机访问选取，取ArrayList
    private static AtomicInteger timestamp;
    static {
        init();
    }

    private static void init() {
        rewindClock();
        nodes = new ArrayList<>();
        adjList = new ArrayList<>();
        TREENODE n1 = new TREENODE(1);
        TREENODE n2 = new TREENODE(2);
        TREENODE n3 = new TREENODE(3);
        TREENODE n4 = new TREENODE(4);
        TREENODE n5 = new TREENODE(5);
        TREENODE n6 = new TREENODE(6);
        nodes.addAll(Arrays.asList(n1, n2, n3, n4, n5, n6));
        List<Integer> a1 = new ArrayList<>();
        a1.add(2);
        a1.add(4);
        adjList.add(a1);
        List<Integer> a2 = new ArrayList<>();
        a2.add(5);
        adjList.add(a2);
        List<Integer> a3 = new ArrayList<>();
        a3.add(6);
        a3.add(5);
        adjList.add(a3);
        List<Integer> a4 = new ArrayList<>();
        a4.add(2);
        adjList.add(a4);
        List<Integer> a5 = new ArrayList<>();
        a5.add(4);
        adjList.add(a5);
        List<Integer> a6 = new ArrayList<>();
        a6.add(6);
        adjList.add(a6);
    }

    public static void dispAdjList() {
        int all = adjList.size();
        for (int i = 0; i < all; i++) {
            List<Integer> ll = adjList.get(i);
            if (CollectionUtils.isNotEmpty(ll)) {
                System.out.println("NODE " + (i + 1) + " HAS ADJ AS BELOW");
                for (int idx : ll) {
                    System.out.println(JSONObject.toJSONString(nodes.get(idx - 1)));
                }
            }
        }
    }

    /*
     * 递归DFS
     */
    public static void DFS_Recur_Algo() {
        rewindClock();
        int all = nodes.size();
        for (int i = 0; i < all; i++) {
            if (nodes.get(i).amWhite()) {
                recurVisit(nodes.get(i));
            }
        }
    }

    private static void recurVisit(TREENODE node) {
        dispVisit(node);
        node.d = incAndGetClock();
        node.setGray();
        TREENODE desc = findFirstWhite(node);
        if (desc != null) { // 有下一个white节点
            desc.pre = Integer.toString(node.idx);
            recurVisit(desc);
        }
        node.setBlack();
        node.f = incAndGetClock();
        dispDyeBlack(node);
    }

    private static void dispVisit(TREENODE node) {
        System.out.println("VISIT " + JSONObject.toJSONString(node));
    }

    private static void dispDyeBlack(TREENODE node) {
        System.out.println("NOW DYE BLACK FOR " + JSONObject.toJSONString(node));
    }

    /*
     * 采用栈的DFS
     */
    public static void DFS_Stack_Algo() {
        rewindClock();
        int all = nodes.size();
        Stack<TREENODE> stack = new Stack<>();
        for (int i = 0; i < all; i++) {
            if (nodes.get(i).amWhite()) {
                stackVisit2(nodes.get(i), stack);
            }
        }
    }
    
    private static int incAndGetClock() {
        return timestamp.incrementAndGet();
    }
    
    private static void rewindClock() {
        timestamp = new AtomicInteger(0);
    }

    private static void stackVisit2(TREENODE treenode, Stack<TREENODE> stack) {
        purePush(treenode, stack);
        while (!stack.isEmpty()) {
            TREENODE tr = stack.peek();
            TREENODE desc = findFirstWhite(tr);
            if (desc != null) {
                desc.pre = Integer.toString(tr.idx); // 找到第一个白色后继就退出，记录pre，继续while
                purePush(desc, stack);
            } else {
                purePop(stack); // 找不到就弹出
            }
        }
    }

    private static TREENODE findFirstWhite(TREENODE tr) {
        List<Integer> adjs = adjList.get(tr.idx - 1);
        return CollectionUtils.isEmpty(adjs) ? null
                : adjs.stream().map(x -> nodes.get(x - 1)).filter(e -> e.amWhite()).findFirst().orElse(null);
    }

    private static void purePush(TREENODE treenode, Stack<TREENODE> stack) {
        dispVisit(treenode);
        treenode.setGray();
        treenode.d = incAndGetClock();
        stack.push(treenode);
    }

    private static void purePop(Stack<TREENODE> stack) {
        TREENODE treenode = stack.pop();
        treenode.f = incAndGetClock();
        treenode.setBlack();
        dispDyeBlack(treenode);
    }

    public static void visitAllNodes() {
        for (TREENODE tr : nodes) {
            System.out.println(JSONObject.toJSONString(tr));
        }
    }

    public static void main(String[] args) {
        System.out.println("============RECUR BELOW==========");
        visitAllNodes();
        System.out.println("----------------------");
        dispAdjList();
        System.out.println("----------------------");
        DFS_Recur_Algo();
        System.out.println("----------------------");
        visitAllNodes();
        System.out.println("============STACK BELOW==========");
        init();
        visitAllNodes();
        System.out.println("----------------------");
        dispAdjList();
        System.out.println("----------------------");
        DFS_Stack_Algo();
        System.out.println("----------------------");
        visitAllNodes();
    }
}
