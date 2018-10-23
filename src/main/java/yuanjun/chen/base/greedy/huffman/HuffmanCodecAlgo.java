/**
 * @Title: HuffmanCodecAlgo.java
 * @Package: yuanjun.chen.base.greedy.huffman
 * @Description: 哈夫曼编解码
 * @author: 陈元俊
 * @date: 2018年10月23日 上午9:54:02
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.greedy.huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import yuanjun.chen.base.common.SortOrderEnum;
import yuanjun.chen.base.container.HeapBasedPriorityQueue;

/**
 * @ClassName: HuffmanCodecAlgo
 * @Description: 哈夫曼编解码
 * @author: 陈元俊
 * @date: 2018年10月23日 上午9:54:02
 */
public class HuffmanCodecAlgo {
    private static final String LOREM =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse venenatis velit tempus, ultricies lectus non, suscipit diam. Pellentesque nunc felis, pellentesque a finibus non, mattis at urna. Cras erat mi, tincidunt at sodales nec, consectetur eu turpis. Aliquam ornare auctor massa sed posuere. Praesent lobortis lorem in feugiat lobortis. Pellentesque dapibus semper tristique. Aenean accumsan odio eget est efficitur, at lobortis nibh hendrerit."
                    + "Nunc non lacus magna. Duis convallis est quam, vel consequat neque ultricies vel. Mauris fringilla malesuada orci. Curabitur et eros finibus, auctor quam at, dignissim nisi. Phasellus eu ligula feugiat, blandit mi et, tempus odio. Aliquam fringilla sed velit eget finibus. Mauris semper est nulla, id convallis quam sodales a. Donec mollis erat eget lacus consequat, quis aliquam diam porttitor. Etiam fringilla tempus lacus at posuere. In hac habitasse platea dictumst. Etiam eu ligula congue, tempor lacus ac, volutpat nisl. Etiam consectetur tortor in ipsum tempor rhoncus. Nam blandit gravida faucibus. Cras in commodo nibh. Duis lacinia bibendum sodales.";
    private static CodecBinaryTreeNode[] huffmanNodes;
    private static HeapBasedPriorityQueue<CodecBinaryTreeNode> hbpq; // 优先级队列
    private static Map<Character, String> encodeMap; // 编码对照表

    public static void init(char[] chars, Integer[] weights) {
        encodeMap = new HashMap<>();
        int len = chars.length;
        huffmanNodes = new CodecBinaryTreeNode[len];
        for (int i = 0; i < len; i++) {
            CodecBinaryTreeNode cbtn = new CodecBinaryTreeNode(weights[i], chars[i], true);
            huffmanNodes[i] = cbtn;
        }
        generatePriorityQueue(len);

        CodecBinaryTreeNode root = hbpq.peek();
        traverse(root, new StringBuilder(""));
        System.out.println(encodeMap);
    }

    private static void generatePriorityQueue(int len) {
        hbpq = new HeapBasedPriorityQueue<>(huffmanNodes, SortOrderEnum.DESC); // 建堆
        for (int i = 0; i < len - 1; i++) { // 执行len-1次构建Huffman树
            CodecBinaryTreeNode first = hbpq.pop();
            CodecBinaryTreeNode second = hbpq.pop();
            CodecBinaryTreeNode combined = new CodecBinaryTreeNode(first.weight + second.weight, null, false);
            combined.left = first;
            combined.right = second;
            hbpq.insertKey(combined);
        }
    }

    // root not null
    // 为了效率，可以将"密电码"持久化写入hashmap中，这样就可以不用每一次都计算一遍
    public static void traverse(CodecBinaryTreeNode root, StringBuilder trace) {
        CodecBinaryTreeNode left = root.left;
        if (left != null && !left.isLeaf) {
            traverse(left, new StringBuilder(trace).append('0'));
        } else if (left != null && left.isLeaf) {
            // System.out.println("For " + left.val + " the encoded is " + trace + "0");
            encodeMap.put(left.val, trace.toString() + "0");
        }

        CodecBinaryTreeNode right = root.right;
        if (right != null && !right.isLeaf) {
            traverse(right, new StringBuilder(trace).append('1'));
        } else if (right != null && right.isLeaf) {
            // System.out.println("For " + right.val + " the encoded is " + trace + "1");
            encodeMap.put(right.val, trace.toString() + "1");
        }
    }

    /**
     * @Title: encode
     * @Description: 一旦编码表确定，可以直接查表
     * @param raw
     * @return: String
     */
    public static String encode(String raw) {
        StringBuilder encrpyt = new StringBuilder();
        for (char x : raw.toCharArray()) {
            encrpyt.append(encodeMap.get(x));
        }
        return encrpyt.toString();
    }

    /**
     * @Title: decode
     * @Description: 解码仍然需要遍历二叉树，效率会慢一些
     * @param encrypt
     * @return: String
     */
    public static String decode(String encrypt) {
        CodecBinaryTreeNode cur = hbpq.peek();
        CodecBinaryTreeNode root = hbpq.peek();
        StringBuilder raw = new StringBuilder();
        for (char x : encrypt.toCharArray()) {
            if (x == '0') {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
            if (cur.isLeaf) {
                raw.append(cur.val);
                cur = root;
            }
        }
        return raw.toString();
    }

    public static void initWithRawInputs(String book) {
        Map<Character, Integer> wordsCount = new HashMap<>();
        for (char x : book.toCharArray()) {
            if (wordsCount.containsKey(x)) {
                wordsCount.put(x, wordsCount.get(x) + 1);
            } else {
                wordsCount.put(x, 1);
            }
        }
        System.out.println("MAP = " + wordsCount);
        Integer[] wts = new Integer[wordsCount.size()];
        char[] chars = new char[wordsCount.size()];
        Set<Entry<Character, Integer>> kvs = wordsCount.entrySet();
        int i = 0;
        for (Entry<Character, Integer> entry : kvs) {
            chars[i] = entry.getKey();
            wts[i] = entry.getValue();
            i++;
        }
        init(chars, wts);
    }

    public static void main(String[] args) {
        Integer[] wts = new Integer[] {45, 13, 12, 16, 9, 5};
        init("abcdef".toCharArray(), wts);
        String raw = "abdfefedbfdbfaecccdedffa";

        String encoded = encode(raw);
        System.out.println(encoded);

        String back = decode(encoded); // 其实这个decode比较无用，其实这个tree是根据明文构建的

        System.out.println(back);

        System.out.println("--------------------------------------------------");

        initWithRawInputs(LOREM); // 根据正文进行编码
        String loremEncoded = encode(LOREM);
        System.out.println(loremEncoded);
        System.out.println(loremEncoded.length());
    }
}
