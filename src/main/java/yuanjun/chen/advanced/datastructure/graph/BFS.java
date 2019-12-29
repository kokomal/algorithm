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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: BFS
 * @Description:
 * @author: 陈元俊
 * @date: 2019年12月28日 上午11:20:31
 */
public class BFS {

    private static List<TREENODE>[] adjList = new List[6];
    private static List<TREENODE> nodes = new ArrayList<>(); // set不方便进行随机访问选取
    private static TREENODE firstNode = null;
    static {
        TREENODE n1 = new TREENODE(1);
        TREENODE n2 = new TREENODE(2);
        TREENODE n3 = new TREENODE(3);
        TREENODE n4 = new TREENODE(4);
        TREENODE n5 = new TREENODE(5);
        TREENODE n6 = new TREENODE(6);
        firstNode = n3; // 这里需要指定起始BFS的node
        nodes.addAll(Arrays.asList(n1, n2, n3, n4, n5, n6));
        List<TREENODE> a1 = new ArrayList<>();
        a1.add(n1);
        a1.add(n4);
        adjList[0] = a1;
        List<TREENODE> a2 = new ArrayList<>();
        a2.add(n5);
        adjList[1] = a2;
        List<TREENODE> a3 = new ArrayList<>();
        a3.add(n6);
        a3.add(n5);
        adjList[2] = a3;
        List<TREENODE> a4 = new ArrayList<>();
        a4.add(n2);
        adjList[3] = a4;
        List<TREENODE> a5 = new ArrayList<>();
        a5.add(n4);
        adjList[4] = a5;
        List<TREENODE> a6 = new ArrayList<>();
        a6.add(n6);
        adjList[5] = a6;
    }

    public static void dispAdjList() {
        for (List<TREENODE> li : adjList) {
            System.out.println(JSONArray.toJSONString(li));
        }
    }

    public static void BFS() {
        Queue<TREENODE> queue = new ArrayBlockingQueue<>(adjList.length);
        firstNode.pre = null;
        firstNode.d = 0;
        firstNode.color = TREENODE.WHITE;
        queue.add(firstNode);
        while (!queue.isEmpty()) {
            TREENODE curNode = queue.poll();
            System.out.println("VISITING " + curNode.idx);
            int idx = curNode.idx; // 获得此节点在相邻矩阵里面的位置
            List<TREENODE> adjs = adjList[idx - 1];
            if (!CollectionUtils.isEmpty(adjs)) {
                for (TREENODE nextNode : adjs) {
                    if (TREENODE.WHITE.equals(nextNode.color)) {
                        nextNode.color = TREENODE.BLACK;
                        nextNode.d = curNode.d + 1;
                        queue.add(nextNode);
                    }
                }
            }
            // curNode.color = TREENODE.BLACK; // 完事染黑
        }
    }

    public static void visitAllNodes() {
        for (TREENODE tr : nodes) {
            System.out.println(JSONObject.toJSONString(tr));
        }
    }

    public static void main(String[] args) {
        dispAdjList();
        System.out.println("----------------------");
        BFS();
        System.out.println("----------------------");
        visitAllNodes();
    }
}
