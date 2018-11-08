/**  
 * @Title: GlobalPageNoGen.java   
 * @Package: yuanjun.chen.advanced.btree   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年11月6日 下午5:11:25   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.advanced.btree;

import java.util.Random;

/**   
 * @ClassName: GlobalPageNoGen   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 陈元俊 
 * @date: 2018年11月6日 下午5:11:25  
 */
public class GlobalPageNoGen {
    private static Random rd = new Random();
    public static Long genNextPageNo() {
        return 100 * System.currentTimeMillis() + rd.nextInt(100);
    }
    
    public static void main(String[] args) {
        for (int i = 0; i < 9; i++) {
            System.out.println(genNextPageNo());
        }
    }
}
