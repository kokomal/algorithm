给定N个活动，以及它们的开始时间和结束时间，求N个活动中，最大兼容的活动个数  
根据证明16-1，最早结束的活动am必然在一个最优的解Ak中  
因此只需每次获得最早结束的贪心解，然后再递归或者非递归剩下的合法集合，获得后者的最优解，再拼接起来