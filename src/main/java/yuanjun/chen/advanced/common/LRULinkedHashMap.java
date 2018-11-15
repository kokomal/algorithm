/**  
 * @Title: LRULinkedHashMap.java   
 * @Package: yuanjun.chen.advanced.common   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年11月15日 下午1:42:18   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.advanced.common;

import java.util.LinkedHashMap;

/**   
 * @ClassName: LRULinkedHashMap   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 陈元俊 
 * @date: 2018年11月15日 下午1:42:18  
 */
public class LRULinkedHashMap<K,V> extends LinkedHashMap<K, V> {

    private static final long serialVersionUID = 2676377988663444186L;

    @Override
    protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
        return this.size() > GlobalConfig.MAX_PAGES;
    }

}
