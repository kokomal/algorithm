/**
 * @Title: CompanyPartyConvivialityAlgo.java
 * @Package: yuanjun.chen.base.dynamicprogramming.companyParty
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年9月14日 上午8:54:09
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.dynamicprogramming.companyParty;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import yuanjun.chen.base.dynamicprogramming.companyParty.EmployeeTreeNode.ENTER_TYPE;

/**
 * @ClassName: CompanyPartyConvivialityAlgo
 * @Description: 公司聚会DP算法
 * @author: 陈元俊
 * @date: 2018年9月14日 上午8:54:09
 */
public class CompanyPartyConvivialityAlgo {
    private static Map<String, Integer> happiness = new HashMap<>();
    private static EmployeeTreeNode boss;

    private static List<String> finalnames = new LinkedList<>();
    private static List<List<String>> allfinalnames = new LinkedList<>();

    public static void init(Map<String, Integer> srcHappiness, EmployeeTreeNode srcBoss) {
        happiness = new HashMap<>();
        happiness.putAll(srcHappiness);
        boss = srcBoss;
        allfinalnames.add(new LinkedList<>());
    }

    public static void solve() {
        postOrderTraverse(boss);
        System.out.println("=======PRINTING JUST ONE VIABLE LIST=======");
        generateOneList();
        System.out.println("=======PRINTING ALL POSSIBLE LISTS=======");
        generateAllLists();
    }

    private static void generateOneList() {
        boss.enterType = boss.InPoint > boss.NotInPoint ? ENTER_TYPE.IN : ENTER_TYPE.NOTIN;
        decideNames(boss);
        System.out.println(finalnames);
    }

    private static void generateAllLists() {
        boss.judgeEnter();
        System.out.println("Boss-" + boss.name + " decides to " + boss.enterType);
        decideAllNames(boss, allfinalnames);
        System.out.println(allfinalnames);
    }

    public static void postOrderTraverse(EmployeeTreeNode node) {
        if (node == null) {
            return;
        }
        if (node.getChild() == null) { // 说明为叶子节点
            node.InPoint = happiness.get(node.name); // 在场分数直接查表
            node.NotInPoint = 0; // 不在场分数为0
        } else { // 说明为父节点
            Integer inP = happiness.get(node.name);
            int notinP = 0;
            EmployeeTreeNode son = node.getChild();
            while (son != null) {
                postOrderTraverse(son); // 标记所有的子节点后，再看分数哪个比较高
                inP += son.NotInPoint;
                notinP += Math.max(son.NotInPoint, son.InPoint);
                son = son.getSibling();
            }
            node.InPoint = inP;
            node.NotInPoint = notinP;
        }
    }

    /**   
     * @Title: decideNames   
     * @Description: 只打印一份邀请函   
     * @param node      
     * @return: void      
     */
    public static void decideNames(EmployeeTreeNode node) {
        if (ENTER_TYPE.IN.equals(node.enterType)) { // 父参与，则子必然不参与
            finalnames.add(node.name);
            EmployeeTreeNode son = node.getChild();
            while (son != null) {
                son.enterType = ENTER_TYPE.NOTIN;
                decideNames(son);
                son = son.getSibling();
            }
        } else if (ENTER_TYPE.NOTIN.equals(node.enterType)) { // 父不参与，子参与竞争，每一个子需要给出立场
            EmployeeTreeNode son = node.getChild();
            while (son != null) {
                if (son.InPoint > son.NotInPoint) {
                    son.enterType = ENTER_TYPE.IN;
                } else {
                    son.enterType = ENTER_TYPE.NOTIN;
                }
                decideNames(son);
                son = son.getSibling();
            }
        } // 其实还有一种无所谓的可能性，见下
    }

    /**
     * @Title: decideAllNames
     * @Description: 打印出所有邀请函
     * @param node
     * @param names
     * @return: void
     */
    public static void decideAllNames(EmployeeTreeNode node, List<List<String>> names) {
        if (ENTER_TYPE.IN.equals(node.enterType)) { // 父参与，则子必然不参与
            for (List<String> ll : names) {
                ll.add(node.name);
            }
            EmployeeTreeNode son = node.getChild();
            while (son != null) {
                son.enterType = ENTER_TYPE.NOTIN;
                decideAllNames(son, names);
                son = son.getSibling();
            }
        } else if (ENTER_TYPE.NOTIN.equals(node.enterType)) { // 父不参与，子参与竞争，每一个子需要给出立场
            EmployeeTreeNode son = node.getChild();
            while (son != null) {
                son.judgeEnter();
                decideAllNames(son, names);
                son = son.getSibling();
            }
        } else { // WHATEVER
            List<List<String>> copiednamesA = deepcopy(names);
            List<List<String>> copiednamesB = deepcopy(names);
            // 先排自己不参与的情况
            EmployeeTreeNode son = node.getChild();
            while (son != null) {
                son.judgeEnter();
                decideAllNames(son, copiednamesA);
                son = son.getSibling();
            }
            // 再排自己参与的情况
            for (List<String> nm : copiednamesB) {
                nm.add(node.name);
            }
            son = node.getChild();
            while (son != null) {
                son.enterType = ENTER_TYPE.NOTIN;
                decideAllNames(son, copiednamesB);
                son = son.getSibling();
            }
            names.clear();
            names.addAll(copiednamesA);
            names.addAll(copiednamesB);
        }
    }

    private static List<List<String>> deepcopy(List<List<String>> names) {
        List<List<String>> res = new LinkedList<>();
        for (List<String> ll : names) {
            List<String> r = new LinkedList<>();
            for (String l : ll) {
                r.add(l);
            }
            res.add(r);
        }
        return res;
    }

    public static void main(String[] args) {}
}
