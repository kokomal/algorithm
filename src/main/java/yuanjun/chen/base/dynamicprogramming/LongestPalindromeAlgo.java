/**
 * @Title: LongestPalindromeAlgo.java
 * @Package: yuanjun.chen.base.dynamicprogramming
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年9月7日 下午5:24:54
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.dynamicprogramming;

import java.util.Arrays;

/**
 * @ClassName: LongestPalindromeAlgo
 * @Description: 最长回文串的最简单做法是把X逆序后求二者的LCS
 * @author: 陈元俊
 * @date: 2018年9月7日 下午5:24:54
 */
public class LongestPalindromeAlgo {
    public static void main(String[] args) {
        longestPalindromeSubseq("cib"); // c
        longestPalindromeSubseq("biic"); // ii
        longestPalindromeSubseq("civic"); // civic
        longestPalindromeSubseq("beneab"); // beneb
        longestPalindromeSubseq("character"); // carac
        longestPalindromeSubseq("abacdfgdcaba"); // abacdfdcaba
    }

    /**
     * 设字符串为s，f(i,j)表示s[i..j]的最长回文子序列。 最长回文子序列长度为f(0, s.length()-1) 状态转移方程如下： 当i>j时，f(i,j)=0。
     * 当i=j时，f(i,j)=1。 当i<j并且s[i]=s[j]时，f(i,j)=f(i+1,j-1)+2。 当i<j并且s[i]≠s[j]时，f(i,j)=max( f(i,j-1),
     * f(i+1,j) )。 注意如果i+1=j并且s[i]=s[j]时，f(i,j)=f(i+1,j-1)+2=f(j,j-1)+2=2，这就是当i>j时f(i,j)=0的好处。
     */
    public static String longestPalindromeSubseq(String str) { // O(n^2)
        if (str == null || str.length() <= 1) {
            return str;
        }
        System.out.println(Arrays.toString(str.toCharArray()));
        int n = str.length();
        String[] resultStrs = new String[n]; // index+1 is palindrome subseq with length index+1
        resultStrs[0] = String.valueOf(str.charAt(0)); // initialize to first char for length is 1

        int[][] matrix = new int[n][n]; // default value is 0

        for (int j = 0; j < n - 1; j++) {
            matrix[j][j] = 1; // f(j,j)=1; f(j,j-1)=0, same as default value
        }
        int startIndex = 0;
        int endIndex = 0;
        int maxLen = 1;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                // i<j case
                if (str.charAt(i) == str.charAt(j)) {
                    int preLen = matrix[i + 1][j - 1];
                    matrix[i][j] = 2 + preLen;
                    if (maxLen <= matrix[i][j]) {
                        maxLen = matrix[i][j];
                        StringBuilder sb = new StringBuilder();
                        switch (preLen) {
                            case 1:
                                sb.append(str.charAt(i));
                                sb.append(str.charAt(i + 1)); // or j-1
                                break;
                            case 0:
                                sb.append(str.charAt(i));
                                break;
                            default:
                                sb.append(str.charAt(i));
                                sb.append(resultStrs[preLen - 1]);
                                break;
                        }
                        sb.append(str.charAt(j));
                        resultStrs[maxLen - 1] = sb.toString();
                        startIndex = i;
                        endIndex = j;
                    }
                } else {
                    matrix[i][j] = Math.max(matrix[i + 1][j], matrix[i][j - 1]);
                }
            }
        }
        System.out.println("startIndex:" + startIndex + ", endIndex:" + endIndex);
        System.out.println("max length:" + maxLen);
        System.out.println("sub string:" + str.substring(startIndex, endIndex + 1));
        String longestSubSeq = null;
        for (int i = 0; i < n; i++) {
            if (resultStrs[i] != null && resultStrs[i].length() == maxLen) {
                longestSubSeq = resultStrs[i]; // find the longest from n-1
                break;
            }
        }
        System.out.println("longest palindrome subseq:" + longestSubSeq);
        return longestSubSeq;
    }
}
