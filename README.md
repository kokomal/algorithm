# algorithm
本工程涵盖基本算法基础 ，部分算法来源于算法导论相关章节

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

## yuanjun.chen.base.sort.CountingSortAlgo.java
计数排序实现，yuanjun.chen.base.sort.CountingSortTest.java实现相应测试  
计数排序适用于元素区间远远小于元素个数的场景，采用了额外的线性空间，换取时间复杂度为O[n]，空间复杂度为O[n+m]其中n为数组size，m为数组的幅值    
如果元素的幅值比较大，那么此算法将耗费比较大的额外空间  
相对于桶排序，计数排序似乎更"喜欢"重复的key，其对键冲突不敏感

## yuanjun.chen.base.sort.BucketSortAlgo.java
桶排序实现，  yuanjun.chen.base.sort.BucketSortTest.java实现相应测试  
桶排序是经典的空间换时间的排序方法，采用类似“列表拉链数组”的方式，将待排元素划分成若干的桶，每个桶存放各个元素的节点
如果桶冲突，则执行链表插入排序，最后组装所有的桶将数据输出。链表方式的插入排序，好处是每一次的插入新元素，只涉及简单的链表节点移动和插入，不会像数组实现的插入排序那样每次插入都会导致大规模内存拷贝复制，其对内存操作比较友好，缺点就是需要额外对原始数据进行包装（转换成Node），内存占用比较大，并且不支持插入排序的二分优化改造  
桶排序的性能，比较依赖于数据来源的特点，和桶映射的方法  
如果输入数据方差比较小，那么可能会导致桶的宽度没有用上，此时桶排序退化成链表形式的插入排序  
桶的映射方法，也直接影响排序性能。如果能够做到根据数据的特征进行可伸缩的桶映射和size定制，将会提升运行性能和内存占用  
最"无耻"的方案是，对区间内的每一个元素都建立bucket，那么槽数组将变成一个巨型的链表数组，每一个数组的链表仅有1个元素，key冲突的处理也非常简单
本算法采用了一种对区间值进行平方根选取槽的方式，这是一个比较折中的方案，目的是为了均匀各个槽的分布，并且每一个槽的链表不至于太长，但实际的时间效率可能并不一定优秀  


