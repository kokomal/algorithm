/**
 * @Title: CodecUtil.java
 * @Package: yuanjun.chen.base.greedy.huffman
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年10月23日 下午2:48:47
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.greedy.huffman;

/**
 * @ClassName: CodecUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: 陈元俊
 * @date: 2018年10月23日 下午2:48:47
 */
public class CodecUtil {

    private static final char MID = '⊥'; // 暂且用⊥做保留字，记为中间节点
    private static final char NULL = 'Ø'; // 暂且用Ø做保留字，记为叶节点

    public static String serialize(CodecBinaryTreeNode node) {
        if (node == null) {
            return String.valueOf(NULL);
        }
        StringBuilder ret = new StringBuilder();
        ret.append(node.val == null ? MID : node.val); 
        if (node.left == null) {
            ret.append(NULL);
        } else {
            ret.append(serialize(node.left));
        }
        if (node.right == null) {
            ret.append(NULL);
        } else {
            ret.append(serialize(node.right));
        }
        return ret.toString();
    }

    private static int index = -1;
    
    public static CodecBinaryTreeNode deserialize(String note) {
        if (note == null || note.isEmpty()) {
            return null;
        }
        index = -1;
        return des(note);
    }

    private static CodecBinaryTreeNode des(String note) {
        index ++;
        if (note.charAt(index) == NULL) { // 说明是null
            return null;
        } else if (note.charAt(index) == MID){ // 说明是中间节点
            CodecBinaryTreeNode node = new CodecBinaryTreeNode(null, null, false);
            node.left = des(note);
            node.right = des(note);
            return node;
        } else { // 说明是叶节点
            CodecBinaryTreeNode node = new CodecBinaryTreeNode(null, note.charAt(index), true);
            node.left = des(note);
            node.right = des(note);
            return node;
        }
    }
}
