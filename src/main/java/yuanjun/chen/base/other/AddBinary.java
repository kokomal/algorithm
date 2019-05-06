/**  
 * @Title: AddBinary.java   
 * @Package: yuanjun.chen.base.other   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2019年5月6日 上午10:27:13   
 * @version V1.0 
 * @Copyright: 2019 All rights reserved. 
 */
package yuanjun.chen.base.other;

/**   
 * @ClassName: AddBinary   
 * @Description: 给定两个二进制字符串，返回他们的和（用二进制表示）。
        输入为非空字符串且只包含数字 1 和 0。
        示例 1:
        输入: a = "11", b = "1"
        输出: "100"
        示例 2:
        输入: a = "1010", b = "1011"
        输出: "10101" 
 * @author: 陈元俊 
 * @date: 2019年5月6日 上午10:27:13  
 */
public class AddBinary {
    public static String addBinary(String a, String b) {
        char[] a1 = a.toCharArray();
        char[] b1 = b.toCharArray();
        reverse(a1);
        reverse(b1);
        int carry = 0;
        StringBuilder sb = new StringBuilder();
        int cur = 0;
        do {
            char x = '0';
            char y = '0';
            if (cur < a1.length) {
                x = a1[cur];
            }
            if (cur < b1.length) {
                y = b1[cur];
            }
            if (carry == 0) {
                if (x == y) {
                    carry = judgeByX(x);
                    sb.append('0');
                } else {
                    sb.append('1');
                }
            } else if (x == y) {
                carry = judgeByX(x);
                sb.append('1');
            } else {
                sb.append('0');
            }
            cur++;
            if (carry == 0 && x == '0' && y == '0' && cur >= Math.max(a.length(), b.length())) {
                break;
            }
        } while (true);
        String res = sb.reverse().toString();
        if (res.startsWith("0") && res.length() > 1) {
            return res.substring(1);
        }
        return res;
    }

    private static int judgeByX(char x) {
        return  x == '1' ? 1 : 0;
    }

    private static void reverse(char[] x) {
        int mid = (x.length - 1) / 2;
        for (int i = 0; i <= mid; i++) {
            char tmp = x[i];
            x[i] = x[x.length - i - 1];
            x[x.length - i - 1] = tmp;
        }
    }
        public static void main(String[] args) {
        String a = "1100011";
        String b = "10011";
        System.out.println(addBinary(a,b));
    }
}
