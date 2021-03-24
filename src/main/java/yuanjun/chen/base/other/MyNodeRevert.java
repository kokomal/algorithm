package yuanjun.chen.base.other;

public class MyNodeRevert {
    private int val;
    private MyNodeRevert next;

    public MyNodeRevert(int v) {
        this.val = v;
    }

    public void linkNext(MyNodeRevert nex) {
        this.next = nex;
    }

    public void printSeq() {
        MyNodeRevert cur = this;
        while (cur != null) {
            System.out.println(cur.val + "->");
            cur = cur.next;
        }
    }

    // 原地翻转single链表
    public void revert() {
        MyNodeRevert cur = this;
        MyNodeRevert pre = null;
        while (cur != null) {
            MyNodeRevert next = cur.next; // temp
            cur.next = pre;
            pre = cur;
            cur = next;
        }
    }

    public static void main(String[] args) {
        MyNodeRevert n1 = new MyNodeRevert(100);
        MyNodeRevert n2 = new MyNodeRevert(200);
        MyNodeRevert n3 = new MyNodeRevert(300);
        MyNodeRevert n4 = new MyNodeRevert(400);
        MyNodeRevert n5 = new MyNodeRevert(500);
        MyNodeRevert n6 = new MyNodeRevert(600);
        MyNodeRevert n7 = new MyNodeRevert(700);
        n1.linkNext(n2);
        n2.linkNext(n3);
        n3.linkNext(n4);
        n4.linkNext(n5);
        n5.linkNext(n6);
        n6.linkNext(n7);
        n1.printSeq();
        System.out.println("---");
        n1.revert();
        n7.printSeq();
    }
}
