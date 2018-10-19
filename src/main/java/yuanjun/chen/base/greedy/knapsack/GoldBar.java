/**
 * @Title: KnapSack.java
 * @Package: yuanjun.chen.base.greedy.knapsack
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年10月19日 下午3:32:49
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.greedy.knapsack;

/**
 * @ClassName: KnapSack
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: 陈元俊
 * @date: 2018年10月19日 下午3:32:49
 */
public class GoldBar<T extends Comparable<?>> {
    protected T weight;
    protected T value;

    /**
     * @return the weight
     */
    public T getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(T weight) {
        this.weight = weight;
    }

    /**
     * @return the value
     */
    public T getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(T value) {
        this.value = value;
    }
}
