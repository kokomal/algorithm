/**  
 * @Title: BTreeHolder.java   
 * @Package: yuanjun.chen.advanced.btree   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年11月7日 上午10:53:49   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.advanced.datastructure.oldbtree;

import java.util.ArrayList;
import java.util.List;
import yuanjun.chen.advanced.datastructure.common.BTreeOnePage;
import yuanjun.chen.advanced.datastructure.common.GlobalPageNoGen;
import yuanjun.chen.base.common.CommonUtils;

/**   
 * @ClassName: BTreeHolder   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 陈元俊 
 * @date: 2018年11月7日 上午10:53:49  
 */
public class BTreeHolder {
    private int degree;
    private BTreeNode root;
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
        this.setRoot(create());
        this.getRoot().keys = new ArrayList<>();
        this.getRoot().children = new ArrayList<>();
        this.tableName = tableName;
        DiskUtil.diskWrite(tableName, getRoot());
        // 这里只存取root的pgNo是不够的，因为root泯然众人，需要有个meta信息来记录表信息
        DiskUtil.diskWriteMeta(tableName, degree, getRoot().pageNo);
    }
    
    /*
     * 从META里面重构数据 
     */
    public void rebuild(String tableName) {
        this.tableName = tableName;
        BTreeOnePage page = DiskUtil.diskReadMeta(tableName);
        if (page == null) {
            return;
        }
        this.setRoot(new BTreeNode(page.getDgr(), page.getPgNo(), page.getIsLeaf(), page.getN()));
        this.getRoot().setIsLoaded(true);
        this.getRoot().keys = page.getKeys();
        this.getRoot().children = new ArrayList<>();
        for (Long childPgNo : page.getChildren()) {
            BTreeOnePage childPage = CacheManager.fetchPageByPgNo(tableName, childPgNo);
            BTreeNode chdNode = convertToNode(childPage);
            this.getRoot().children.add(chdNode);
        }
    }
    
    // 一层拷贝
    private BTreeNode convertToNode(BTreeOnePage page) {
        BTreeNode node = new BTreeNode(page.getDgr(), page.getPgNo(), page.getIsLeaf(), page.getN());
        node.setIsLoaded(true);
        node.keys = page.getKeys();
        node.children = new ArrayList<>();
        if (page.getChildren() != null) {
            for (Long childPgNo : page.getChildren()) { // 第二层就先不读取
                CacheManager.deleteNode(childPgNo);
                BTreeNode chd = new BTreeNode(page.getDgr(), childPgNo, null, null); // no loaded
                node.children.add(chd);
                CacheManager.putNode(childPgNo, chd);
            }
        }
        CacheManager.putNode(page.getPgNo(), node);
        return node;
    }

    public BTreeNode create() {
        return new BTreeNode(degree, GlobalPageNoGen.genNextPageNo(), true, 0);
    }
    
    // 将来考虑增加事务
    public void insert(String k) throws Exception {
        System.out.println("INSERTING " + k);
        if (getRoot().isFull()) { // 满了，注意root可以违背度数的最低number
            BTreeNode s = create(); // 新的root
            BTreeNode r = this.getRoot();
            this.setRoot(s);
            s.setIsLeaf(false);
            List<BTreeNode> children = new ArrayList<>();
            children.add(r); // 老root退位
            List<String> keys = new ArrayList<>();
            s.setKeys(keys);
            s.setChildren(children);
            splitChild(s, 1); // 分裂第一个child
            DiskUtil.diskWriteMeta(tableName, degree, s.pageNo); // 这里要重新改变root的meta资料
            insertNonFull(s, k);
        } else { // 不满则太好了
            insertNonFull(getRoot(), k);
        }
        report(getRoot());
    }
    
    // 不满的插入操作，属于激进型扩张策略
    public void insertNonFull(BTreeNode x, String k) throws Exception {
        int i = x.n;
        if (x.getIsLeaf()) { // 是叶子节点，直接更改keys即可,因为叶子节点的所有children都是无意义的
            while (i >= 1 && CommonUtils.less(k, x.getKeyAt(i))) {
                x.setKeyAt(i + 1, x.getKeyAt(i));
                i = i - 1;
            }
            x.setKeyAt(i + 1, k);
            x.n = x.n + 1;
            DiskUtil.diskWrite(tableName, x);
        } else { // 是内部节点
            while (i >= 1 && CommonUtils.less(k, x.getKeyAt(i))) {
                i = i - 1;
            }
            i = i + 1;
            if (!x.getChildrenAt(i).getIsLoaded()) {
                BTreeOnePage iPage = CacheManager.fetchPageByPgNo(tableName, x.getChildrenAt(i).pageNo);
                x.modifyChildAt(i, convertToNode(iPage)); // 将ith孩子更新
            }
            // check child fresh
            if (x.getChildrenAt(i).isFull()) { // 子已经满了，说明会溢出，分裂之
                splitChild(x, i);
                if (CommonUtils.more(k, x.getKeyAt(i))) {
                    i = i + 1; // 无需diskRead因为会落到splitChild新增的节点上
                }
            }
            insertNonFull(x.getChildrenAt(i), k); 
        }
    }
    
    // 对节点x的第i号元素进行分裂
    public void splitChild(BTreeNode x, int i) {
        BTreeNode y = x.getChildrenAt(i); // 原先儿子要拆分为2，并且提取一个到x,因为x必然不满，因此可以这么操作
        String newK = y.getKeyAt(degree); // Y中央的Key，即将上调x
        BTreeNode z = packZ(y); // x分裂的另外一个儿子
        y.n = degree - 1;
        for (int j = x.n + 1; j >= i + 1; j--) {
            x.modifyChildAt(j + 1, x.getChildrenAt(j));
        }
        x.modifyChildAt(i + 1, z); // 其实是i+1号元素
        for (int j = x.n; j >= i; j--) {
            x.setKeyAt(j + 1, x.getKeyAt(j));
        }
        x.setKeyAt(i, newK);
        x.n = x.n + 1; // 扩张
        // persist...
        try {
            DiskUtil.diskWrite(tableName, x);
            DiskUtil.diskWrite(tableName, y);
            DiskUtil.diskWrite(tableName, z);
        } catch (Exception e) {
        }
    }

    private BTreeNode packZ(BTreeNode y) {
        BTreeNode z = new BTreeNode(degree, GlobalPageNoGen.genNextPageNo(), y.getIsLeaf(), degree - 1);
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
    
    public void report(BTreeNode t) {
        if (t != null && t.getIsLoaded()) {
            System.out.println("REPORT PAGENO." + t.pageNo + ", KEYS " + t.keys);
            if (!t.getIsLeaf()) {
                dispChildren(t);
            }
        }
    }
    
    public void reportFull(BTreeNode t) {
        if (t != null && t.getIsLoaded()) {
            dispLoaded(t);
        } else if (t != null) {
            BTreeOnePage page = CacheManager.fetchPageByPgNo(tableName, t.getPageNo());
            BTreeNode newNode = convertToNode(page);
            t.keys = newNode.keys;
            t.children = newNode.children;
            t.setIsLoaded(true);
            t.setIsLeaf(newNode.getIsLeaf());
            t.n = newNode.n;
            dispLoaded(t);
        }
    }

    private void dispLoaded(BTreeNode newNode) {
        System.out.println("REPORT PAGENO." + newNode.pageNo + ", KEYS " + newNode.keys);
        if (!newNode.getIsLeaf()) {
            dispChildrenFull(newNode);
        }
    }

    private void dispChildrenFull(BTreeNode t) {
        if (t.children != null) {
            System.out.println("-*-*-FETCH PAGENO " + t.pageNo + " CHILDREN INFO-*-*-");
            for (BTreeNode nd : t.children) {
                if (nd.getIsLoaded()) {
                    if (!nd.getIsLeaf()) {
                        //System.out.println(nd.pageNo + " -^- " + nd.keys + " -^- " + nd.children);
                        System.out.println("-*-*-PAGE " + t.pageNo + " HAS CHILD " + nd.pageNo + " -^- " + nd.keys + " -^- WITH CHILDREN");
                    } else {
                        System.out.println("-*-*-PAGE " + t.pageNo + " HAS CHILD " + nd.pageNo + " -^- " + nd.keys + " -^- LEAF");
                    }
                } else {
                     System.out.println("-*-*-PAGE " + t.pageNo + " HAS CHILD " + nd.pageNo + " -^- BUT UNLOADED YET");
                }
                reportFull(nd);
            }
        }
    }

    private void dispChildren(BTreeNode t) {
        if (t.children != null) {
            System.out.println("-*-*-");
            for (BTreeNode nd : t.children) {
                if (nd.getIsLoaded()) {
                    if (!nd.getIsLeaf()) {
                        System.out.println(nd.pageNo + " -^- " + nd.keys + " -^- " + nd.children);
                    } else {
                        System.out.println(nd.pageNo + " -^- " + nd.keys + " -^- LEAF");
                    }
                } else {
                    System.out.println(nd.pageNo + " -^- UNLOADED");
                }
                report(nd);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BTreeHolder holder = new BTreeHolder();
         holder.init("t_example2", 2);
         String xx = "FSQKCLHTVWMRNPABXYDZE";
         for (char x : xx.toCharArray()) {
         holder.insert(String.valueOf(x));
         }
//        holder.rebuild("t_example");
//        holder.reportFull(holder.root);
//        System.out.println("==========");
//        holder.reportFull(holder.root);
    }

    /**
     * @return the root
     */
    public BTreeNode getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(BTreeNode root) {
        this.root = root;
    }
}
