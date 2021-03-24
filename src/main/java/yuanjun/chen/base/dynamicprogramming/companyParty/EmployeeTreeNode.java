/**  
 * @Title: EmployeeTreeNode.java   
 * @Package: yuanjun.chen.base.dynamicprogramming.companyParty   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年9月14日 上午8:46:31   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.base.dynamicprogramming.companyParty;

/**   
 * @ClassName: EmployeeTreeNode   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 陈元俊 
 * @date: 2018年9月14日 上午8:46:31  
 */
public class EmployeeTreeNode {
    public static enum ENTER_TYPE{
        IN, NOTIN, WHATEVER
    }
    
    private static final Integer INIT_VALUE = Integer.MIN_VALUE;
    
    protected String name;
    @Deprecated
    protected EmployeeTreeNode parent = null; // 似乎没有用武之地
    private EmployeeTreeNode child = null;
    private EmployeeTreeNode sibling = null;
    
    protected Integer InPoint = INIT_VALUE; // 此人入场的影响分数
    protected Integer NotInPoint = INIT_VALUE; // 此人入场的影响分数

    protected ENTER_TYPE enterType;
    
    public EmployeeTreeNode(String name) {
        super();
        this.name = name;
    }  
    
    public void judgeEnter() {
        if (InPoint > NotInPoint) {
            enterType = ENTER_TYPE.IN;
        } else if (InPoint.equals(NotInPoint)) {
            enterType = ENTER_TYPE.WHATEVER;
        } else {
            enterType = ENTER_TYPE.NOTIN;
        }
    }

    /**
     * @return the child
     */
    public EmployeeTreeNode getChild() {
        return child;
    }

    /**
     * @param child the child to set
     */
    public void setChild(EmployeeTreeNode child) {
        this.child = child;
    }

    /**
     * @return the sibling
     */
    public EmployeeTreeNode getSibling() {
        return sibling;
    }

    /**
     * @param sibling the sibling to set
     */
    public void setSibling(EmployeeTreeNode sibling) {
        this.sibling = sibling;
    }
}
