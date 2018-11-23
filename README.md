# algorithm
本工程涵盖基本算法和容器操作基础 ，部分算法来源于CLRS算法导论相关章节，在此向CLRS等诸位先驱致敬！  
---
## advanced包相关：
为先进算法和数据结构相关内容，包含  
1.b-tree的实现，以及文本数据库的简易实现  
2.斐波那契堆的实现  
ods包为开源数据库的源码，在此感谢Pat Morin  

---

## sort包相关:  
根据排序算法的特性，可以分为比较排序（例如冒泡、堆排序、插入排序、归并排序、快速排序、选择排序、希尔排序等）和非比较排序（例如计数排序、基数排序和桶排序）  
不同的排序，对数据类型和幅值的要求不一，因此应用场合也各有不同  

## GenericAlgoTester.java
通用测试类，支持多种数据类型，支持2份相同数组的排序比较（与JDK自带J.U.A的Arrays.sort算法进行对比）  
子类需要重载showtime方法来实现具体的排序

## yuanjun.chen.base.sort.BubbleSortAlgo.java
冒泡排序实现类，yuanjun.chen.base.sort.BubbleSortTest.java实现相应测试

## yuanjun.chen.base.sort.HeapSortAlgo.java
堆排序实现类，yuanjun.chen.base.sort.HeapSortTest.java实现相应测试  
堆排序支持递归堆整理方法，和非递归整理方法，二者区别在于MAX_HEAPIFY的过程中采用的方式是否为递归  

## yuanjun.chen.base.sort.InsertionSortAlgo.java
插入排序实现类，yuanjun.chen.base.sort.InsertionSortTest.java实现相应测试  
插入排序支持线性搜索插入算法，和改进的二分查找搜索插入算法，二者区别在于，前者是逐一线性的逆序查找插入点  
而后者是采用二分法进行插入点的搜索  

## yuanjun.chen.base.sort.MergeSortAlgo.java
归并排序实现类，yuanjun.chen.base.sort.MergeSortTest.java实现相应测试  
归并排序是经典的分治算法，只是额外需要O[n]的内存空间，本算法在merge中申请某一区间的数组空间temp，排序好之后，将temp复制到指定的原始数组里面  

## yuanjun.chen.base.sort.HeapBasedPriorityQueue.java
基于堆的优先级队列实现，yuanjun.chen.base.sort.HeapBasedPriorityQueueTest.java实现相应测试  
优先级队列参考了HeapSortAlgo类的堆整理和插入、删除及升级/降级操作   

## yuanjun.chen.base.sort.QuickSortAlgo.java
快速排序实现，yuanjun.chen.base.sort.QuickSortTest.java实现相应测试  

## yuanjun.chen.base.sort.SelectionSortAlgo.java
选择排序实现，yuanjun.chen.base.sort.SelectionSortTest.java实现相应测试    

## yuanjun.chen.base.sort.ShellSortAlgo.java
希尔排序实现，yuanjun.chen.base.sort.ShellSortTest.java实现相应测试(TODO)  
希尔排序思想是按照h对数据进行分类插入排序后，然后逐次削减h，最终h退化为1，从而将大致有序的数组进行最后一次插入排序  
Knuth对希尔排序的h选择和削减提出了行之有效的优化（3倍递减法）   

## yuanjun.chen.base.sort.CountingSortAlgo.java
计数排序实现，yuanjun.chen.base.sort.CountingSortTest.java实现相应测试  
计数排序适用于元素区间远远小于元素个数的场景，采用了额外的线性空间，换取时间复杂度为O[n]，空间复杂度为O[n+m]其中n为数组size，m为数组的幅值    
如果元素的幅值比较大，那么此算法将耗费比较大的额外空间  
相对于桶排序，计数排序似乎更"喜欢"重复的key，其对键冲突不敏感

## yuanjun.chen.base.sort.RadixSortAlgo.java
基数排序实现，yuanjun.chen.base.sort.RadixSortTest.java实现相应测试  
基数排序的原理，为从低到高逐次比较各个元素的各个位，进行计数排序。基数排序从低位到高位进行，使得最后一次计数排序完成后，数组有序。但是如果先排序高位，会导致稳定性的破坏。

