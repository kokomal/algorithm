/**  
 * @Title: MinPathSum.java   
 * @Package: yuanjun.chen.base.other   
 * @Description: LEETCODE 64  
 * @author: 陈元俊     
 * @date: 2019年5月6日 上午9:52:12   
 * @version V1.0 
 * @Copyright: 2019 All rights reserved. 
 */
package yuanjun.chen.base.other;

import java.util.Arrays;

/**   
 * @ClassName: MinPathSum   
 * @Description: 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
        说明：每次只能向下或者向右移动一步。
        示例:
        输入:
        [
          [1,3,1],
          [1,5,1],
          [4,2,1]
        ]
        输出: 7
        解释: 因为路径 1→3→1→1→1 的总和最小。 
 * @author: 陈元俊 
 * @date: 2019年5月6日 上午9:52:12  
 */
public class MinPathSum {
    public static int minPathSum(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int[][] price = new int[row][col];
        int vertical = 0;
        for (int i = 0; i < col; i++) {
            vertical += grid[0][i];
            price[0][i] = vertical;
        }
        int horizon = 0;
        for (int i = 0; i < row; i++) {
            horizon += grid[i][0];
            price[i][0] = horizon;
        }
        // dispMatrix(price);
        for (int rowIdx = 1; rowIdx < row; rowIdx++) {
            for (int colIdx = 1; colIdx < col; colIdx++) {
                price[rowIdx][colIdx] =
                        grid[rowIdx][colIdx] + Math.min(price[rowIdx][colIdx - 1], price[rowIdx - 1][colIdx]);
            }
        }
        // dispMatrix(price);
        return price[row - 1][col - 1];
    }

    private static void dispMatrix(int[][] price) {
        int len = price.length;
        for (int i = 0; i < len; i++) {
            System.out.println(Arrays.toString(price[i]));
        }
    }
    
    public static void main(String[] args) {
        int[][] grid = {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}};
        System.out.println("--------------RAW INPUT IS--------------");
        dispMatrix(grid);
        System.out.println("--------------PRICE TABLE IS--------------");
        System.out.println(minPathSum(grid));
    }
}
