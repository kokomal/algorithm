# algorithm
本工程涵盖基本算法基础 ，部分算法来源于算法导论相关章节

## yuanjun.chen.base.sort.BubbleSortAlgo.java
冒泡排序实现类，yuanjun.chen.base.sort.TestCase001.java实现相应测试

## yuanjun.chen.base.sort.HeapSortAlgo.java
堆排序实现类，yuanjun.chen.base.sort.TestCase002.java实现相应测试  
堆排序支持递归堆整理方法，和非递归整理方法，二者区别在于MAX_HEAPIFY的过程中采用的方式是否为递归  

## yuanjun.chen.base.sort.InsertionSortAlgo.java
插入排序实现类，yuanjun.chen.base.sort.TestCase003.java实现相应测试  
插入排序支持线性搜索插入算法，和改进的二分查找搜索插入算法，二者区别在于，前者是逐一线性的逆序查找插入点  
而后者是采用二分法进行插入点的搜索  

## yuanjun.chen.base.sort.MergeSortAlgo.java
归并排序实现类，yuanjun.chen.base.sort.TestCase004.java实现相应测试  
归并排序是经典的分治算法，只是额外需要O[n]的内存空间，本算法在merge中申请某一区间的数组空间temp，排序好之后，将temp复制到指定的原始数组里面  

## yuanjun.chen.base.sort.HeapBasedPriorityQueue.java
基于堆的优先级队列实现，yuanjun.chen.base.sort.TestCase005.java实现相应测试  
优先级队列参考了HeapSortAlgo类的堆整理和插入、删除及升级/降级操作   

## yuanjun.chen.base.sort.QuickSortAlgo.java
快速排序实现，yuanjun.chen.base.sort.TestCase007.java实现相应测试  

## yuanjun.chen.base.sort.SelectionSortAlgo.java
选择排序实现，yuanjun.chen.base.sort.TestCas006.java实现相应测试    

## yuanjun.chen.base.sort.CountingSortAlgo.java
计数排序实现，yuanjun.chen.base.sort.TestCase008.java实现相应测试  
计数排序适用于元素区间远远小于元素个数的场景，采用了额外的线性空间，换取时间复杂度为O[n]  
如果元素的方差比较大，那么此算法将耗费比较大的空间  

