## 带权值的活动安排问题 
参见普林斯顿大学的教程  & SpecialThanksTo: zhouhao011280s
http://www.cs.princeton.edu/~wayne/cs423/lectures/dynamic-programming-4up.pdf
这问题其实CLRS正文介绍贪心算法时用到的 活动选择问题 的泛化；即能用贪心算法解决的活动选择是该问题的一个特例：收益都是1。  
而这里，收益是各不相同的。于是直观地看，需要用到dp解决。  
而因为活动之间存在兼容问题：j的开始时间有可能与 i 的结束时间或者开始时间冲突，所以对活动j，必须比较选择和不选择带来的收益。  
可以定义一个数组，描述每一个活动 与 其他活动的兼容情况：compatible[ j ] = i 表示 与j 兼容的最大的 i，即 所有 finish [ i ] <= start [ j ]中，i的最大值。
于是 选择j的收益 = j的兼容活动的收益 + j本身的价值，而不选择j的收益 = j前一个活动的兼容活动集合带来的收益。  
即 optimal [ i ] = max { value[i] + optimal [compatible [i] ] , optimal [ i-1 ] }   
