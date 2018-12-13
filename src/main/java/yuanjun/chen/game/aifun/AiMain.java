/**  
 * @Title: AiMain.java   
 * @Package: yuanjun.chen.game.aifun   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年12月13日 下午6:10:47   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.game.aifun;

import java.util.Scanner;

/**   
 * @ClassName: AiMain   
 * @Description: AI核心代码，估值一个亿   
 * @author: 陈元俊 
 * @date: 2018年12月13日 下午6:10:47  
 */
public class AiMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str;
        while (true) {
            str = sc.next();
            str = str.replace("吗", "");
            str = str.replace("?", "!");
            str = str.replace("？", "！");
            System.out.println(str);
        }
    }
}
