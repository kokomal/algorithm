/**
 * @Title: BTreeHolder.java
 * @Package: yuanjun.chen.advanced.btree
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年11月7日 上午10:53:49
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.advanced.btreelite;

import java.util.ArrayList;
import java.util.List;
import yuanjun.chen.advanced.common.BTreeOnePage;
import yuanjun.chen.advanced.common.GlobalPageNoGen;
import static yuanjun.chen.base.common.CommonUtils.*;

/**
 * @ClassName: BTreeHolder
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: 陈元俊
 * @date: 2018年11月7日 上午10:53:49
 */
public class BTreeHolderLite {
    private int degree;
    BTreeNodeLite root;
    private String tableName;

    /**
     * @Title: init
     * @Description: 根据dgr度数创建并持久化一个空的B树
     * @param dgr
     * @throws Exception
     * @return: void
     */
    public void init(String tableName, int dgr) throws Exception {
        this.degree = dgr;
        this.root = PageManager.newEmptyLeafNode(tableName, degree);
        this.root.keys = new ArrayList<>();
        this.root.children = new ArrayList<>();
        this.tableName = tableName;
        PageManager.save(tableName, root);
        // 这里只存取root的pgNo是不够的，因为root泯然众人，需要有个meta信息来记录表信息
        DiskUtilLite.diskWriteMeta(tableName, degree, root.pageNo);
    }

    /*
     * 从META里面重构数据
     */
    public void rebuild(String tableName) throws Exception {
        this.tableName = tableName;
        BTreeOnePage page = PageManager.readMeta(tableName);
        if (page == null) {
            return;
        }
        this.root = PageManager.newNodeFromPage(page);
        PageManager.save(tableName, root);
    }

    // 将来考虑增加事务
    public void insert(String k) throws Exception {
        System.out.println("INSERTING " + k);
        if (this.root == null) {
            // 这里不新建tree，因为缺很多基本参数，而且不应该允许非法的建立和插入
            System.out.println("EMPTY TREE ROOT, ABORT!"); 
            return;
        }
        if (root.isFull()) { // 满了，注意root可以违背度数的最低number
            BTreeNodeLite s = PageManager.newEmptyLeafNode(tableName, degree); // 新的root
            BTreeNodeLite r = this.root;
            this.root = s;
            s.setIsLeaf(false); // s现在是root，而且有孩子，因此不是Leaf
            List<Long> children = new ArrayList<>();
            children.add(r.pageNo); // 老root退位
            List<String> keys = new ArrayList<>();
            s.setKeys(keys);
            s.setChildren(children);
            splitChild(s, 1); // 分裂第一个child
            DiskUtilLite.diskWriteMeta(tableName, degree, s.pageNo); // 这里要重新改变table的meta资料
            insertNonFull(s, k);
            PageManager.save(tableName, s);
        } else { // 不满则太好了
            insertNonFull(root, k);
        }
        report(root);
    }

    // 不满的插入操作，属于激进型扩张策略
    public void insertNonFull(BTreeNodeLite x, String k) throws Exception {
        int i = x.n;
        if (x.getIsLeaf()) { // 是叶子节点，直接更改keys即可,因为叶子节点的所有children都是无意义的
            while (i >= 1 && less(k, x.getKeyAt(i))) {
                x.setKeyAt(i + 1, x.getKeyAt(i));
                i = i - 1;
            }
            x.setKeyAt(i + 1, k);
            x.n = x.n + 1;
            PageManager.save(tableName, x);
        } else { // 是内部节点
            while (i >= 1 && less(k, x.getKeyAt(i))) {
                i = i - 1;
            }
            i = i + 1;
            BTreeNodeLite chdNodeAtI = PageManager.fetchNodeByPgNo(tableName, x.getChildrenAt(i));
            if (chdNodeAtI.isFull()) { // 子已经满了，说明会溢出，分裂之
                splitChild(x, i);
                if (more(k, x.getKeyAt(i))) {
                    i = i + 1; // 无需diskRead因为会落到splitChild新增的节点上
                }
            }
            BTreeNodeLite chdNodeAtX = PageManager.fetchNodeByPgNo(tableName, x.getChildrenAt(i));
            insertNonFull(chdNodeAtX, k);
        }
    }

