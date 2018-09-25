package yuanjun.chen.base.dynamicprogramming.viterbi;

/**
 * @ClassName: ViterbiAlgo
 * @Description: 维特比算法（隐马尔可夫）
 * @author: 陈元俊
 * @SpecialThanksTo: hankcs
 * @date: 2018年9月25日 上午10:37:49
 */
public class ViterbiAlgo {
    /**
     * 求解HMM模型.
     * 
     * @param obs 观测序列
     * @param states 隐状态
     * @param start_p 初始概率（隐状态）
     * @param trans_p 转移概率（隐状态）
     * @param emit_p 发射概率 （隐状态表现为显状态的概率）
     * @return 最可能的序列
     */
    public static int[] compute(int[] obs, int[] states, double[] start_p, double[][] trans_p, double[][] emit_p) {
        double[][] V = new double[obs.length][states.length];
        int[][] path = new int[states.length][obs.length];
        for (int eachState : states) {
            V[0][eachState] = start_p[eachState] * emit_p[eachState][obs[0]];
            path[eachState][0] = eachState;
        }
        for (int t = 1; t < obs.length; ++t) { // 对于纵向每一个ob序列t，递增
            int[][] newPath = new int[states.length][obs.length];
            for (int curState : states) { // 对于t号ob的每一个curstate，寻求最优的maxstate
                double maxProb = -1;
                int maxPreState = 0;
                for (int preState : states) {
                    double candiProb = V[t - 1][preState] * trans_p[preState][curState] * emit_p[curState][obs[t]];
                    if (candiProb > maxProb) {
                        maxProb = candiProb;
                        maxPreState = preState;
                    }
                }
                // 记录最大概率
                V[t][curState] = maxProb;
                /* ★★★精华★★★ 记录路径 最优上一步的路径直接拷贝到新路径*/
                System.arraycopy(path[maxPreState], 0, newPath[curState], 0, t); // 最大的上游maxPreState路径，连接到curstate
                newPath[curState][t] = curState; // 第t个为curstate
            }
            path = newPath;
        }
        double maxProb = -1;
        int optState = 0;
        for (int eachState : states) {
            if (V[obs.length - 1][eachState] > maxProb) {
                maxProb = V[obs.length - 1][eachState];
                optState = eachState;
            }
        }
        return path[optState];
    }
}
