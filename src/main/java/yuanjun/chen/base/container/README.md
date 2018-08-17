# container
---
本包实现了算法中基础的数据结构  
.BinarySearchTree.java实现二叉搜索树的搜索、删除和查找等相关操作，BSTnode为其基础数据结构  
.MyQueue是由循环数组实现的有界FIFO泛型队列，支持进队，出队，空间判断（空or满），容量等操作  
※※※MyQueue的空间留存了额外的一个tail虚元素，是为了计算边界条件方便，防止队列满时和队列空时分辨不清  
.MyDeque是由MyQueue扩展而来，支持双端的进队出队操作 
.HeapBasedPriorityQueue.java实现了基于堆的优先级队列   
