/**  
 * @Title: FibonacciNode.java   
 * @Package: yuanjun.chen.advanced.datastructure.fibonacciheap   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年11月21日 上午9:20:26   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.advanced.datastructure.fibonacciheap;

import java.util.ArrayList;
import java.util.List;
import yuanjun.chen.base.common.CommonUtils;

/**   
 * @ClassName: FibonacciNode   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 陈元俊 
 * @date: 2018年11月21日 上午9:20:26  
 */
public class FibonacciNode<T extends Comparable<T>> {
    int degree;
    boolean mark;
    FibonacciNode<T> parent;
    List<FibonacciNode<T>> child = new ArrayList<>();
    Integer key;
    T val;
    
    public void addChild(FibonacciNode<T> nd) {
        if (this.child == null) {
            this.child = new ArrayList<>();
            this.child.add(nd);
            return;
        }
        else {
            this.child.add(nd);
        }
    }
    
    public void print() {
        System.out.println("THIS.KEY=" + key + " WITH VAL=" + val);
        System.out.println("CHILDREN NUM=" + child.size());
        for (FibonacciNode<T> chd : child) {
            chd.print();
        }
        System.out.println("FINISH PRINT KEY " + key);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FibonacciNode<?> other = (FibonacciNode<?>) obj;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        return CommonUtils.eq(this.val, other.val); // 只取val和key
    }
}
