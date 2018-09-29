## CLRS-3 15-12 Signing free-agent baseball players签约棒球自由球员问题
原文：  
Suppose that you are the general manager for a major-league baseball team. During the off-season, you need to sign some free-agent players for your team. The team owner has given you a budget of $X to spend on free agents. You are allowed to spend less than $X altogether, but the owner will fire you if you spend any more than $X.
You are considering N different positions, and for each position, P free-agent players who play that position are available. Because you do not want to overload your roster with too many players at any position, for each position you may sign at most one free agent who plays that position. (If you do not sign any players at a particular position, then you plan to stick with the players you already have at that position.)
To determine how valuable a player is going to be, you decide to use a sabermetric statistic known as 'VORP', or 'value over replacement player'. A player with a higher VORP is more valuable than a player with a lower VORP. A player with a higher VORP is not necessarily more expensive to sign than a player with a lower VORP, because factors other than a player's value determine how much it costs to sign him.
For each available free-agent player, you have three pieces of information: the player's position,the amount of money it will cost to sign the player, andthe player's VORP.
Devise an algorithm that maximizes the total VORP of the players you sign while spending no more than $X altogether. You may assume that each player signs for a multiple of 100,000. Your algorithm should output the total VORP of the players you sign, the total amount of money you spend, and a list of which players you sign. Analyze the running time and space requirement of your algorithm.
简易翻译:
假设你是一支棒球大联盟球队的总经理。在赛季休季期间，你需要签入一些自由球员。球队老板给你的预算为X美元，你可以使用少于X美元来签入球员，但如果超支，球队老板就会解雇你。
你正在考虑在N个不同位置签入球员，在每个位置上，有P个该位置的自由球员供你选择。由于你不希望任何位置过于臃肿，因此每个位置最多签入一名球员(如果在某个特定位置上你没有签入任何球员，则意味着计划继续使用现有球员)。
为了确定一名球员的价值，你决定使用一种称为“VORP”，或“球员替换价值”的统计评价指标。球员的VORP值越高，其价值越高。但VORP值高的球员签约费用并不一定比VORP值低的球员高，因为还有球员价值之外的因素影响签约费用。
对于每个可选择的自由球员，你知道他的三方面信息：
1.他打哪个位置。2.他的签约费用。3.他的VORP。
设计一个球员选择算法，是的总签约费用不超过X美元，而球员的总VORP最大。你可以假定每位球员的签约费用是10万美元的整数倍。算法应输出签约球员的总VORP值，总签约费用，以及球员名单。分析算法的时间和空间复杂度。

所有职业以BaseBallPos.java这个枚举的value作为固定值   

解题思路与0/1背包相似，不同之处在于每一个物件都有P个候选方案  
算法在已有基础上扩展了在不变候选人的情况下，动态修改预算总额，来评估各个预算总额下的VORP的最优值  