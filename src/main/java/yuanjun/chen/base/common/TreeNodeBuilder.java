/**
 * 
 */
package yuanjun.chen.base.common;

import java.util.ArrayList;
import java.util.List;

import yuanjun.chen.base.container.TreeNode;

/**
 * @author hp
 *
 */
public class TreeNodeBuilder {
	public static <T extends Object> TreeNode<T> buildABinaryTree(T[] vals) {
		int len = vals.length;
		if (len < 1)
			return null;
		TreeNode<T> root = new TreeNode<>();
		root.setVal(vals[0]);
		List<TreeNode<T>> thisLoop = new ArrayList<>();
		thisLoop.add(root);
		int i = 0;
		while (true) {
			List<TreeNode<T>> nextLoop = new ArrayList<>();
			for (TreeNode<T> t : thisLoop) {
				i++;
				if (i < len) {
					TreeNode<T> tt = new TreeNode<>();
					tt.setVal(vals[i]);
					t.setLeft(tt);
					nextLoop.add(tt);
				} else {
					return root;
				}
				i++;
				if (i < len) {
					TreeNode<T> tt = new TreeNode<>();
					tt.setVal(vals[i]);
					t.setRight(tt);
					nextLoop.add(tt);
				} else {
					return root;
				}
				thisLoop = nextLoop;
			}
		}
	}

	public static <T extends Object> void midtraverse(TreeNode<T> t) {
		if (t == null)
			return;
		System.out.println("t--" + t.getVal());
		midtraverse(t.getLeft());
		midtraverse(t.getRight());
	}

	public static void main(String[] args) {
		Integer[] tt = new Integer[] { 12, 32, 323, 13, 21124, 123, 2123 };
		TreeNode<Integer> t = buildABinaryTree(tt);
		
		System.out.println(t.getVal());
		System.out.println(t.getLeft().getVal());
		System.out.println(t.getRight().getVal());
		
		midtraverse(t);
	}
}
