/**
 * @Title: AM.java
 * @Package: yuanjun.chen.game.aifun
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2019年2月13日 下午1:56:59
 * @version V1.0
 * @Copyright: 2019 All rights reserved.
 */
package yuanjun.chen.game.aifun;

/**
 * @ClassName: AM
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: 陈元俊
 * @date: 2019年2月13日 下午1:56:59
 */
public class AM {
    public static void main(String[] args) {
        int aaa = 999;
        int bbb = 888;
        System.out.println(aaa + " to bin is " + tenToTwo(aaa));
        System.out.println(bbb + " to bin is " + tenToTwo(bbb));
        String res = binAdd(tenToTwo(aaa), tenToTwo(bbb));
        System.out.println(res);

        int res2 = Integer.parseInt(res, 2);

        System.out.printf("%d + %d == %d", aaa, bbb, res2);
    }

    public static String tenToTwo(int data) {
        if (data == 1 || data == 0) {
            return data == 1 ? "1" : "0";
        } else {
            return tenToTwo(data / 2) + tenToTwo(data % 2);
        }
    }

    public static String binAdd(String a, String b) {
        StringBuilder ret = new StringBuilder();

        String longer = a.length() > b.length() ? a : b;
        String shorter = a.length() > b.length() ? b : a;
        longer = new StringBuilder(longer).reverse().toString();
        shorter = new StringBuilder(shorter).reverse().toString();
        System.out.println(longer + "--" + shorter);
        int cur = 0;
        int carry = 0;
        do {
            if (cur == longer.length()) {
                if (carry == 1) {
                    ret.append('1');
                }
                break;
            } else if (cur < shorter.length()) {
                char aa = longer.charAt(cur);
                char bb = shorter.charAt(cur);
                if (aa != bb) {
                    ret.append(carry == 1 ? '0' : '1');
                } else if (aa == '0') {
                    ret.append(carry == 0 ? '0' : '1');
                    carry = 0;
                } else {
                    ret.append(carry == 0 ? '0' : '1');
                    carry = 1;
                }
            } else {
                char aa = longer.charAt(cur);
                if (carry == 1) {
                    ret.append(aa == '1' ? '0' : '1');
                    if (aa == '1') {
                        carry = 1;
                    }
                } else {
                    ret.append(aa);
                }
            }
            cur++;
        } while (true);

        return ret.reverse().toString();
    }
}
