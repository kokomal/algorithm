/**
 * @Title: GoldBar.java
 * @Package: yuanjun.chen.base.greedy.knapsack
 * @Description: 金条pojo，重量和价值为其2个属性
 * @author: 陈元俊
 * @date: 2018年10月19日 下午3:32:49
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.greedy.knapsack;

/**
 * @ClassName: GoldBar
 * @Description: 金条pojo，重量和价值为其2个属性
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
