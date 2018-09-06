/**
 * @Title: WeakRefernceTest.java
 * @Package: yuanjun.chen.performance.weakRef
 * @Description: 弱引用测试
 * @author: 陈元俊
 * @date: 2018年9月5日 下午3:37:44
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.performance.weakRef;

import java.lang.ref.WeakReference;
import org.junit.Test;

/**
 * @ClassName: WeakRefernceTest
 * @Description: 弱引用测试
 * @author: 陈元俊
 * @date: 2018年9月5日 下午3:37:44
 */
public class WeakRefernceTest {
    @Test
    public void testWeak001() {
        Car car = new Car(22000, "silver"); // car采用逃逸分析可以断定已死
        WeakReference<Car> weakCar = new WeakReference<Car>(car);
        int i = 0;
        do {
            if (weakCar.get() != null) {
                i++;
                System.out.println("Object is alive for " + i + " loops - " + weakCar);
            } else {
                System.out.println("Object has been collected.");
                break;
            }
        } while (true);
    }

    @Test
    public void testWeak002() throws Exception {
        Car car = new Car(22000, "silver");
        WeakReference<Car> weakCar = new WeakReference<Car>(car); // car一直活得很好
        int i = 0;
        do {
            System.out.println("here is the strong reference 'car' " + car); // 阴魂不散
            if (weakCar.get() != null) {
                i++;
                System.out.println("Object is alive for " + i + " loops - " + weakCar);
            } else {
                System.out.println("Object has been collected.");
                break;
            }
            if (i >= 18888) {
                System.out.println("------------begin GC!!!----------------"); // 即便手动GC，岿然不动
                System.gc();
            }
            if (i == 20000) {
                break;
            }
        } while (true);
        System.out.println(weakCar.get() + " is still alive");
        car = null; // 显示将强引用杀死
        System.gc(); // 手动gc
        System.out.println("weakCar is " + weakCar.get()); // null
    }
}
