package yuanjun.chen.performance.weakRef;

/**
 * @ClassName: Car
 * @Description: POJO
 * @author: 陈元俊
 * @date: 2018年9月5日 下午3:37:22
 */
public class Car {
    private double price;
    private String colour;

    public Car(double price, String colour) {
        this.price = price;
        this.colour = colour;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return colour + "car costs $" + price;
    }
}
