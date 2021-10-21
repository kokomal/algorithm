package yuanjun.chen.advanced.dp.assist;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import yuanjun.chen.advanced.dp.partycompany.ENTER_TYPE;
import yuanjun.chen.advanced.dp.partycompany.EmployeeTreeNode;

public class GraphDrawer {
    public static final String picPath = "/Users/admin/";
    public static final String type = "png";
    public static final String repesentationType = "dot";

    public List<String> nodeDeclareLis = new ArrayList<>();
    public List<String> edgeLis = new ArrayList<>();
    public List<String> edgeEquivLis = new ArrayList<>();
    private static final char NEWLINE = '\n';
    private static final String mainGvTemplate = "digraph module_dys{ \n" + "\n" + "fontname = \"Microsoft YaHei\";\n"
            + "fontsize = 12;\nrankdir = TB;\n"
            + "node [fontname = \"LiSu\", fontcolor =\"#FFFFCC\", fontsize = 14, shape = \"Mrecord\", color=\"#000033\", style=\"filled\"]; \n"
            + "edge [fontname = \"LiSu\", fontsize = 14, color=\"red\" ];\n %s}";
    private static final String nodeDecalreTemplate = "%s [label=\" %s\"];";
    private static final String nodeHighLightTemplate = "%s [label=\" %s\", color=\"#FF3300\"];";
    private static final String edgeMasterTutorTemplate = "%s  -> %s [label=\" 师徒\"];";
    private static final String edgeSiblingTemplate = "%s -> %s [label=\" 师兄弟\", color=\"blue\"];";
    private static final String edgeEquivTemplate = "{rank=same; %s ; %s};";

    private void clear() {
        nodeDeclareLis = new ArrayList<>();
        edgeLis = new ArrayList<>();
        edgeEquivLis = new ArrayList<>();
    }

    // 得到nodeMaps后可以绘制graphViz
    private String packCoreGvContent(final Collection<EmployeeTreeNode> nodes, boolean highlight) {
        clear();
        for (EmployeeTreeNode node : nodes) {
            // 郭德纲 [label=" 郭德纲"];
            String template =
                    (highlight && ENTER_TYPE.IN.equals(node.enterType)) ? nodeHighLightTemplate : nodeDecalreTemplate;
            String declare =
                    String.format(template, node.name, node.name + "[" + node.inPoint + "," + node.outPoint + "]");
            nodeDeclareLis.add(declare);
            if (node.getChild() != null) {
                // 郭德纲 -> 岳云鹏 [label=" 师徒"];
                String master_tutor = String.format(edgeMasterTutorTemplate, node.name, node.getChild().name);
                edgeLis.add(master_tutor);
            }
            if (node.getSibling() != null) {
                // 郭德纲:e -> 于谦:w [label=" 师兄弟", style="dotted"];
                String sibling = String.format(edgeSiblingTemplate, node.name, node.getSibling().name);
                edgeLis.add(sibling);
                String equiv = String.format(edgeEquivTemplate, node.name, node.getSibling().name);
                edgeEquivLis.add(equiv);
            }
        }
        return batchPackGvContent();
    }

    private String batchPackGvContent() {
        StringBuilder sb = new StringBuilder();
        nodeDeclareLis.forEach(node -> sb.append(node).append(NEWLINE));
        sb.append(NEWLINE);
        edgeEquivLis.forEach(node -> sb.append(node).append(NEWLINE));
        sb.append(NEWLINE);
        edgeLis.forEach(equiv -> sb.append(equiv).append(NEWLINE));
        // System.out.println("GV==\n" + sb.toString());
        return sb.toString();
    }

    public String drawGraphVizPng(final Collection<EmployeeTreeNode> nodes, String filename, boolean highlight) {
        GraphViz gv = new GraphViz();
        gv.add(String.format(mainGvTemplate, packCoreGvContent(nodes, highlight)));
        gv.increaseDpi();
        gv.increaseDpi();
        gv.increaseDpi();
        String fullPath = picPath + filename + "." + type;
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type, repesentationType), new File(fullPath));
        return fullPath;
    }
}
