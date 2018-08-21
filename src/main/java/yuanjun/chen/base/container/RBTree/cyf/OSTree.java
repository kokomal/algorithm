package yuanjun.chen.base.container.RBTree.cyf;

import java.util.LinkedList;
import java.util.Queue;
import yuanjun.chen.base.container.RBTree.cyf.Node.Color;

/** Red-Black Tree，key值不重复的集合 这里只有插入方法 参考：《算法导论》13.3.节 Created by 曹艳丰 2016-08-18 */
public class OSTree<T> {
    public Node<T> root, nil;

    /**
     * 查找第i个节点.
     * 
     * @param i
     * @return
     */
    public Node<T> select(int i) {
        return select(root, i);
    }

    /** 《算法导论》14.1节 OS-SELECT(x,i)函数 */
    private Node<T> select(Node<T> x, int i) {
        int r = 1;
        if (x.left != nil) {
            r += x.left.size;
        }
        if (i == r) {
            return x;
        } else if (i < r) {
            return select(x.left, i);
        } else {
            return select(x.right, i - r);
        }
    }

    /** 插入键值对。采用上下指针，从root开始遍历，直到找到插入点. */
    public void insert(int key, T value) {
        Node<T> y = nil;
        Node<T> x = root;
        Node<T> z = new Node<T>(key, value);
        // x、y双指针移动
        while (x != nil) {
            y = x;
            if (key < x.key) {
                x.size++;// 下探路径的节点都累加
                x = x.left;
            } else if (key > x.key) {
                x.size++;// 下探路径的节点都累加
                x = x.right;
            } else {
                x.setValue(value);// 如果已经存在这个key，就修改该值
                return;
            }
        }

        z.parent = y;
        if (y == nil) {
            root = z;
            updateSize(root);
            return;
        } else if (key < y.key) {
            y.left = z;
        } else {
            y.right = z;
        }
        z.left = nil;
        z.right = nil;
        z.color = Color.RED;

        insertFixUp(z);// 调整平衡
        updateSize(z);// 更新本节点的size
    }