    // 对节点x的第i号元素进行分裂
    public void splitChild(BTreeNodeLite x, int i) {
        BTreeNodeLite y = PageManager.fetchNodeByPgNo(tableName, x.getChildrenAt(i)); // 原先儿子要拆分为2，并且提取一个到x,因为x必然不满，因此可以这么操作
        String newK = y.getKeyAt(degree); // Y中央的Key，即将上调x
        BTreeNodeLite z = packZ(y); // x分裂的另外一个儿子
        y.n = degree - 1;
        for (int j = x.n + 1; j >= i + 1; j--) {
            x.modifyChildAt(j + 1, x.getChildrenAt(j));
        }
        x.modifyChildAt(i + 1, z.pageNo); // 其实是i+1号元素
        for (int j = x.n; j >= i; j--) {
            x.setKeyAt(j + 1, x.getKeyAt(j));
        }
        x.setKeyAt(i, newK);
        x.n = x.n + 1; // 扩张
        // persist...
        try {
            PageManager.save(tableName, x);
            PageManager.save(tableName, y);
            PageManager.save(tableName, z);
        } catch (Exception e) {
        }
    }

    private BTreeNodeLite packZ(BTreeNodeLite y) {
        BTreeNodeLite z = new BTreeNodeLite(degree, GlobalPageNoGen.genNextPageNo(), y.getIsLeaf(), degree - 1);
        z.setKeys(new ArrayList<>());
        for (int j = 1; j < degree; j++) {
            z.getKeys().add(y.getKeyAt(j + degree));
        }
        z.setChildren(new ArrayList<>());
        if (!y.getIsLeaf()) {
            for (int j = 1; j <= degree; j++) {
                z.children.add(y.getChildrenAt(j + degree));
            }
        }
        // y只保留前一半，即移到z的不能存在y里面
        for (int j = 1; j <= degree; j++) {
            y.getKeys().remove(degree - 1); // 此外中间的key也不能留，因为被x录用
            if (!y.getIsLeaf()) {
                y.getChildren().remove(degree);
            }
        }
        return z;
    }

    public void report(BTreeNodeLite t) {
        if (t != null) {
            System.out.println("REPORT PAGENO." + t.pageNo + ", KEYS " + t.keys);
            if (!t.getIsLeaf()) {
                dispChildren(t);
            }
        }
    }

    private void dispChildren(BTreeNodeLite t) {
        if (t.children != null) {
            System.out.println("-*-*-");
            for (Long chId : t.children) {
                BTreeNodeLite nd = PageManager.fetchNodeByPgNo(tableName, chId);
                if (!nd.getIsLeaf()) {
                    System.out.println(nd.pageNo + " -^- " + nd.keys + " -^- " + nd.children);
                } else {
                    System.out.println(nd.pageNo + " -^- " + nd.keys + " -^- LEAF");
                }
                report(nd);
            }
        }
    }

    public void reportFull(BTreeNodeLite t) {
        if (t != null) {
            BTreeNodeLite newNode = PageManager.fetchNodeByPgNo(tableName, t.getPageNo());
            t.keys = newNode.keys;
            t.children = newNode.children;
            t.setIsLeaf(newNode.getIsLeaf());
            t.n = newNode.n;
            dispLoaded(t);
        }
    }

    private void dispLoaded(BTreeNodeLite newNode) {
        System.out.println("REPORT PAGENO." + newNode.pageNo + ", KEYS " + newNode.keys);
        if (!newNode.getIsLeaf()) {
            dispChildrenFull(newNode);
        }
    }

    private void dispChildrenFull(BTreeNodeLite t) {
        if (t.children != null) {
            System.out.println("-*-*-FETCH PAGENO " + t.pageNo + " CHILDREN INFO-*-*-");
            for (Long chId : t.children) {
                BTreeNodeLite nd = PageManager.fetchNodeByPgNo(tableName, chId);
                if (!nd.getIsLeaf()) {
                    // System.out.println(nd.pageNo + " -^- " + nd.keys + " -^- " + nd.children);
                    System.out.println("-*-*-PAGE " + t.pageNo + " HAS NON LEAF CHILD " + nd.pageNo + " -^- " + nd.keys
                            + " -^- WITH CHILDREN");
                } else {
                    System.out.println("-*-*-PAGE " + t.pageNo + " HAS LEAF CHILD " + nd.pageNo + " -^- " + nd.keys);
                }
                reportFull(nd);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BTreeHolderLite holder = new BTreeHolderLite();
//        holder.init("t_example2", 2);
//        String xx = "FSQKCLHTVWMRNPABXYDZE";
//        for (char x : xx.toCharArray()) {
//            holder.insert(String.valueOf(x));
//        }
         holder.rebuild("t_example");
         holder.reportFull(holder.root);
         System.out.println("==========");
         holder.reportFull(holder.root);
    }
}
