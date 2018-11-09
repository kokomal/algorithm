/**
 * @Title: BTreeLiteTest.java
 * @Package: yuanjun.chen.advanced.btree
 * @Description: 简化版B树的测试
 * @author: 陈元俊
 * @date: 2018年11月9日 上午9:04:54
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.advanced.btreelite;

import java.io.File;
import org.junit.Test;
import yuanjun.chen.advanced.common.GlobalConfig;

/**
 * @ClassName: BTreeLiteTest
 * @Description: 简化版B树的测试
 * @author: 陈元俊
 * @date: 2018年11月9日 上午9:04:54
 */
public class BTreeLiteTest {

    private static final String D_DATA_BTREETEST = "d://data/btreetest/";

    /**   
     * @Title: testPersist   
     * @Description: 1.测试持久化到文件  
     * @throws Exception      
     * @return: void      
     */
    @Test
    public void testPersist() throws Exception {
        GlobalConfig.BTREELITE_PATH = D_DATA_BTREETEST;
        BTreeHolderLite holder = new BTreeHolderLite();
        holder.init("t_examplex", 3);
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
        GlobalConfig.BTREELITE_PATH = D_DATA_BTREETEST;
        BTreeHolderLite holder = new BTreeHolderLite();
        holder.rebuild("t_examplex");
        holder.reportFull(holder.root);
    }
    
    /**   
     * @Title: testFinish   
     * @Description: 3.善后   
     * @throws Exception      
     * @return: void      
     */
    @Test
    public void testFinish() throws Exception {
        File f = new File(D_DATA_BTREETEST);
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
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
