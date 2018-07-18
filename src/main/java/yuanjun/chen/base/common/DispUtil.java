package yuanjun.chen.base.common;

import java.util.Arrays;

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

    public static void splitOne(int len, char x) {
        for (int i = 0; i < len; i++) {
            System.out.print(x);
        }
    }
    
    public static void showMatrix(Integer[][] matrix) {
        for (Integer[] line : matrix) {
            System.out.print("|" + line[0]);
            for (int i = 1; i < line.length; i++) {
                System.out.print("," + line[i]);
            }
            System.out.print("|");
            System.out.println();
        }
    }
}
