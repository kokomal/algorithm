/**
 * @Title: CodecBinaryTreeNode.java
 * @Package: yuanjun.chen.base.greedy.huffman
 * @Description: 编码树节点
 * @author: 陈元俊
 * @date: 2018年10月23日 上午10:01:42
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.greedy.huffman;

/**
 * @ClassName: CodecBinaryTreeNode
 * @Description: Huffman的节点
 * @author: 陈元俊
 * @date: 2018年10月23日 上午10:01:42
 */
public class CodecBinaryTreeNode implements Comparable<CodecBinaryTreeNode> {
    protected Integer weight;
    /** 对于叶节点是有值的，内部节点没有. */
    protected Character val;
    protected boolean isLeaf;

    protected CodecBinaryTreeNode left;
    protected CodecBinaryTreeNode right;
    
    public CodecBinaryTreeNode(Integer weight, Character val, boolean isLeaf) {
        super();
        this.weight = weight;
        this.val = val;
        this.isLeaf = isLeaf;
    }

    @Override
    public int compareTo(CodecBinaryTreeNode o) {
        if (weight > o.weight) {
            return 1;
        } else if (weight == o.weight) {
            return 0;
        }
        return -1;
    }
}
