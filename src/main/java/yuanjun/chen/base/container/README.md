# container
---
本包实现了算法中基础的数据结构  
.BinarySearchTree.java实现二叉搜索树的搜索、删除和查找等相关操作，BSTnode为其基础数据结构  
.MyQueue是由循环数组实现的有界FIFO泛型队列，支持进队，出队，空间判断（空or满），容量等操作  
※※※MyQueue的空间留存了额外的一个tail虚元素，是为了计算边界条件方便，防止队列满时和队列空时分辨不清  
.MyDeque是由MyQueue扩展而来，支持双端的进队出队操作 
.HeapBasedPriorityQueue.java实现了基于堆的优先级队列   
.MyRBTree.java和RBTnode.java为红黑树的经典实现，内置了NIL哨兵常量来避免空指针的边界判断，支持插入、删除等常规红黑树操作，以及层次/中序遍历、打印等观察和验证功能  
插入新元素，需要进行插入调整，以适配红黑树的特性，删除元素，如果删除的是黑色节点，也需要进行删除调整  