package yuanjun.chen.base.dynamicprogramming.viterbi;

public class WeatherDemo {
    enum Weather {
        Rainy, Sunny,
    }
    enum Activity {
        walk, shop, clean,
    }

    static int[] states = new int[] {Weather.Rainy.ordinal(), Weather.Sunny.ordinal()};
    static int[] observations = new int[] {
            Activity.walk.ordinal(), 
            Activity.shop.ordinal(), 
            Activity.clean.ordinal(),
            Activity.shop.ordinal(), 
            Activity.clean.ordinal(),
            Activity.walk.ordinal(),
            Activity.walk.ordinal(),
            Activity.walk.ordinal(),
            Activity.walk.ordinal(),
    };
    
    /* 初始内部状态Rainy 0.6, Sunny 0.4 */
    static double[] start_probability = new double[] {0.6, 0.4}; 
    
    /*
     * 内部状态转换矩阵
     * { 
     *   'Rainy' : {'Rainy': 0.7, 'Sunny': 0.3}, 
     *   'Sunny' : {'Rainy': 0.4, 'Sunny': 0.6} 
     * }
     **/
    static double[][] transititon_probability = new double[][] {
        {0.7, 0.3}, 
        {0.4, 0.6}
    };
    
    /*
     * 发射转换矩阵
     * { 
     *   'Rainy' : {'walk': 0.1, 'shop': 0.4, 'clean': 0.5}, 
     *   'Sunny' : {'walk': 0.6, 'shop': 0.3, 'clean': 0.1} 
     * }
     **/
    static double[][] emission_probability = new double[][] {
        {0.1, 0.4, 0.5}, 
        {0.6, 0.3, 0.1}
    };

    public static void main(String[] args) {
        int[] result2 =
                ViterbiAlgo.compute(observations, states, start_probability, transititon_probability, emission_probability);
        for (int r : result2) {
            System.out.print(Weather.values()[r] + " ");
        }
    }
}
