/**  
 * @Title: MyPair.java   
 * @Package: yuanjun.chen.base.common   
 * @Description: 获取最大最小的结构体  
 * @author: 陈元俊     
 * @date: 2018年7月24日 下午12:24:24   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.base.common;

/**   
 * @ClassName: MyPair   
 * @Description: 获取最大最小的结构体  
 * @author: 陈元俊 
 * @date: 2018年7月24日 下午12:24:24  
 */
public class MyPair<T extends Comparable<?>> {
    T max;
    T min;
    public T getMax() {
        return max;
    }
    public void setMax(T max) {
        this.max = max;
    }
    public T getMin() {
        return min;
    }
    public void setMin(T min) {
        this.min = min;
    }
}
