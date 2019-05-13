package yuanjun.chen.base.other;

/*LEETCODE 110*/
public class BalancedTree {
	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	public boolean isBalanced(TreeNode root) {
		return root == null
				|| (isBalanced(root.left) && isBalanced(root.right) && (Math.abs(h(root.left) - h(root.right)) <= 1));
	}

	private static int h(TreeNode root) {
		return root == null ? 0 : 1 + Math.max(h(root.left), h(root.right));
	} 
}
