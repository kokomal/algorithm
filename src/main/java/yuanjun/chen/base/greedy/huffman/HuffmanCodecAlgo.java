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
 * @Description: 哈夫曼编解码，不推荐用Lipsum做字典训练，这是因为西塞罗的文本不包含K，W，Z
 *               这几个有异于拉丁文的字母，所以这几个字母和其他一些字母常常被随机插入去模拟欧洲语言的排印样式，
 *               这些字在原文中其实并没有。
 * @author: 陈元俊
 * @date: 2018年10月23日 上午9:54:02
 */
public class HuffmanCodecAlgo {
    // private static final char UNKNOWN = '✕';
    private static final String LOREM =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse venenatis velit tempus, ultricies lectus non, suscipit diam. Pellentesque nunc felis, pellentesque a finibus non, mattis at urna. Cras erat mi, tincidunt at sodales nec, consectetur eu turpis. Aliquam ornare auctor massa sed posuere. Praesent lobortis lorem in feugiat lobortis. Pellentesque dapibus semper tristique. Aenean accumsan odio eget est efficitur, at lobortis nibh hendrerit."
                    + "Nunc non lacus magna. Duis convallis est quam, vel consequat neque ultricies vel. Mauris fringilla malesuada orci. Curabitur et eros finibus, auctor quam at, dignissim nisi. Phasellus eu ligula feugiat, blandit mi et, tempus odio. Aliquam fringilla sed velit eget finibus. Mauris semper est nulla, id convallis quam sodales a. Donec mollis erat eget lacus consequat, quis aliquam diam porttitor. Etiam fringilla tempus lacus at posuere. In hac habitasse platea dictumst. Etiam eu ligula congue, tempor lacus ac, volutpat nisl. Etiam consectetur tortor in ipsum tempor rhoncus. Nam blandit gravida faucibus. Cras in commodo nibh. Duis lacinia bibendum sodales.";
    private CodecBinaryTreeNode[] huffmanNodes;
    private HeapBasedPriorityQueue<CodecBinaryTreeNode> hbpq; // 优先级队列
    private Map<Character, String> encodeMap = new HashMap<>(); // 编码对照表

    private CodecBinaryTreeNode root;

    public String init(char[] chars, Integer[] weights) {
        encodeMap = new HashMap<>();
        int len = chars.length;
        huffmanNodes = new CodecBinaryTreeNode[len];
        for (int i = 0; i < len; i++) {
            CodecBinaryTreeNode cbtn = new CodecBinaryTreeNode(weights[i], chars[i], true);
            huffmanNodes[i] = cbtn;
        }
        generatePriorityQueue();

        root = hbpq.peek();
        traverse(root, new StringBuilder(""));
        System.out.println("EncodeMAP = " + encodeMap); // 输出编码的对照表

        return CodecUtil.serialize(root); // 最终输出note
    }

