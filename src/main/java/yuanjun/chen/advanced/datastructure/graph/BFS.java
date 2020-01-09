/**
 * @Title: BFS.java
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
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import org.apache.commons.collections4.CollectionUtils;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: BFS
 * @Description:
 * @author: 陈元俊
 * @date: 2019年12月28日 上午11:20:31
 */
public class BFS {

    private static List<List<Integer>> adjList = new ArrayList<>(); // 邻接列表数组
    private static List<TREENODE> nodes = new ArrayList<>(); // set不方便进行随机访问选取，取ArrayList
    private static TREENODE firstNode = null;
    static {
        init();
    }

    private static void init() {
        adjList = new ArrayList<>();
        nodes = new ArrayList<>();
        TREENODE n1 = new TREENODE(1);
        TREENODE n2 = new TREENODE(2);
        TREENODE n3 = new TREENODE(3);
        TREENODE n4 = new TREENODE(4);
        TREENODE n5 = new TREENODE(5);
        TREENODE n6 = new TREENODE(6);
        firstNode = n3; // 这里需要指定起始BFS的node
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
     * BFS核心思想是从某一个点出发，维护一个FIFO的Queue BFS可能漏掉节点，如果从出发点不可达的话
     */
    public static void BFS_algo() {
        Queue<TREENODE> queue = new ArrayBlockingQueue<>(adjList.size());
        firstNode.pre = null;
        firstNode.d = 0;
        firstNode.color = TREENODE.WHITE;
        queue.add(firstNode);
        while (!queue.isEmpty()) {
            TREENODE curNode = queue.poll();
            System.out.println("VISITING " + curNode.idx);
            curNode.color = TREENODE.BLACK; // 进入为灰色（3态），或者黑色（2态）
            int idx = curNode.idx; // 获得此节点在相邻矩阵里面的位置
            List<Integer> adjs = adjList.get(idx - 1);
            if (!CollectionUtils.isEmpty(adjs)) {
                for (int next : adjs) {
                    TREENODE nextNode = nodes.get(next - 1);
                    if (TREENODE.WHITE.equals(nextNode.color)) {
                        nextNode.color = TREENODE.BLACK;
                        nextNode.d = curNode.d + 1;
                        queue.add(nextNode);
                    }
                }
            }
            // curNode.color = TREENODE.BLACK; // 完事染黑,其实BFS没有必要维护3个状态
        }
    }

    public static void visitAllNodes() {
        for (TREENODE tr : nodes) {
            System.out.println(JSONObject.toJSONString(tr));
        }
    }

    public static void main(String[] args) {
        visitAllNodes();
        System.out.println("----------------------");
        dispAdjList();
        System.out.println("----------------------");
        BFS_algo();
        System.out.println("----------------------");
        visitAllNodes();
    }
}
