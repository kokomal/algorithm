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
import org.apache.commons.collections4.CollectionUtils;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: DFS
 * @Description: DFS的特点为：
 * @1 无需起始节点，所有的点都可以被访问到 但不是每一个点都有pre
 * @2 每一个点的d和f都有值并且闭包[1,2N]，d和f指定了节点被访问的起始时间
 * @author: 陈元俊
 * @date: 2019年12月28日 上午11:20:31
 */
public class DFS {

    private static List<List<Integer>> adjList = new ArrayList<>(); // adj不可以再存一份拷贝，应该只存索引,从1开始
    private static List<TREENODE> nodes = new ArrayList<>(); // set不方便进行随机访问选取，取ArrayList
    private static int timestamp = 0;
    static {
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
        timestamp = 0;
        int all = nodes.size();
        for (int i = 0; i < all; i++) {
            if (TREENODE.WHITE.equals(nodes.get(i).color)) {
                visit(nodes.get(i));
            }
        }
    }

    private static void visit(TREENODE node) {
        System.out.println("VISIT " + JSONObject.toJSONString(node));
        timestamp++;
        node.d = timestamp;
        int idx = node.idx;
        node.color = TREENODE.GRAY;
        List<Integer> adjs = adjList.get(idx - 1);
        if (CollectionUtils.isNotEmpty(adjs)) {
            for (int i : adjs) {
                TREENODE neighbor = nodes.get(i - 1);
                if (neighbor.color.equals(TREENODE.WHITE)) {
                    neighbor.pre = Integer.toString(node.idx);
                    visit(neighbor);
                }
            }
        }
        node.color = TREENODE.BLACK;
        timestamp++;
        node.f = timestamp;
        System.out.println("NOW DYE BLACK FOR " + JSONObject.toJSONString(node));
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
        DFS_Recur_Algo();
        System.out.println("----------------------");
        visitAllNodes();
    }
}
