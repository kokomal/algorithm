/**
 * @Title: CountSmaller.java
 * @Package: yuanjun.chen.base.other
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2019年5月6日 下午3:22:17
 * @version V1.0
 * @Copyright: 2019 All rights reserved.
 */
package yuanjun.chen.base.other;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: CountSmaller
 * @Description: LEETCODE 315 slow way
 * @author: 陈元俊
 * @date: 2019年5月6日 下午3:22:17
 */
public class CountSmaller {
    public static List<Integer> countSmaller(int[] nums) {
        int size = nums.length;
        int[] price = new int[size];
        for (int i = size - 2; i >= 0; i--) {
            int count = 0;
            for (int j = i + 1; j < size; j++) {
                if (nums[j] < nums[i]) {
                    count++;
                } else if (nums[j] == nums[i]) {
                    count += price[j];
                    break;
                }
            }
            price[i] = count;
        }
        List<Integer> lis = new ArrayList<>();
        for (int r : price) {
            lis.add(r);
        }
        return lis;
    }

    public static void main(String[] args) {
        System.out.println(countSmaller(new int[] {5, 2, 6, 1}));
    }
}
