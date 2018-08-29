package yuanjun.chen.base.dynamicprogramming;

/**
 * 传说中的LCS
 *
 * @SpecialThanksTo fjssharpsword
 */
public class LongestCommonSubsequenceAlgo {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String str1 = "ACCGGTCGAGTGCGCGGAAGCCGGCCGAA";
        String str2 = "GTCGTTCGGAATGCCGTTGCTCTGTAAA";
        //计算lcs递归矩阵
        int[][] re = longestCommonSubsequence(str1, str2);

        System.out.println();
        //打印矩阵
        for (int i = 0; i <= str1.length(); i++) {
            for (int j = 0; j <= str2.length(); j++) {
                System.out.printf("%6d", re[i][j]);
            }
            System.out.println();
        }

        System.out.println();
        //输出LCS
        print(re, str1, str2, str1.length(), str2.length());
    }

    // 假如返回两个字符串的最长公共子序列的长度
    public static int[][] longestCommonSubsequence(String str1, String str2) {
        int[][] matrix = new int[str1.length() + 1][str2.length() + 1];//建立二维矩阵
        // 初始化边界条件
        for (int i = 0; i <= str1.length(); i++) {
            matrix[i][0] = 0;//每行第一列置零
        }
        for (int j = 0; j <= str2.length(); j++) {
            matrix[0][j] = 0;//每列第一行置零
        }
        // 填充矩阵
        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;
                } else {
                    matrix[i][j] = (matrix[i - 1][j] >= matrix[i][j - 1] ? matrix[i - 1][j]
                            : matrix[i][j - 1]);
                }
            }
        }
        return matrix;
    }

    //根据矩阵输出LCS
    public static void print(int[][] opt, String X, String Y, int i, int j) {
        if (i == 0 || j == 0) {
            return;
        }
        if (X.charAt(i - 1) == Y.charAt(j - 1)) {
            print(opt, X, Y, i - 1, j - 1);
            System.out.print(X.charAt(i - 1));
        } else if (opt[i - 1][j] >= opt[i][j]) {
            print(opt, X, Y, i - 1, j);
        } else {
            print(opt, X, Y, i, j - 1);
        }
    }


}