    /** 从该节点开始，从下到上进行调整. */
    private void insertFixUp(Node<T> z) {
        while (z.parent != nil && z.parent.color == Color.RED) {
            if (z.parent == z.parent.parent.left) {
                Node<T> y = z.parent.parent.right;
                if (y.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else if (z == z.parent.right) {
                    z = z.parent;
                    leftRoate(z);
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    rightRoate(z.parent.parent);
                }
            } else {
                Node<T> x = z.parent.parent.left;
                if (x.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    x.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else if (z == z.parent.left) {
                    z = z.parent;
                    rightRoate(z);
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    leftRoate(z.parent.parent);
                }
            }
        }
        root.color = Color.BLACK;
    }

    /** 更新节点的size. */
    private void updateSize(Node<T> z) {
        z.size += 1;
        if (z.left != null) {
            z.size += z.left.size;
        }
        if (z.right != null) {
            z.size += z.right.size;
        }
    }

    /** 左旋. */
    private void leftRoate(Node<T> x) {
        Node<T> y = x.right;
        x.right = y.left;
        // y的左子树接到x的右子树
        if (y.left != nil) {
            y.left.parent = x;
        }

        // y的parent变更到x的parent
        y.parent = x.parent;

        if (x.parent == nil) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        // y的左子树变更到x
        y.left = x;
        x.parent = y;
        y.size = x.size;
        updateSize(x);// 旋转过程中需要更改size
    }

    /** 右旋. */
    private void rightRoate(Node<T> y) {
        Node<T> x = y.left;
        y.left = x.right;
        // x的右子树接到y的左子树
        if (x.right != nil) {
            x.right.parent = y;
        }

        // x的parent变更到y的parent
        x.parent = y.parent;

        if (y.parent == nil) {
            root = x;
        } else if (y == y.parent.left) {
            y.parent.left = x;
        } else {
            y.parent.right = x;
        }
        // x的右子树变更到y
        x.right = y;
        y.parent = x;
        x.size = y.size;
        updateSize(y);// 旋转过程中需要更改size
    }

    /** 查询. */
    public Node<T> search(int key) {
        Node<T> y = nil;
        Node<T> x = root;
        // x、y双指针移动
        while (x != nil) {
            y = x;
            if (key < x.key) {
                x = x.left;
            } else if (key > x.key) {
                x = x.right;
            } else {
                break;
            }
        }
        return x;
    }

    /** 根据key值来删除节点. */
    public void delete(int key) {
        Node<T> z = search(key);
        if (z != nil) {
            delete(z);
        } else {
            throw new IllegalArgumentException("不存在这个Key！");
        }
    }

    /** 删除节点z. */
    private void delete(Node<T> z) {
        Node<T> y = z;
        Node<T> x;
        Color yOriginalColor = y.color;
        if (z.left == nil) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == nil) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            // 《算法导论》13.4节 RB-DELETE(T,Z) 12、13的伪代码有问题
            // z.right的左节点不为null
            if (y.parent == z) {
                if (x != nil) {
                    x.parent = y;
                }
            } else {
                transplant(y, y.right);
                y.right = z.right;
                if (y.right != nil && y.right.parent != nil) {
                    y.right.parent = y;
                }
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        // 删除red类型不改变数据结构，黑高不变
        if (yOriginalColor == Color.BLACK && x != nil) {
            deleteFixUp(x);
        }
    }

    /** 删除后恢复平衡. */
    private void deleteFixUp(Node<T> x) {
        while (x != root && x != nil && x.color == Color.BLACK) {
            if (x == x.parent.left) {
                Node<T> w = x.parent.right;
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    leftRoate(x.parent);
                    w = x.parent.right;
                }
                if (w.right.color != Color.BLACK) {
                    w.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    w.right.color = Color.BLACK;
                    leftRoate(x.parent);
                    x = root;
                } else if (w.left.color == Color.BLACK) {
                    w.color = Color.RED;
                    x = x.parent;
                } else {
                    w.right.color = Color.BLACK;
                    w.color = Color.RED;
                    rightRoate(w);
                    w = x.parent.right;
                }
            } else {
                Node<T> w = x.parent.left;
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    leftRoate(x.parent);
                    w = x.parent.left;
                }
                if (w.left.color != Color.BLACK) {
                    w.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    w.left.color = Color.BLACK;
                    rightRoate(x.parent);
                    x = root;
                } else if (w.right.color == Color.BLACK) {
                    w.color = Color.RED;
                    x = x.parent;
                } else {
                    w.right.color = Color.BLACK;
                    w.color = Color.RED;
                    leftRoate(w);
                    w = x.parent.left;
                }
            }
        }
        x.color = Color.BLACK;
    }

    /** 节点x子树种最小的节点 左节点都为null时，返回自身. */
    private Node<T> minimum(Node<T> x) {
        while (x.left != nil) {
            x = x.left;
        }
        return x;
    }

    /** 用节点v取代树中的节点u. */
    private void transplant(Node<T> u, Node<T> v) {
        if (u.parent == nil) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        if (v != nil) {
            v.parent = u.parent;
        }
    }

    /** 按层级输出. */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Queue<Node<T>> queue = new LinkedList<>();
        queue.offer(root);
        int cur = 0;// 每一层的指针
        int level = 0;
        while (cur < queue.size()) {
            builder.append(level).append("层：");
            int lastofCurrentLevel = queue.size();
            while (cur < lastofCurrentLevel) {
                Node<T> pointer = queue.poll();
                builder.append(pointer);
                builder.append("    ");
                if (pointer.left != null) {
                    queue.offer(pointer.left);
                }
                if (pointer.right != null) {
                    queue.offer(pointer.right);
                }
                cur++;
            }
            builder.append("\n");
            cur = 0;
            level++;
        }

        return builder.toString();
    }
}
