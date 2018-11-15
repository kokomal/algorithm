/**
 * @Title: PageManager.java
 * @Package: yuanjun.chen.advanced.btreelite
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年11月9日 上午10:27:22
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.advanced.btreelite;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import yuanjun.chen.advanced.common.BTreeOnePage;
import yuanjun.chen.advanced.common.GlobalPageNoGen;

/**
 * @ClassName: PageManager
 * @Description: 内存页面管理类
 * @author: 陈元俊
 * @date: 2018年11月9日 上午10:27:22
 */
public class PageManager {
    // 这个cache存放了节点编号和数据，全局的pgNo只有一份对应的节点！
    private static Map<String, Map<Long, BTreeNodeLite>> pageCache = new ConcurrentHashMap<>();

    public static BTreeNodeLite fetchNodeByPgNo(int dgr, String tableName, Long pgNo) {
        // System.out.println("FetchPgno " + pgNo);
        // 很简单，如果内存里面有，就捞出来，否则要重新读取并设置状态
        Map<Long, BTreeNodeLite> tableCache = safeGetTableCache(tableName);
        if (!tableCache.containsKey(pgNo)) {
            System.out.println("HAD TO READ THE FILE");
            BTreeOnePage pg = DiskUtilLite.fetchByPgNo(tableName, pgNo);
            pg.setDgr(dgr); // 注意数据文件没有degree，需要从meta里面导入
            tableCache.put(pgNo, convertToNodeFromPage(pg));
        }
        return tableCache.get(pgNo);
    }
    
    public static void save(String tableName, BTreeNodeLite node) throws Exception {
        Map<Long, BTreeNodeLite> tableCache = safeGetTableCache(tableName);
        tableCache.put(node.pageNo, node);
        DiskUtilLite.diskWrite(tableName, node); // 存盘可以异步执行，这里仅作展示刷盘
    }
    
    public static void delete(String tableName, BTreeNodeLite node) {
        Map<Long, BTreeNodeLite> tableCache = safeGetTableCache(tableName);
        tableCache.remove(node.pageNo);
        DiskUtilLite.diskDelete(tableName, node.pageNo); // 存盘可以异步执行，这里仅作展示刷盘
    }

    public static BTreeNodeLite newNodeFromPage(BTreeOnePage page) {
        BTreeNodeLite nd = new BTreeNodeLite(page.getDgr(), page.getPgNo(), page.getIsLeaf(), page.getN());
        nd.keys = page.getKeys();
        nd.children = page.getChildren();
        return nd;
    }
    
    public static BTreeNodeLite newEmptyLeafNode(String tableName, int degree) throws Exception {
        BTreeNodeLite nd = new BTreeNodeLite(degree, GlobalPageNoGen.genNextPageNo(), true, 0);
        return nd;
    }
    
    public static BTreeOnePage readMeta(String tableName){
        return DiskUtilLite.diskReadMeta(tableName);
    }
    
    private static Map<Long, BTreeNodeLite> safeGetTableCache(String tableName) {
        if (!pageCache.containsKey(tableName)) {
            pageCache.put(tableName, new ConcurrentHashMap<>());
        }
        return pageCache.get(tableName);
    }

    private static BTreeNodeLite convertToNodeFromPage(BTreeOnePage pg) {
        BTreeNodeLite node = new BTreeNodeLite(pg.getDgr(), pg.getPgNo(), pg.getIsLeaf(), pg.getN());
        node.keys = pg.getKeys();
        node.children = pg.getChildren();
        return node;
    }
}
