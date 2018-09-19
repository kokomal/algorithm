/**
 * @Title: TspBitonicAlgo.java
 * @Package: yuanjun.chen.base.dynamicprogramming.tspbitonic
 * @Description: Bently提出的一种TSP问题的左右遍历DP算法
 * @author: 陈元俊
 * @date: 2018年9月19日 上午9:53:28
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.dynamicprogramming.tspbitonic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName: TspBitonicAlgo
 * @Description: Bently提出的一种TSP问题的左右遍历DP算法
 * @author: 陈元俊
 * @date: 2018年9月19日 上午9:53:28
 */
public class TspBitonicAlgo {
    public static class Coord {
        protected Coord preUp; // 专门的链表，打印来回
        protected Coord preDown;

        protected Integer x;
        protected Integer y;

        protected String cityName;

        public Coord(String city, Integer inX, Integer inY) {
            this.cityName = city;
            this.x = inX;
            this.y = inY;
        }

        public Double distance(Coord another) {
            return Math.sqrt(Math.pow((double) x - another.x, 2) + Math.pow((double) y - another.y, 2));
        }

        @Override
        public String toString() {
            return this.cityName + "[" + x + "," + y + "]";
        }

    }

    private static Coord[] points; // 约定输入的坐标，x不递减，且无重复的坐标
    private static int len;
    private static Double[][] distances;
    private static Integer[][] solutions;

    public static void init(Coord[] pts) {
        len = pts.length;
        points = new Coord[len];
        System.arraycopy(pts, 0, points, 0, len);
        distances = new Double[len][len];
        solutions = new Integer[len][len];
    }

    public static void solve() {
        setDistAtPlusOne(2, 1, weightPlusOne(2, 1)); // 初始化distances[1][0]
        setSolutionAtPlusOne(2, 1, 1);
        for (int i = 3; i <= len; ++i) {
            for (int j = 1; j < i; ++j) {
                if (i > j + 1) { // 说明i与j相隔大于1，此时i必须与i-1相连，d[i][j] = d[i-1][j] + W[i][i-1]
                    setDistAtPlusOne(i, j, safeDistancePlusOne(i - 1, j) + weightPlusOne(i, i - 1));
                    setSolutionAtPlusOne(i, j, i - 1);
                } else { // 说明j == i-1, 此时需要遍历所有的k，满足d[j][k] + W[i][k]最小
                    Double minDist = Double.MAX_VALUE;
                    int minK = 0;
                    for (int k = 1; k <= i - 2; k++) {
                        Double candi = safeDistancePlusOne(j, k) + weightPlusOne(i, k);
                        if (candi < minDist) {
                            minDist = candi;
                            minK = k;
                        }
                    }
                    setDistAtPlusOne(i, j, minDist);
                    setSolutionAtPlusOne(i, j, minK);
                }
            }
        }
        Double minRes = Double.MAX_VALUE;
        int minK = 0;
        for (int k = 1; k <= len - 1; k++) {
            Double candi = safeDistancePlusOne(len, k) + weightPlusOne(len, k);
            if (candi < minRes) {
                minRes = candi;
                minK = k;
            }
        }
        setSolutionAtPlusOne(len, len, minK);
        System.out.println("finally the shortest tsp is " + minRes + "Km");
        // for (int i = 0; i < len; i++) {
        // System.out.println(Arrays.toString(solutions[i]));
        // }
        parse(len, len);
        finalDispEx();
    }

    private static void finalDispEx() {
        Coord dest = points[len - 1];
        List<Coord> upList =  assembleFromDest(dest, dest.preUp);
        List<Coord> downList = assembleFromDest(dest, dest.preDown);

        List<Coord> fullList = new ArrayList<>(upList.size());
        fullList.addAll(upList);
        
        Collections.reverse(fullList);
        System.out.println("出发路线---" + fullList);
        System.out.println("回城路线---" + downList);
        
        fullList.remove(fullList.size() - 1);
        fullList.addAll(downList);

        int fullSize = fullList.size();
        System.out.println("===PRINT OUT THE FULL TSP-BITONIC TOUR LOOP===");
        for (int i = 0; i < fullSize; i++) {
            Coord cur = fullList.get(i);
            System.out.print(cur);
            if (i != fullSize - 1) {
                Coord next = fullList.get(i + 1);
                System.out.print("--" + cur.distance(next) + "Km-->");
            }
        }
        System.out.println("\n===FULL TSP-BITONIC TOUR LOOP ENDS===");
    }

    private static List<Coord> assembleFromDest(Coord dest, Coord coord) {
        List<Coord> list = new ArrayList<>();
        list.add(dest);
        Coord cur = coord;
        while (cur != null) {
            if (cur.preDown == null && cur.preUp == null) {
                list.add(cur);
                break;
            } else {
                Coord next = cur.preUp == null ? cur.preDown : cur.preUp;
                list.add(cur);
                cur = next;
            }
        }
        return list;
    }

    // left >= right
    // minK-->len是最后一步
    // minK==1==len是居中一环
    private static void parse(int left, int right) {
        if (right == 1 && left == 1) {
            return;
        }

        int bigger = left > right ? left : right;
        int smaller = left + right - bigger;

        Integer solu = safeGetSolution(left, right);
        // System.out.printf("between %d -- %d the solution is %d\n", smaller, bigger, solu);
        // System.out.println("Direct line: " + solu + " --> " + bigger);
        if (points[bigger - 1].preUp == null) {
            points[bigger - 1].preUp = points[solu - 1];
        } else {
            points[bigger - 1].preDown = points[solu - 1];
        }
        parse(smaller, solu);
    }

    public static void setDistAtPlusOne(int ida, int idb, Double val) {
        distances[ida - 1][idb - 1] = val;
    }

    public static void setSolutionAtPlusOne(int ida, int idb, Integer val) {
        solutions[ida - 1][idb - 1] = val;
    }

    public static Double weightPlusOne(int ida, int idb) {
        return points[ida - 1].distance(points[idb - 1]);
    }

    public static Integer safeGetSolution(int ida, int idb) {
        if (ida > idb) {
            return solutions[ida - 1][idb - 1];
        }
        return solutions[idb - 1][ida - 1];
    }

    public static Double safeDistancePlusOne(int ida, int idb) {
        if (ida > idb) {
            return distances[ida - 1][idb - 1];
        }
        return distances[idb - 1][ida - 1];
    }

    /**
     * 模拟从乌鲁木齐-->青岛再回到乌鲁木齐的过程 
     */
    public static void main(String[] args) {
        Coord x1 = new Coord("乌鲁木齐", 0, 0);
        Coord x2 = new Coord("昆明", 100, 600);
        Coord x3 = new Coord("银川", 200, 300);
        Coord x4 = new Coord("西安", 500, 200);
        Coord x5 = new Coord("厦门", 600, 500);
        Coord x6 = new Coord("北京", 700, 100);
        Coord x7 = new Coord("青岛", 800, 400);

        Coord[] pts = new Coord[] {x1, x2, x3, x4, x5, x6, x7};
        init(pts);
        solve();
    }
}
