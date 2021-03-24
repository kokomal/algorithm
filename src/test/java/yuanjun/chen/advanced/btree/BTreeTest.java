/**
 * @Title: BTreeTest.java
 * @Package: yuanjun.chen.advanced.btree
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年11月9日 上午9:04:54
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.advanced.btree;

import java.io.File;
import org.junit.Test;
import yuanjun.chen.advanced.datastructure.common.GlobalConfig;
import yuanjun.chen.advanced.datastructure.oldbtree.BTreeHolder;

/**
 * @ClassName: BTreeTest
 * @Description: 传统B树的测试
 * @author: 陈元俊
 * @date: 2018年11月9日 上午9:04:54
 */
public class BTreeTest {
    /**   
     * @Title: testPersist   
     * @Description: 1.测试持久化到文件  
     * @throws Exception      
     * @return: void      
     */
    @Test
    public void testPersist() throws Exception {
        GlobalConfig.BTREE_PATH = "d://data/btreetest/";
        BTreeHolder holder = new BTreeHolder();
        holder.init("t_examplex", 2);
        String xx = "FSQKCLHTVWMRNPABXYDZE";
        for (char x : xx.toCharArray()) {
            holder.insert(String.valueOf(x));
        }
    }
    
    /**   
     * @Title: testRebuild   
     * @Description: 2.测试读取文件进行B树的重建  
     * @throws Exception      
     * @return: void      
     */
    @Test
    public void testRebuild() throws Exception {
        GlobalConfig.BTREE_PATH = "d://data/btreetest/";
        BTreeHolder holder = new BTreeHolder();
        holder.rebuild("t_examplex");
        holder.reportFull(holder.getRoot());
        System.out.println("=====AFTER LOADING=====");
        holder.reportFull(holder.getRoot());
    }
    
    /**   
     * @Title: testFinish   
     * @Description: 3.善后   
     * @throws Exception      
     * @return: void      
     */
    @Test
    public void testFinish() throws Exception {
        File f = new File("d://data/btreetest/");
        deleteDir(f);
    }
    
    /**   
     * @Title: deleteDir   
     * @Description: 暴力删除文件夹下所有文件和文件夹  
     * @param dir
     * @return: boolean      
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
