package yuanjun.chen.base.container.RBTree.cyf;

/** Created by 曹艳丰 2016-08-18 Red-Black Tree 节点，包括key-value，左右指针和parent指针. */
public class Node<T> {
    public int key;
    private T value;
    public Node<T> right, left, parent;
    public Color color;
    /** 记录子树（包括本身）的大小. */
    public int size;

    public Node(int k, T v) {
        key = k;
        value = v;
        color = Color.BLACK;
        size = 0;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof Node<?>)) {
            return false;
        }
        Node<T> ent;
        try {
            ent = (Node<T>) o; // 检测类型
        } catch (ClassCastException ex) {
            return false;
        }
        return ent.key == key && ent.getValue() == value;
    }

    @Override
    public String toString() {
        return key + ":" + value + ":" + color + ":" + size;
    }

    /** Red-Black Tree 红黑属性. */
    public enum Color {
        RED, BLACK
    }
}
