/**
 * @Title: DijkstraAlgo.java
 * @Package: yuanjun.chen.advanced.datastructure.graph
 * @Description: Dijkstra算法用于统计加权有向图的某节点出发到其他节点的最短路径
 * @author: 陈元俊
 * @date: 2019年1月4日 上午10:39:55
 * @version V1.0
 * @Copyright: 2019 All rights reserved.
 */
package yuanjun.chen.advanced.datastructure.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: DijkstraAlgo
 * @Description: 阔别课堂15年，再见Dijkstra
 * @author: 陈元俊
 * @date: 2019年1月4日 上午10:39:55
 */
public class DijkstraAlgo {
    public static List<List<String>> findShortestPath(int[][] adjacentMatrix, int nodeIdx) throws Exception {
        // preliminary check
        if (adjacentMatrix == null)
            throw new Exception("illegal adjacent matrix");
        int len = adjacentMatrix.length;
        if (nodeIdx >= len)
            throw new Exception("illegal node index");
        int[] dist = adjacentMatrix[nodeIdx];
        System.out.println("OLD DIST=" + Arrays.toString(dist));
        int[] mark = new int[len]; // 是否已经标记了的mark
        int count = 1; // 已经找到最短路径的数目
        mark[nodeIdx] = 1; // 自己先标记
        List<List<String>> path = new ArrayList<List<String>>(len);
        for (int i = 0; i < len; i++) {
            path.add(new ArrayList<String>());
            path.get(i).add(String.valueOf(i));
        }
//        for (List<String> pa : path) {
//            System.out.println(pa);
//        }
        //path.get(nodeIdx).add(String.valueOf(nodeIdx));
        while (count < len) {
            // 找dist除mark之外的最小的pos
            int minPos = findMinInDistWithoutMark(dist, mark);
            count++;
            // System.out.println("Count" + count);
            
            // 扫荡一下mark，更新自身路径
            for (int i = 0; i < len; i++) {
                if (mark[i] == 1 && adjacentMatrix[i][minPos] != Integer.MAX_VALUE) {
                    int newDist = dist[i] + adjacentMatrix[i][minPos];
                    // System.out.println("NEWDIST=" + newDist + ", dist[minPos]=" + dist[minPos]);
                    if (newDist <= dist[minPos]) {
                        dist[minPos] = newDist;
                        List<String> outPosPath = new ArrayList<String>(path.get(i));
                        outPosPath.add(String.valueOf(minPos));
                        // System.out.println(outPosPath);
                        path.set(minPos, outPosPath);
                    }
                }
            }
            
            mark[minPos] = 1;
            
            // 找到minPos外延的所有pos
            List<Integer> outPoses = findAllOutStretch(minPos, adjacentMatrix[minPos], mark);
            // 遍历更新所有外延的dist
            for (Integer outPos : outPoses) {
                int newDistOfOutPos = dist[minPos] + adjacentMatrix[minPos][outPos];
                if (dist[outPos] > newDistOfOutPos) {
                    dist[outPos] = newDistOfOutPos; // 修正dist
                    List<String> outPosPath = new ArrayList<String>(path.get(minPos));
                    outPosPath.add(String.valueOf(outPos));
                    path.set(outPos, outPosPath);
                }
            }
        }
        System.out.println("NEW DIST=" + Arrays.toString(dist));
        return path;

    }

    private static List<Integer> findAllOutStretch(int minPos, int[] minPosRow, int[] mark) {
        List<Integer> outStretch = new ArrayList<>();
        int len = minPosRow.length;
        for (int i = 0; i < len; i++) {
            if (mark[i] == 0 && i != minPos && minPosRow[i] != Integer.MAX_VALUE) {
                outStretch.add(i);
            }
        }
        return outStretch;
    }

    // O[n]一次遍历获得最小pos
    private static int findMinInDistWithoutMark(int[] dist, int[] mark) {
        int min = Integer.MAX_VALUE;
        int pos = -1;
        int len = dist.length;
        for (int i = 0; i < len; i++) {
            if (mark[i] == 0 && dist[i] < min) {
                min = dist[i];
                pos = i;
            }
        }
        return pos;
    }

    public static int MAX = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        int[][] adjMatrix = new int[][] {
            {0, 1, 12, MAX, MAX, MAX}, 
            {MAX, 0, 9, 3, MAX, MAX}, 
            {MAX, MAX, 0, MAX, 5, MAX},
            {MAX, MAX, 4, 0, 13, 15}, 
            {MAX, MAX, MAX, MAX, 0, 4}, 
            {MAX, MAX, MAX, MAX, MAX, 0}
            };
        List<List<String>> res = findShortestPath(adjMatrix, 0);   
        for (List<String> pa : res) {
            for (String x : pa) {
                System.out.print((Integer.parseInt(x) + 1) + "-->");
            }
            System.out.println("\n===============");
        }
    }
}
