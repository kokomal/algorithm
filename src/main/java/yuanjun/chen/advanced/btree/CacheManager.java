/**  
 * @Title: CacheManager.java   
 * @Package: yuanjun.chen.advanced.btree   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年11月8日 上午11:17:03   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.advanced.btree;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**   
 * @ClassName: CacheManager   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 陈元俊 
 * @date: 2018年11月8日 上午11:17:03  
 */
public class CacheManager {
    // 这个cache存放了页编号和数据
    private static Map<Long, BTreeOnePage> pageCache = new ConcurrentHashMap<>();
    
    public static Map<Long, BTreeNode> nodeCache = new ConcurrentHashMap<>();
    
    public static BTreeOnePage fetchPageByPgNo(String tableName, Long pgNo) {
        System.out.println("FetchPgno " + pgNo);
        // 很简单，如果内存里面有，就捞出来，否则要重新读取并设置状态
        if (!pageCache.containsKey(pgNo)) {
            BTreeOnePage pg = DiskUtil.fetchByPgNo(tableName, pgNo);
            pageCache.put(pgNo, pg);
            return pg;
        } else {
            return pageCache.get(pgNo);
        }
    }
    
    public static boolean containsPage(Long pgNo) {
        return pageCache.containsKey(pgNo);
    }
    
    public static boolean containsNode(Long pgNo) {
        return nodeCache.containsKey(pgNo);
    }
    
    public static void deleteNode(Long pgNo) {
        nodeCache.remove(pgNo);
    }
    
    public static void putNode(Long paNo, BTreeNode node) {
        nodeCache.put(paNo, node);
    }
    
    public static void forcePersist(String tableName, Long pgNo, BTreeOnePage pg) throws Exception {
        modifyOnly(pgNo, pg);
        persist(tableName, pgNo);
    }
    
    public static void modifyOnly(Long pgNo, BTreeOnePage pg) throws Exception {
        pageCache.put(pgNo, pg);
    }
    
    public static void persist(String tableName, Long pgNo) throws Exception {
        if (pageCache.containsKey(pgNo)) {
            DiskUtil.diskWrite(tableName, pageCache.get(pgNo));
        }
    }
}
