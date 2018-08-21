package yuanjun.chen.base.container.RBTree.cyf;

public class Main {

    public static void main(String[] args) {
        OSTree<String> tree = new OSTree<String>();
        tree.insert(26, "26");

        tree.insert(17, "17");
        tree.insert(41, "41");

        tree.insert(14, "14");
        tree.insert(21, "21");
        tree.insert(30, "30");
        tree.insert(47, "47");

        tree.insert(10, "10");
        tree.insert(16, "16");
        tree.insert(19, "19");
        tree.insert(22, "22");
        tree.insert(28, "28");
        tree.insert(38, "38");

        tree.insert(7, "7");
        tree.insert(12, "12");
        tree.insert(15, "15");

        tree.insert(20, "20");
        tree.insert(35, "35");
        tree.insert(39, "39");

        tree.insert(3, "3");
        System.out.println(tree);
        System.out.println("输出第1、10、20号元素");
        System.out.println(tree.select(1));
        System.out.println(tree.select(10));
        System.out.println(tree.select(20));

    }

}
