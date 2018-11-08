/**  
 * @Title: BTreeResult.java   
 * @Package: yuanjun.chen.advanced.btree   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年11月6日 下午12:31:30   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.advanced.btree;

/**   
 * @ClassName: BTreeResult   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 陈元俊 
 * @date: 2018年11月6日 下午12:31:30  
 */
public class BTreeResult {
    protected BTreeNode node;
    protected int index;
    public BTreeResult(BTreeNode node, int index) {
        super();
        this.node = node;
        this.index = index;
    }
}
