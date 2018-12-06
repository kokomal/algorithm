package yuanjun.chen.performance.inheritance;

public class Son extends Father {
    int num;

    Son() {
        // super();//调用的就是父类中的空参数的构造函数。
        // 类似于this() 调用的是本类的构造函数
        System.out.println("C son run" + num);
    }

    Son(int x) {
        this(); // this super二者不能共存
        // super(x); //如果需要调用父类中带参数的构造函数，可以在子类构造函数中定义。
        System.out.println("D son run " + x);
    }
}
