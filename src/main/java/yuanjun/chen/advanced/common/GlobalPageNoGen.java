/**  
 * @Title: GlobalPageNoGen.java   
 * @Package: yuanjun.chen.advanced.btree   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年11月6日 下午5:11:25   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.advanced.common;

import java.util.Random;

/**   
 * @ClassName: GlobalPageNoGen   
 * @Description: 全局id生成器，采用snowflake生成long类型的大致与时间正相关的序列  
 * @author: 陈元俊 
 * @date: 2018年11月6日 下午5:11:25  
 */
public class GlobalPageNoGen {
    private static Random rd = new Random();
    private static SnowflakeIdWorker idWorker;
    static {
        int dataCenterId = 0;
        int machineId = 0; // 将来此二配置均可在服务器内进行手动个性化配置
        idWorker = new SnowflakeIdWorker(dataCenterId, machineId); 
    }
    
    public static Long genNextPageNo() {
        // return 100 * System.currentTimeMillis() + rd.nextInt(100);
        return idWorker.nextId();
    }
    
    public static void main(String[] args) {
        for (int i = 0; i < 9; i++) {
            System.out.println(genNextPageNo());
        }
    }
}
