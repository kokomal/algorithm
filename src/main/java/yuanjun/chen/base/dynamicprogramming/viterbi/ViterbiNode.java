/**  
 * @Title: ViterbiNode.java   
 * @Package: yuanjun.chen.base.dynamicprogramming.viterbi   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年9月21日 上午10:45:42   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.base.dynamicprogramming.viterbi;

/**   
 * @ClassName: ViterbiNode   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 陈元俊 
 * @date: 2018年9月21日 上午10:45:42  
 */
public class ViterbiNode {
    
    protected ViterbiNode last = null; // 指向前置节点的指针
    
    protected Double curProbWeight; // 当前概率权重[0,1]
    
    protected int level; // 当前level，从1开始，FIRST--->1...n--->END

    public ViterbiNode getLast() {
        return last;
    }

    public void setLast(ViterbiNode last) {
        this.last = last;
    }

    public Double getCurProbWeight() {
        return curProbWeight;
    }

    public void setCurProbWeight(Double curProbWeight) {
        this.curProbWeight = curProbWeight;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ViterbiNode(ViterbiNode last, Double curProbWeight, int level) {
        super();
        this.last = last;
        this.curProbWeight = curProbWeight;
        this.level = level;
    }
    
}
