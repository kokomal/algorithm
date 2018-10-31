## N-PUZZLE问题
激难！  
采用IDA*算法，即递归加深的A*算法  
逐步叠加递归深度，不可避免的有重复计算，并且似乎无法进行缓存  
注意：
1.N-PUZZLE问题是否可解可以参考@https://blog.csdn.net/u010398265/article/details/50987577 的中文介绍；
2.求解逆序对可以参考归并排序的相关链接@https://www.cnblogs.com/liuzhen1995/p/6511622.html
3.X,Y轴位置和2D数组是比较别扭的映射关系  
4.似乎可以考虑cantor映射将序列进行hash，然后进行cache，这样省的每次都去遍历，对此采用了带缓存的阶乘FactUtil，但似乎cantor映射效果不佳    

5.NPuzzleEasyAlgo采用了更简洁高效的内部1维数组取代NPuzzleAlgo的2维数组，性能有了近40%的提升  
6.总共花在manhattan的时间比较惊人  