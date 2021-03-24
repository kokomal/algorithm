package yuanjun.chen.advanced.dp.partycompany;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import yuanjun.chen.advanced.dp.assist.CsvReader;
import yuanjun.chen.advanced.dp.assist.GraphDrawer;

public class NodeParser {

    public Map<String, EmployeeTreeNode> nodeMaps = new HashMap<>();
    public GraphDrawer graphDrawer = new GraphDrawer();

    private void parseOneLine(String line) {
        // 岳云鹏,400,孔云龙,刘筱亭|尚筱菊|徐筱竹
        if (StringUtils.isNoneBlank(line)) {
            String[] contents = line.split(",", -1);
            String keyName = contents[0];
            int power = Integer.parseInt(contents[1]);
            EmployeeTreeNode mainNode = getOrNew(keyName);
            mainNode.inPoint = power;
            String sibling = contents[2];
            if (StringUtils.isNoneBlank(sibling)) {
                // 处理师兄弟关系，如果师弟已经注册，则需要关联，没有注册，则注册后再关联
                mainNode.setSibling(getOrNew(sibling)); // 关联
            }
            String children = contents[3];
            if (StringUtils.isNoneBlank(children)) {
                String[] childArr = children.split("\\|", -1);
                int i = 0;
                // 处理徒弟关系
                for (String child : childArr) {
                    EmployeeTreeNode childNode = getOrNew(child);
                    if (i == 0) {
                        mainNode.setChild(childNode);
                    }
                    ++i;
                }
            }
        }
    }

    private EmployeeTreeNode getOrNew(String keyName) {
        if (!nodeMaps.containsKey(keyName)) {
            nodeMaps.put(keyName, new EmployeeTreeNode(keyName));
        }
        return nodeMaps.get(keyName);
    }

    public void buildRelationshipFromCsv(String csv) {
        this.nodeMaps = new HashMap<>();
        CsvReader.readCsv(csv).forEach(this::parseOneLine);
    }

    public String callGraphDrawer(String fn, boolean highlight) {
        return graphDrawer.drawGraphVizPng(nodeMaps.values(), fn, highlight);
    }

    // 第一轮自顶而下的遍历，取child和sibling的值，决定是否参加
    // node不为null
    public void topDownCalc(final EmployeeTreeNode node) {
        System.out.println("Parsing " + node.name);
        // 只遍历子孙
        if (node.getChild() != null) {
            EmployeeTreeNode child = node.getChild();
            int inP = node.inPoint;
            int outP = 0; // 对这个节点而言，需要根据子孙的分数进行重新计算
            while (child != null) {
                topDownCalc(child);
                inP += child.outPoint; // 父参加的话，子必不参加
                outP += Math.max(child.inPoint, child.outPoint); // 父不参加，子取MAX
                child = child.getSibling();
            }
            node.inPoint = inP;
            node.outPoint = outP;
        }
        // 这里为什么没有else，是因为原生的叶节点已经有现成的inPoint和outPoint:)
    }

    // 从boss入口，boss可能也有合伙人,但代码有点侵入
    public void viewTops(String boss) {
        EmployeeTreeNode node = nodeMaps.get(boss);
        topDownCalc(node);
        EmployeeTreeNode sibling = node.getSibling();
        while (sibling != null) {
            topDownCalc(sibling);
            sibling = sibling.getSibling();
        }
    }

    // 虚拟一个超级boss
    public void viewTopsEx(String boss) {
        EmployeeTreeNode virtualBoss = new EmployeeTreeNode("virt");
        virtualBoss.setChild(nodeMaps.get(boss));
        topDownCalc(virtualBoss);
        virtualBoss = null; // gc友好
    }

    // 在所有遍历都结束后，可以生成1份清单(保守版)
    public List<String> genAttendeeList(String boss) {
        List<String> finalNames = new ArrayList<>();
        EmployeeTreeNode virtualBoss = new EmployeeTreeNode("virt");
        virtualBoss.setChild(nodeMaps.get(boss));
        // virtualBoss很显然OUT
        // virtualBoss.enterType = ENTER_TYPE.OUT;
        traverseNames(virtualBoss, finalNames);
        virtualBoss = null;
        return finalNames;
    }

    // 自顶向下的第二轮遍历，为了标记各个节点是否参与
    // 在第一轮遍历过后，各个节点其实都有了In和Out的分数
    // 注意此时入参的node已经决定了参与与否，需要递归让树的底层分别给出决定
    private static void traverseNames(final EmployeeTreeNode node, List<String> finalNames) {
        if (ENTER_TYPE.IN.equals(node.enterType)) { // 父In，子不参与
            finalNames.add(node.name);
            EmployeeTreeNode son = node.getChild();
            while (son != null) {
                son.enterType = ENTER_TYPE.OUT; // 父In，子Out
                traverseNames(son, finalNames); // 子决定了之后才能让下级进行站位
                son = son.getSibling();
            }
        } else if (ENTER_TYPE.OUT.equals(node.enterType)) { // 父Out，子竞争
            EmployeeTreeNode son = node.getChild();
            while (son != null) {
                son.judgeEnterBinary(); // 每一个子根据自身的in和out给出决定
                traverseNames(son, finalNames); // 子决定了之后才能让下级进行站位
                son = son.getSibling();
            }
        }
    }

    public static void main(String[] args) {
        NodeParser parser = new NodeParser();
        parser.buildRelationshipFromCsv("party/employees.csv");
        parser.callGraphDrawer("raw", false);
        parser.viewTopsEx("郭德纲");
        parser.callGraphDrawer("first-topdown", false);
        System.out.println(parser.genAttendeeList("郭德纲"));
        parser.callGraphDrawer("final-invite", true);
    }
}