## yuanjun.chen.base.sort.BucketSortAlgo.java
桶排序实现，  yuanjun.chen.base.sort.BucketSortTest.java实现相应测试  
桶排序是经典的空间换时间的排序方法，采用类似“列表拉链数组”的方式，将待排元素划分成若干的桶，每个桶存放各个元素的节点
如果桶冲突，则执行链表插入排序，最后组装所有的桶将数据输出。链表方式的插入排序，好处是每一次的插入新元素，只涉及简单的链表节点移动和插入，不会像数组实现的插入排序那样每次插入都会导致大规模内存拷贝复制，其对内存操作比较友好，缺点就是需要额外对原始数据进行包装（转换成Node），内存占用比较大，并且不支持插入排序的二分优化改造  
桶排序的性能，比较依赖于数据来源的特点，和桶映射的方法  
如果输入数据方差比较小，那么可能会导致桶的宽度没有用上，此时桶排序退化成链表形式的插入排序  
桶排序的难点是对链表进行插入排序，参见leetcode#147，需要构造伪头节点进行遍历和插入   

---
## find包相关:  
此包实现基本的顺序序列查找算法  
## yuanjun.chen.base.find.FindAlgo.java
此类实现在序列中单独查找min和max的方法，时间为O[n-1]，也展现了一次遍历同时查找min和max的方法，时间为O[3/2n],即二二步进，每次3次比较  
查找topK方面有两个具体的方法，即随机选择前I号元素的randomizedSelectIthMaxWrapper方法，和采用5分组的中位数快速选择算法的fiveFoldedMidSelectIthMaxWrapper方法，这是目前为止看来比较优雅，但最繁琐的查找排序算法，涵盖了插入排序、递归寻找中位数、改进的快速排序下的partition算法，含金量比较高   
randomizedSelectIthMaxWrapper采用了CLRS chapter 9 里面提出的随机快速partition算法，此算法能够快速定位待排的数是在pivot前还是pivot之后，然后可以进行快速的剪枝；  
fiveFoldedMidSelectIthMaxWrapper采用了CLRS chapter 9 里面提到的五分组快速选择算法，CLRS并未完整给出其算法细节，只是提示快速排序的partition算法需要锁定pivot实现，此外，CLRS还给出5分组中位数算法，以快速求得某数组的中位数。此算法有诸多小细节需要额外注意，例如改进的partition算法将不得不将尾元素考虑到排序移位上来，并且，需要手动把原pivot与新pivot值交换，否则topK的K将无法精确定位。  

---
## container包相关:  
此包涵盖基础的容器类的实现及相应算法  
## yuanjun.chen.base.container.MyStack.java
此类为简易的栈，支持简易的入栈出栈操作，并且支持自动shrink操作，yuanjun.chen.base.container.MyStackTest.java实现了对MyStack的测试  
## yuanjun.chen.base.container.MyRBTree.java
此类为红黑树的经典实现，内置了NIL哨兵常量来避免空指针的边界判断，支持插入、删除等常规红黑树操作，以及层次/中序遍历、打印等观察和验证功能  

---
## dynamicprogramming包相关：  
此包涵盖动态规划相关算法  
## CutRodAlgo.java
为CLRS-3经典的切割钢条算法，如果采用暴力遍历，则效率为O[2^n]的时间复杂度，如果采用辅助的容器来记录子问题的已有答案，则性能及其优秀，时间复杂度O[n^2]，空间复杂度O[n]
## MatrixChainMultiplicationAlgo.java
为CLRS-3经典的矩阵链式乘法的动态规划算法，如果采用暴力遍历，效率为O[n!]遍历，但如果步进记录各个小问题的答案，则时间复杂度为O[n^3],空间复杂度为O[n^2]
## LCSAlgo.java
为CLRS-3经典的LCS算法（最长公共子串），采用额外的辅助矩阵来记录方向（可以优化掉，但为了不影响理解，暂时保留），特别感谢@[薛丁文](https://www.cnblogs.com/XueDingWen/p/EXLCS.html)给出的遍历所有LCS解法打印的方案  
## CompanyPartyConvivialityAlgo.java
为CLRS-3课后的公司聚会问题DP算法，其采用了左右子女树的公司结构，求得所有最优全体快乐指数的聚会人员安排全集  
## TspBitonicAlgo.java
为CLRS-3课后的双调欧几里得旅行商[Bently优化版]，其采用DP的方法求出一个逼近最优解的朴素可行解  

---
## greedy包相关：  
此包涵盖贪心算法：  
包括活动选择，背包问题，找零问题等  
---
## jmh相关  
mvn clean install -Dmaven.test.skip=true  此命令将打成2个包，其中包括正常的包，和benchmarks.jar包，后者为真正的性能测试jar 
如果需要执行benchmark测试，只需java -jar benchmarks.jar就可以看到结果，当然也可以在IDE里面执行main方法  

---
## game相关
此包涵盖基础的枚举类型的游戏（猜数字，24点）  
mmorpg描述了在大型随机加点的对战中，选取最优加点数的暴力枚举    
nPuzzle采用IDA*算法对n数码问题进行解决和简易console展示   
