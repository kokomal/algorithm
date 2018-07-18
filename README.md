# algorithm
本工程涵盖基本算法基础 ，部分算法来源于算法导论相关章节

## BubbleSortAlgo.java
冒泡排序实现类，TestCase001.java实现相应测试

## HeapSortAlgo.java
堆排序实现类，TestCase002.java实现相应测试  
堆排序支持递归堆整理方法，和非递归整理方法，二者区别在于MAX_HEAPIFY的过程中采用的方式是否为递归  

## InsertionSortAlgo.java
插入排序实现类，TestCase003.java实现相应测试  
插入排序支持线性搜索插入算法，和改进的二分查找搜索插入算法，二者区别在于，前者是逐一线性的逆序查找插入点  
而后者是采用二分法进行插入点的搜索  

## MergeSortAlgo.java
归并排序实现类，TestCase004.java实现相应测试  
归并排序是经典的分治算法，只是额外需要O[n]的内存空间，本算法在merge中申请某一区间的数组空间temp，排序好之后，将temp复制到指定的原始数组里面  

## HeapBasedPriorityQueue.java
基于堆的优先级队列实现，TestCase005.java实现相应测试  
优先级队列参考了HeapSortAlgo类的堆整理和插入、删除及升级/降级操作   