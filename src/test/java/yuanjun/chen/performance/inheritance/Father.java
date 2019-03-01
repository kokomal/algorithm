package yuanjun.chen.performance.inheritance;

public class Father {
    int num;

    Father() {
        num = 10;
        System.out.println("A father run");
    }

    Father(int x) {
        System.out.println("B father run..." + x);
    }
}
