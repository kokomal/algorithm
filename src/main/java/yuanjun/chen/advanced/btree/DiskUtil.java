/**  
 * @Title: DiskUtil.java   
 * @Package: yuanjun.chen.advanced.btree   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年11月7日 上午11:05:31   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.advanced.btree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**   
 * @ClassName: DiskUtil   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 陈元俊 
 * @date: 2018年11月7日 上午11:05:31  
 */
public class DiskUtil {
    // 将来可以个性化
    private static final String PATH = "d://data/btree/";
    
    public static void diskWrite(String tableName, final BTreeNode node) throws Exception {
        node.isLoaded = true;
        BTreeOnePage page = new BTreeOnePage();
        page.setIsLeaf(node.isLeaf);
        page.setKeys(node.keys);
        page.setN(node.n);
        page.setPgNo(node.pageNo);
        List<BTreeNode> children = node.children; // 持久化孩子
        int i = children == null ? 0 : children.size();
        if (i > 0) {
            List<Long> longs = new ArrayList<>();
            for (BTreeNode bt : children) {
                longs.add(bt.pageNo);
            }
            page.children = longs;
        }
        diskWrite(tableName, page);
    }
    
    public static void diskWrite(String tableName, final BTreeOnePage page) throws Exception {
        File file = new File(PATH + tableName + "/"+ page.pgNo + ".txt"); // 数据暂时放在d盘,注意编码格式
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(page.pgNo.toString());
        bw.newLine();
        bw.write(page.isLeaf.toString());
        bw.newLine();
        List<String> keys = page.keys;
        int i = keys == null ? 0 : keys.size(); // 持久化Key
        if (i > 0) {
            bw.write(StringUtils.join(keys.toArray(), ","));
        } else {
            bw.write("");
        }
        bw.newLine();
        List<Long> children = page.children; // 持久化孩子
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
        node.keys = onePage.getKeys();
        node.children = new ArrayList<>();
        if (onePage.getChildren() != null) {
            for (Long x : onePage.getChildren()) {
                node.children.add(new BTreeNode(degree, x, null, null)); // 此时不知道子页的详细信息，只有page页，默认没有加载
            }
        }
        node.isLeaf = onePage.getIsLeaf();
        node.isLoaded = true;
        node.n = onePage.getN();
        return node;
    }
    
    /*根据页面号找到文件，读取里面的数据，并且解析*/
    public static BTreeOnePage fetchByPgNo(String tableName, Long pgNo) {
        BTreeOnePage page = new BTreeOnePage();
        File file = new File(PATH + tableName + "/" + pgNo + ".txt");  // 数据暂时放在d盘,注意编码格式
        BufferedReader reader = null;  
        try {  
            reader = new BufferedReader(new FileReader(file));  
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
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
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
        File file = new File(PATH + tableName + "/META.txt"); // 数据暂时放在d盘,注意编码格式
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
        File file = new File(PATH + tableName + "/META.txt");  // 数据暂时放在d盘,注意编码格式
        if (!file.exists()) { // 不存在！
            System.out.println("TABLE NAME " + tableName + " META file not exists!");
            return null;
        }
        int degree = 0;
        Long rootId = 0L;
        BufferedReader reader = null;  
        try {  
            reader = new BufferedReader(new FileReader(file));  
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
            page.dgr = degree; // 把度传回来
            return page;
        } catch (Exception e) {  
            System.out.println("WRONG READ META" + e);
            return null;
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }  
    }
    
    public static void main(String[] args) throws Exception {
        diskWriteMeta("zz", 2, 5666L);
        
        BTreeOnePage page = diskReadMeta("t_example");
        System.out.println(page);
    }
}
