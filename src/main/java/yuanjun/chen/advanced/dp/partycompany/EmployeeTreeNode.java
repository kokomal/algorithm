package yuanjun.chen.advanced.dp.partycompany;

public class EmployeeTreeNode {
    
    private static final Integer INIT_VALUE = 0;
    
    public String name; // 唯一标识
    
    private EmployeeTreeNode child = null;
    private EmployeeTreeNode sibling = null;
    
    public Integer inPoint = INIT_VALUE; // 此人入场的影响分数
    public Integer outPoint = INIT_VALUE; // 此人不入场的影响分数

    public ENTER_TYPE enterType = ENTER_TYPE.OUT;
    
    public EmployeeTreeNode(String name) {
        this.name = name;
    }  
    
    public void judgeEnter() {
        if (this.inPoint > this.outPoint) {
            this.enterType = ENTER_TYPE.IN;
        } else if (this.inPoint.equals(this.outPoint)) {
            this.enterType = ENTER_TYPE.WHATEVER;
        } else {
            this.enterType = ENTER_TYPE.OUT;
        }
    }
    
    public void judgeEnterBinary() {
        if (this.inPoint >= this.outPoint) {
            this.enterType = ENTER_TYPE.IN;
        } else {
            this.enterType = ENTER_TYPE.OUT;
        }
    }
    
    public EmployeeTreeNode getChild() {
        return child;
    }

    public void setChild(EmployeeTreeNode child) {
        this.child = child;
    }


    public EmployeeTreeNode getSibling() {
        return sibling;
    }


    public void setSibling(EmployeeTreeNode sibling) {
        this.sibling = sibling;
    }
    

}
