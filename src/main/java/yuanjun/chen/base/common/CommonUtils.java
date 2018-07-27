/**  
 * @Title: CommonUtils.java   
 * @Package: yuanjun.chen.base.common   
 * @Description: 通用util   
 * @author: 陈元俊     
 * @date: 2018年7月24日 下午5:51:31   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.base.common;

/**   
 * @ClassName: CommonUtils   
 * @Description: 通用util  
 * @author: 陈元俊 
 * @date: 2018年7月24日 下午5:51:31  
 */
public class CommonUtils {

    /**   
     * @Title: less   
     * @Description: 比较  
     * @param: A
     * @param: B
     * @return: boolean      
     * @throws   
     */
    @SuppressWarnings("unchecked")
    public static <T> boolean less(Comparable<T> A, Comparable<T> B) {
        return A.compareTo((T) B) < 0;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> boolean eq(Comparable<T> A, Comparable<T> B) {
        return A.compareTo((T) B) == 0;
    }

    @SuppressWarnings("unchecked")
    public static <T> boolean lesseq(Comparable<T> A, Comparable<T> B) {
        return A.compareTo((T) B) <= 0;
    }
    
    /**   
     * @Title: more   
     * @Description: 比较  
     * @param: A
     * @param: B
     * @return: boolean      
     * @throws   
     */
    @SuppressWarnings("unchecked")
    public static <T> boolean more(Comparable<T> A, Comparable<T> B) {
        return A.compareTo((T) B) > 0;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> boolean moreeq(Comparable<T> A, Comparable<T> B) {
        return A.compareTo((T) B) >= 0;
    }
}
