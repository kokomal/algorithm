package yuanjun.chen.base.common;

public class DispUtil {
    public static void split(int len, char x) {
        for (int i = 0; i < len; i++) {
            System.out.print(x);
        }
        System.out.println();
    }

    public static void embed(int len, char x, String word) {
        for (int i = 0; i < len; i++) {
            System.out.print(x);
        }
        System.out.print(word);
        for (int i = 0; i < len; i++) {
            System.out.print(x);
        }
        System.out.println();
    }

}
