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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import yuanjun.chen.advanced.common.BTreeOnePage;
import yuanjun.chen.advanced.common.GlobalPageNoGen;
import yuanjun.chen.base.container.BSTnode;
import yuanjun.chen.base.container.MyQueue;
import yuanjun.chen.base.exception.QueueOverflowException;
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

    /** 从META里面重构数据. */
    public void rebuild(String tableName) throws Exception {
        this.tableName = tableName;
        BTreeOnePage page = PageManager.readMeta(tableName);
        if (page == null) {
            return;
        }
        this.root = PageManager.newNodeFromPage(page);
        this.degree = this.root.degree;
        PageManager.save(tableName, root);
    }

    /** 将来考虑增加事务. */
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

    /** 不满的插入操作，属于激进型扩张策略. */
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
            BTreeNodeLite chdNodeAtI = fetchIthNode(x.getChildrenAt(i));
            if (chdNodeAtI.isFull()) { // 子已经满了，说明会溢出，分裂之
                splitChild(x, i);
                if (more(k, x.getKeyAt(i))) {
                    i = i + 1; // 无需diskRead因为会落到splitChild新增的节点上
                }
            }
            BTreeNodeLite chdNodeAtX = fetchIthNode(x.getChildrenAt(i));
            insertNonFull(chdNodeAtX, k);
        }
    }

    /** 对节点x的第i号元素进行分裂. */
    public void splitChild(BTreeNodeLite x, int i) {
        BTreeNodeLite y = fetchIthNode(x.getChildrenAt(i)); // 原先儿子要拆分为2，并且提取一个到x,因为x必然不满，因此可以这么操作
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
                BTreeNodeLite nd = fetchIthNode(chId);
                if (!nd.getIsLeaf()) {
                    System.out.println(
                            "DGR " + nd.degree + ", pgNo" + nd.pageNo + " -^- " + nd.keys + " -^- " + nd.children);
                } else {
                    System.out.println("DGR " + nd.degree + ", pgNo" + nd.pageNo + " -^- " + nd.keys + " -^- LEAF");
                }
                report(nd);
            }
        }
    }

    private BTreeNodeLite fetchIthNode(Long chId) {
        return PageManager.fetchNodeByPgNo(this.degree, tableName, chId);
    }

    public void reportFull(BTreeNodeLite t) {
        if (t != null) {
            BTreeNodeLite newNode = fetchIthNode(t.getPageNo());
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
                BTreeNodeLite nd = fetchIthNode(chId);
                if (!nd.getIsLeaf()) {
                    // System.out.println(nd.pageNo + " -^- " + nd.keys + " -^- " + nd.children);
                    System.out.println("-*-*-PAGE " + t.pageNo + "DGR " + nd.degree + " HAS NON LEAF CHILD " + nd.pageNo
                            + " -^- " + nd.keys + " -^- WITH CHILDREN");
                } else {
                    System.out.println("-*-*-PAGE " + t.pageNo + "DGR " + nd.degree + " HAS LEAF CHILD " + nd.pageNo
                            + " -^- " + nd.keys);
                }
                reportFull(nd);
            }
        }
    }

    private static final BTreeNodeLite CHD_SPILITTER = new BTreeNodeLite(100, null, null, 0);
    private static final BTreeNodeLite PAR_SPILITTER = new BTreeNodeLite(100, null, null, 0);

    public void dispLevel() throws Exception {
        if (this.root == null) {
            System.out.print("#");
            return;
        }
        ArrayDeque<BTreeNodeLite> curqueue = new ArrayDeque<>(128); // arrayqueue不可能无限大
        curqueue.add(this.root);
        int line = 0;
        while (!curqueue.isEmpty()) {
            System.out.printf("==========LEVEL%d===========\n", ++line);
            ArrayDeque<BTreeNodeLite> nextqueue = new ArrayDeque<>(128);
            while (!curqueue.isEmpty()) {
                BTreeNodeLite node = curqueue.poll();
                boolean isEnd = curqueue.isEmpty();
                show(isEnd, node);
                List<Long> chlds = node.children;
                if (chlds != null && !chlds.isEmpty()) {
                    for (Long chId : chlds) {
                        nextqueue.add(fetchIthNode(chId));
                        nextqueue.add(CHD_SPILITTER);
                    }
                    nextqueue.removeLast();
                    nextqueue.add(PAR_SPILITTER);
                }
            }
            curqueue = nextqueue;
            System.out.println();
        }
    }

    private static void show(boolean isEnd, BTreeNodeLite node) {
        if (node == CHD_SPILITTER) {
            System.out.print("┃");
        } else if (node == PAR_SPILITTER && !isEnd) {
            System.out.print("※");
        } else if (node != PAR_SPILITTER) {
            System.out.print(node.keys);
        }
    }

    /** 每次的删除会导致大量的旋转和值修改，因此持久化要注意. */
    public void delete(String k) throws Exception {
        System.out.println("DELETE KEY " + k);
        BTreeResultLite ndK = findKey(k);
        if (ndK == null) {
            System.out.println("Key " + k + " NOT EXISTED!");
            return;
        }
        Stack<BTreeNodeLite> trace = traceFromRoot(ndK.node); // 预先获得从root到leaf的trace
        if (ndK.node.isLeaf) { // 这个节点是叶节点，删掉之后rebalance
            ndK.node.deleteKeyAt(ndK.index);
            ndK.node.updateN();
            if (ndK.node.isCritical()) { // 危险了，要重新平衡
                rebalance(trace);
            }
        } else { // 中间节点,则此节点不动，只需改动Key，真正的删除发生在leaf并且会发生重构，那是后面的事情
            // BTreeNodeLite succ = findSucc(ndK.node, ndK.index);
            BTreeNodeLite succ = findSucc(ndK.node, ndK.index);
            trace = traceFromRoot(succ);
            ndK.node.setKeyAt(ndK.index, succ.getKeyAt(1));
            removeFirst(trace);
        }
    }

    private void removeFirst(Stack<BTreeNodeLite> trace) throws Exception {
        trace.peek().deleteKeyAt(1); // 需要补上文件的删除
        trace.peek().updateN();
        if (trace.peek().isCritical()) {
            System.out.println("CRITICAL!!");
            rebalance(trace);
        }
    }

    public Stack<BTreeNodeLite> traceFromRoot(BTreeNodeLite node) {
        Stack<BTreeNodeLite> stack = new Stack<>();
        BTreeNodeLite cur = this.root;
        String nK = node.getKeyAt(1);
        while (cur != node) {
            stack.push(cur);
            int i = 1;
            while (i <= cur.n && more(nK, cur.getKeyAt(i))) {
                i = i + 1;
            }
            cur = fetchIthNode(cur.getChildrenAt(i));
        }
        stack.push(node);
        return stack;
    }

    /** 激难，重新平衡. */
    private void rebalance(Stack<BTreeNodeLite> trace) throws Exception {
        System.out.println("REBALANCE ACTIVATED!");
        while (!trace.isEmpty()) {
            BTreeNodeLite node = trace.pop();
            if (!node.isCritical()) { // 直接返回
                return;
            } else if (trace.isEmpty()) { // 理论上不会走到
                System.out.println("ROOT EMPTY");
                return; // 说明此时root不平衡，无所谓，只要不是空
            } else {
                BTreeNodeLite parent = trace.peek();
                BTreeNodeLite leftSibling = fetchLeftSibling(parent, node);
                if (leftSibling == null || !leftSibling.isRich()) {
                    BTreeNodeLite rightSibling = fetchRightSibling(parent, node);
                    if (rightSibling == null || !rightSibling.isRich()) {
                        // 抽root进行merge, 小心parent被抽空
                        merge(parent, rightSibling, leftSibling, node);
                        if (parent == this.root && parent.isKeyEmpty()) { // 要注意把root掏空的情况
                            this.root = node;
                            System.out.println("NEW ROOT LANDING!");
                            return;
                        }
                    } else {
                        // 借右兄弟
                        borrowRightSibling(parent, rightSibling, node);
                        return;
                    }
                } else {
                    // 借左兄弟
                    borrowLeftSibling(parent, leftSibling, node);
                    return;
                }
            }
        }
    }

    private static void merge(BTreeNodeLite parent, BTreeNodeLite rightSibling, BTreeNodeLite leftSibling,
            BTreeNodeLite node) {
        if (leftSibling != null) { // node就是极左，不得不合并右兄弟
            mergeLeftSibling(parent, leftSibling, node);
        } else { // 左右皆critical，默认合并左兄弟
            mergeRightSibling(parent, rightSibling, node);
        }
    }

    private static void mergeLeftSibling(BTreeNodeLite parent, BTreeNodeLite leftSibling, BTreeNodeLite node) {
        int parentPos = parent.findChildIndexByPgNo(leftSibling.pageNo); // key & child的索引，从1开始
        String parK = parent.getKeyAt(parentPos);
        parent.deleteChildAt(parentPos);
        parent.deleteKeyAt(parentPos);
        parent.updateN();
        leftSibling.insertOneRightKey(parK);
        leftSibling.addAllKeys(node.keys);
        leftSibling.updateN();
        node.clearKeys();
        node.addAllKeys(leftSibling.keys);
        leftSibling.children.addAll(node.children);
        node.clearChildren();
        node.addAllChildren(leftSibling.children);
        node.updateN();
    }

    private static void mergeRightSibling(BTreeNodeLite parent, BTreeNodeLite rightSibling, BTreeNodeLite node) {
        int parentPos = parent.findChildIndexByPgNo(rightSibling.pageNo); // key & child的索引，从1开始
        String parK = parent.getKeyAt(parentPos - 1);
        parent.deleteChildAt(parentPos);
        parent.deleteKeyAt(parentPos - 1);
        parent.updateN();
        node.insertOneRightKey(parK);
        node.addAllKeys(rightSibling.keys);
        node.addAllChildren(rightSibling.children);
        node.updateN();
    }

    private static void borrowLeftSibling(BTreeNodeLite parent, BTreeNodeLite leftSibling, BTreeNodeLite node) {
        Long leftSibChld = leftSibling.popTailChild(); // 先修改child，否则n不保
        String leftK = leftSibling.popTailKey();
        leftSibling.updateN();
        int parentPos = parent.findChildIndexByPgNo(leftSibling.pageNo); // key & child的索引，从1开始
        String parK = parent.getKeyAt(parentPos);
        node.insertOneLeftKey(parK);
        if (leftSibChld != null) {
            node.addChild(0, leftSibChld);
        }
        node.updateN();
        parent.setKeyAt(parentPos, leftK);
    }

    private static void borrowRightSibling(BTreeNodeLite parent, BTreeNodeLite rightSibling, BTreeNodeLite node) {
        Long rightSibChld = rightSibling.popFirstChild();
        String rightK = rightSibling.popFirstKey();
        rightSibling.updateN();
        int parentPos = parent.findChildIndexByPgNo(node.pageNo); // key & child的索引，从1开始
        String parK = parent.getKeyAt(parentPos);
        node.insertOneRightKey(parK);
        if (rightSibChld != null) {
            node.addChild(rightSibChld);
        }
        node.updateN();
        parent.setKeyAt(parentPos, rightK);
    }

    /** 获得parent下的右兄弟，可能为null. */
    private BTreeNodeLite fetchRightSibling(BTreeNodeLite parent, BTreeNodeLite node) {
        int pos = parent.findChildIndexByPgNo(node.getPageNo());
        // 说明是最后一个了，没有右兄弟
        return pos == parent.n + 1 ? null : fetchIthNode(parent.getChildrenAt(pos + 1)); // 注意数组的序号
    }

    /** 获得parent下的左兄弟，可能为null. */
    private BTreeNodeLite fetchLeftSibling(BTreeNodeLite parent, BTreeNodeLite node) {
        int pos = parent.findChildIndexByPgNo(node.getPageNo());
        // 说明是第一个，没有左兄弟
        return pos == 1 ? null : fetchIthNode(parent.getChildrenAt(pos - 1));
    }

    /** 查找node的后继，必然是leaf. */
    public BTreeNodeLite findSucc(BTreeNodeLite node, int index) {
        BTreeNodeLite next = fetchIthNode(node.getChildrenAt(index + 1));
        do {
            return next.isLeaf ? next : fetchIthNode(next.getChildrenAt(1));
        } while (true);
    }

    /** 查找node的前继，必然是leaf，二者选一. */
    public BTreeNodeLite findPred(BTreeNodeLite node, int index) {
        BTreeNodeLite pre = fetchIthNode(node.getChildrenAt(index));
        do {
            return pre.isLeaf ? pre : fetchIthNode(pre.getChildrenAt(pre.n + 1));
        } while (true);
    }

    public BTreeResultLite findKey(String k) {
        return searchKey(root, k);
    }

    private BTreeResultLite searchKey(BTreeNodeLite targ, String k) {
        if (targ != null) {
            int i = 1;
            while (i <= targ.n && more(k, targ.getKeyAt(i))) {
                i = i + 1;
            }
            if (i <= targ.n && eq(targ.getKeyAt(i), k)) {
                return new BTreeResultLite(targ, i);
            } else if (!targ.isLeaf) {
                return searchKey(fetchIthNode(targ.getChildrenAt(i)), k);
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        BTreeHolderLite holder = new BTreeHolderLite();
        // holder.init("t_example2", 2);
        holder.rebuild("t_example2");
        holder.reportFull(holder.root);
        System.out.println("=====DISPLAY B-TREES=====");
        holder.dispLevel();
        String xx = "FSQKCLHTVWMRNPABXYDZE";
        for (char x : xx.toCharArray()) {
            holder.delete(String.valueOf(x));
            holder.dispLevel();
        }

        // holder.insert("P");
        // holder.insert("W");
        // holder.insert("S");
        // BTreeResultLite f = holder.findKey("W");
        // System.out.println(f); // 查找
        // Stack<BTreeNodeLite> stack = holder.traceFromRoot(f.node);
        // while (!stack.isEmpty()) {
        // System.out.println("FROM STACK " + stack.pop().keys);
        // }
        // System.out.println(holder.findSucc(f.node, f.index));
    }
}
