package yuanjun.chen.base.other;
/*
 * LEETCODE 108
 * */
public class SortedArrayToBST {
	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) {
			val = x;
		}
	}

	public static TreeNode sortedArrayToBST(int[] nums) {
		int len = nums.length;
		if (len == 0)
			return null;
		return aux(nums, 0, len - 1);
	}

	public static TreeNode aux(int[] nums, int start, int end) {
		if (start == end) {
			TreeNode node = new TreeNode(nums[start]);
			return node;
		} else {
			int mid = (start + end) >> 1;
			if (mid == start) {
				TreeNode node = new TreeNode(nums[end]);
				node.left = new TreeNode(nums[mid]);
				return node;
			} else {
				TreeNode node = new TreeNode(nums[mid]);
				node.left = aux(nums, start, mid - 1);
				node.right = aux(nums, mid + 1, end);
				return node;
			}
		}
	}
}
