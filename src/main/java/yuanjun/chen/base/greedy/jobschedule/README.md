##在单处理器上具有期限和惩罚的单位时间任务调度问题
参见CLRS chapter16.5拟阵matroid相关章节  
任务调度问题就是给定一个有穷单位时间任务的集合S，集合S中的每个任务都有一个截止期限di和超时惩罚wi，  
需要找出集合S的一个调度，使得因任务误期所导致的总惩罚最小，这个调度也称为S的一个最优调度。  
利用拟阵解决任务调度问题的算法原理主要就是将最小化迟任务的惩罚之和问题转化为最大化早任务的惩罚之和的问题  
ai 	1 	2   3   4   5   6   7
di 	4 	2   4   3   1   4   6
wi 70  60  50  40  30  20  10
