package yuanjun.chen.base.container;

public class BSTnode<T extends Comparable<T>> {
    protected T val;
    protected BSTnode<T> parent;
    protected BSTnode<T> left;
    protected BSTnode<T> right;
}
