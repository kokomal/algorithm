/**  
 * @Title: DiskUtil.java   
 * @Package: yuanjun.chen.advanced.btree   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年11月7日 上午11:05:31   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.advanced.datastructure.oldbtree;

import org.apache.commons.lang.StringUtils;
import yuanjun.chen.advanced.datastructure.common.BTreeOnePage;
import yuanjun.chen.advanced.datastructure.common.GlobalConfig;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**   
 * @ClassName: DiskUtil   
 * @author: 陈元俊
 * @date: 2018年11月7日 上午11:05:31  
 */
public class DiskUtil {
    public static void diskWrite(String tableName, final BTreeNode node) throws Exception {
        node.setIsLoaded(true);
        BTreeOnePage page = new BTreeOnePage();
        page.setIsLeaf(node.getIsLeaf());
        page.setKeys(node.getKeys());
        page.setN(node.getN());
        page.setPgNo(node.getPageNo());
        List<BTreeNode> children = node.getChildren(); // 持久化孩子
        int i = children == null ? 0 : children.size();
        if (i > 0) {
            List<Long> longs = new ArrayList<>();
            for (BTreeNode bt : children) {
                longs.add(bt.getPageNo());
            }
            page.setChildren(longs);
        }
        diskWrite(tableName, page);
    }
    
    public static void diskWrite(String tableName, final BTreeOnePage page) throws Exception {
        File file = new File(GlobalConfig.BTREE_PATH + tableName + "/"+ page.getPgNo() + ".txt"); // 数据暂时放在d盘,注意编码格式
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(page.getPgNo().toString());
        bw.newLine();
        bw.write(page.getIsLeaf().toString());
        bw.newLine();
        List<String> keys = page.getKeys();
        int i = keys == null ? 0 : keys.size(); // 持久化Key
        if (i > 0) {
            bw.write(StringUtils.join(keys.toArray(), ","));
        } else {
            bw.write("");
        }
        bw.newLine();
        List<Long> children = page.getChildren(); // 持久化孩子
        i = children == null ? 0 : children.size();
        if (i > 0) {
            bw.write(StringUtils.join(children.toArray(), ","));
        } else {
            bw.write("");
        }
        bw.newLine();
        bw.flush();
        bw.close();
    }
    
    // 分为深拷贝深入读取和浅拷贝读取
    public static BTreeNode diskRead (String tableName, int degree, Long pgNo) {
        BTreeOnePage onePage = fetchByPgNo(tableName, pgNo); // 没有读到需要抛出异常
        // 此时node只有一个pageNo，isLeaf,keys和children都没有，需要从硬盘里面加载
        BTreeNode node = new BTreeNode(degree, pgNo, onePage.getIsLeaf(), onePage.getN());
        /*
         * 磁盘io
         * 此时文件里面可能是这样的：
         * ---------------------------------------------------------
         * pgNo: 100232
         * isLeaf: false
         * keys: 12,20,33,44,55
         * children: 100333, 100334, 100336, 100337, 100455, 100467
         * ---------------------------------------------------------
         * 那么，n = 5, children可以推6个pgNo进入，keys直接转换导入
         * */
        node.setKeys(onePage.getKeys());
        node.setChildren(new ArrayList<>());
        if (onePage.getChildren() != null) {
            for (Long x : onePage.getChildren()) {
                node.getChildren().add(new BTreeNode(degree, x, null, null)); // 此时不知道子页的详细信息，只有page页，默认没有加载
            }
        }
        node.setIsLeaf(onePage.getIsLeaf());
        node.setIsLoaded(true);
        node.setN(onePage.getN());
        return node;
    }
    
    /*根据页面号找到文件，读取里面的数据，并且解析*/
    public static BTreeOnePage fetchByPgNo(String tableName, Long pgNo) {
        BTreeOnePage page = new BTreeOnePage();
        File file = new File(GlobalConfig.BTREE_PATH + tableName + "/" + pgNo + ".txt");  // 数据暂时放在d盘,注意编码格式
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String tempString = reader.readLine();
            long x = Long.parseLong(tempString);
            page.setPgNo(x);
            tempString = reader.readLine().trim();
            page.setIsLeaf(tempString.equalsIgnoreCase("true"));
            tempString = reader.readLine().trim();
            page.setRawKeys(tempString);
            tempString = reader.readLine().trim();
            page.setRawChildren(tempString);
            reader.close();
            page.parse();
        } catch (Exception e) {
            System.out.println("WRONG FETCH PAGENO " + e);
        }
        return page;
    }

    /*
     * META.txt类似于
     * DGR
     * 2
     * TABLE NAME
     * t_xxx
     * TABLE ENTRY
     * 23213
     * 
     * */
    public static void diskWriteMeta(String tableName, Integer degree, Long pageNo) throws Exception {
        File file = new File(GlobalConfig.BTREE_PATH + tableName + "/META.txt"); // 数据暂时放在d盘,注意编码格式
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("DGR");
        bw.newLine();
        bw.write(degree.toString());
        bw.newLine();
        bw.write("TABLE-NAME");
        bw.newLine();
        bw.write(tableName);
        bw.newLine();
        bw.write("TABLE-ENTRY");
        bw.newLine();
        bw.write(pageNo.toString());
        bw.flush();
        bw.close();
    }
    
    public static BTreeOnePage diskReadMeta(String tableName) {
        File file = new File(GlobalConfig.BTREE_PATH + tableName + "/META.txt");  // 数据暂时放在d盘,注意编码格式
        if (!file.exists()) { // 不存在！
            System.out.println("TABLE NAME " + tableName + " META file not exists!");
            return null;
        }
        int degree = 0;
        Long rootId = 0L;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String tempString = reader.readLine();
            if ("DGR".equalsIgnoreCase(tempString)) {
                tempString = reader.readLine().trim();
                degree = Integer.parseInt(tempString);
            }
            tempString = reader.readLine().trim();
            if ("TABLE-NAME".equalsIgnoreCase(tempString)) {
                tempString = reader.readLine().trim();
                if (!tableName.equals(tempString)) {
                    System.out.println("TABLE NAME should be " + tableName + " but META is " + tempString);
                    return null;
                }
            }
            tempString = reader.readLine().trim();
            if ("TABLE-ENTRY".equalsIgnoreCase(tempString)) {
                tempString = reader.readLine().trim();
                System.out.println("ROOTID-" + tempString);
                rootId = Long.parseLong(tempString);
            }
            reader.close();
            BTreeOnePage page = fetchByPgNo(tableName, rootId);
            page.setDgr(degree); // 把度传回来
            return page;
        } catch (Exception e) {
            System.out.println("WRONG READ META" + e);
            return null;
        }
    }
    
    public static void main(String[] args) throws Exception {
        diskWriteMeta("zz", 2, 5666L);
        
        BTreeOnePage page = diskReadMeta("t_example");
        System.out.println(page);
    }
}
