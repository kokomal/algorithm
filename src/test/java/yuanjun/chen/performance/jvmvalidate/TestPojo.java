package yuanjun.chen.performance.jvmvalidate;

public class TestPojo {
    static {
        System.out.println("Class TestPojo Was Loaded !");
    }
    protected static final int a = 10; // 编译时常量,用final修饰，调用它不会触发静态块
    protected static final int b = "test".length(); // 运行时常量
    protected int mem = 9; // 私有变量
    
    protected static int k = 15; // 非编译时的常量
    
}