    private void generatePriorityQueue() {
        hbpq = new HeapBasedPriorityQueue<>(huffmanNodes, SortOrderEnum.DESC); // 建堆
        for (int i = 0; i < huffmanNodes.length - 1; i++) { // 执行len-1次构建Huffman树
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
    public void traverse(CodecBinaryTreeNode root, StringBuilder trace) {
        CodecBinaryTreeNode left = root.left;
        if (left != null && !left.isLeaf) {
            traverse(left, new StringBuilder(trace).append('0'));
        } else if (left != null && left.isLeaf) {
            encodeMap.put(left.val, trace.toString() + "0");
        }

        CodecBinaryTreeNode right = root.right;
        if (right != null && !right.isLeaf) {
            traverse(right, new StringBuilder(trace).append('1'));
        } else if (right != null && right.isLeaf) {
            encodeMap.put(right.val, trace.toString() + "1");
        }
    }

    /**
     * @Title: encode
     * @Description: 一旦编码表确定，可以直接查表
     * @param raw
     * @return: String
     */
    public String encode(String raw) {
        if (encodeMap == null || encodeMap.isEmpty()) {
            traverse(root, new StringBuilder(""));
        }
        StringBuilder encrpyt = new StringBuilder();
        for (char x : raw.toCharArray()) {
            if (encodeMap.containsKey(x)) {
                encrpyt.append(encodeMap.get(x));
            } else {
                encrpyt.append(x); // 不识别用明文来表征，解码的时候会避开，这样做有点画蛇添足，因为真正传输是不可能混搭0101夹杂原码的
            }
        }
        return encrpyt.toString();
    }

    /**
     * @Title: decode
     * @Description: 解码仍然需要遍历二叉树，效率会慢一些
     * @param encrypt
     * @return: String
     */
    public String innerDecode(String encrypt) {
        CodecBinaryTreeNode cur = root;
        StringBuilder raw = new StringBuilder();
        for (char x : encrypt.toCharArray()) {
            if (x == '0') {
                cur = cur.left;
            } else if (x == '1') {
                cur = cur.right;
            } else {
                raw.append(x);
                cur = root;
                continue;
            }
            if (cur.isLeaf) {
                raw.append(cur.val);
                cur = root;
            }
        }
        return raw.toString();
    }

    /**
     * @Title: initWithRawInputs
     * @Description: 根据大数据来输出Huffman的配方
     * @param book
     * @return: String
     */
    public String initWithRawInputs(String book) {
        Map<Character, Integer> wordsCount = new HashMap<>();
        for (char x : book.toCharArray()) {
            if (wordsCount.containsKey(x)) {
                wordsCount.put(x, wordsCount.get(x) + 1);
            } else {
                wordsCount.put(x, 1);
            }
        }
        Integer[] wts = new Integer[wordsCount.size()];
        char[] chars = new char[wordsCount.size()];
        Set<Entry<Character, Integer>> kvs = wordsCount.entrySet();
        int i = 0;
        for (Entry<Character, Integer> entry : kvs) {
            chars[i] = entry.getKey();
            wts[i] = entry.getValue();
            i++;
        }
        return init(chars, wts);
    }

    /**
     * @Title: rebuild
     * @Description: 根据配方重建Huffman树
     * @param note
     * @return: void
     */
    public void rebuild(String note) {
        root = CodecUtil.deserialize(note);
    }

    public String decode(String encrpt) {
        return innerDecode(encrpt);
    }

    public static void main(String[] args) {
        HuffmanCodecAlgo algo1 = new HuffmanCodecAlgo();
        Integer[] wts = new Integer[] {45, 13, 12, 16, 9, 5};
        String recipe = algo1.init("abcdef".toCharArray(), wts);
        System.out.println("序列化的RECIPE:" + recipe);
        String raw = "abdfefedbfdbfaecccdedffa";
        System.out.println("编码前为:" + raw);
        String encoded = algo1.encode(raw);
        System.out.println("编码后为:" + encoded);

        String back = algo1.innerDecode(encoded); // 其实这个decode比较无用，其实这个tree是根据明文构建的
        System.out.println("解码后为:" + back);

        System.out.println("--------------------------------------------------");

        recipe = algo1.initWithRawInputs(LOREM); // 根据正文进行编码
        System.out.println("LOREM序列化的RECIPE = " + recipe);
        System.out.println("编码前为:" + LOREM);
        String loremEncoded = algo1.encode(LOREM);
        System.out.println("LOREM编码后:" + loremEncoded);

        /*-----------------------recipe+loremEncoded via network----------------------*/

        HuffmanCodecAlgo algo2 = new HuffmanCodecAlgo();
        algo2.rebuild(recipe); // 根据配方重建Huffman树

        back = algo2.decode(loremEncoded);
        System.out.println("algo2.LOREM解码后:" + back);

        String a2 = algo2.encode("adipiscingwawa"); // LOREM ipsum里面没有w，因此如果待加密的串里面有w会出现找不到字典的key
        System.out.println("a2Encoded = " + a2);

        String bk1 = algo1.decode(a2);
        System.out.println("此时algo1端也能成功解码" + bk1); // 实现双向编解码
    }
}
