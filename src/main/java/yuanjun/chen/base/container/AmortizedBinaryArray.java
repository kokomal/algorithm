/**
 * @Title: AmortizedBinaryArray.java
 * @Package: yuanjun.chen.base.container
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年11月5日 下午3:02:04
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.container;

import java.util.Arrays;

/**
 * @ClassName: AmortizedBinaryArray
 * @Description: 对应CLRS-3 17.2动态二分查找数组
 * @author: 陈元俊
 * @date: 2018年11月5日 下午3:02:04
 */
public class AmortizedBinaryArray {
    private int legalMax;
    /** 总共的数据. */
    private int nElements;
    /** 总共的维度, N = log 2 nElements. */
    private int N;
    /** 0~N-1编号，每一个内含2^id的元素，例如arr[0]有1个元素，arr[2]有4个元素，以此类推. */
    private int[][] arr;
    /** 0~N-1编号的full flag数组，要么满，要么空， true=满，false=空. */
    private boolean[] fullArr;

    public void init(int n) { // 初始化
        this.N = n;
        this.legalMax = (int) Math.pow(2, N);
        this.nElements = 0;
        this.arr = new int[N][]; // 此时还完全没有初始化
        this.fullArr = new boolean[N]; // 默认false
    }

    public boolean find(int x) { // 查找比较慢，logN*logN
        for (int i = 0; i < N; i++) {
            if (fullArr[i]) {
                int pos = Arrays.binarySearch(arr[i], x);
                if (pos != 0) {
                    return true; // 找到了，其实[i,pos]即为其坐标，但其实并不关心其具体的位置啦
                }
            }
        }
        return false; // 找不到！
    }

    public void insert(int x) { // 插入，暂时不考虑溢出的regroup
        if (this.nElements >= legalMax) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int pos = 0;
        int[] tmp = new int[] {x};
        do {
            if (!this.fullArr[pos]) { // 不满
                this.arr[pos] = tmp;
                Arrays.sort(this.arr[pos]);
                this.fullArr[pos] = true;
                this.nElements++;
                return;
            } else { // 满了
                int tmpLen = tmp.length;
                int[] a1 = this.arr[pos];
                int[] nextTmp = new int[tmpLen * 2];
                System.arraycopy(a1, 0, nextTmp, 0, tmpLen);
                System.arraycopy(tmp, 0, nextTmp, tmpLen, tmpLen);
                tmp = nextTmp;
                this.fullArr[pos] = false;
                this.arr[pos] = null;
                pos++;
            }
        } while (true);
    }

    public void delete(int x) {
        System.out.println("DELETE " + x);
        int idx = 0;
        int pos = 0;
        boolean found = false;
        for (int i = 0; i < N; i++) {
            if (fullArr[i]) {
                pos = Arrays.binarySearch(arr[i], x);
                if (pos >= 0) {
                    idx = i;
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            throw new IllegalAccessError(); // 没找到就抛异常
        }
        int firstNonEmpty = 0;
        // 接下来找最近的一个满的
        for (int i = 0; i < N; i++) {
            if (fullArr[i]) {
                firstNonEmpty = i;
                break;
            }
        }
        // swap，将待删除的元素与最近的满数组的最后一个元素交换，然后重排待删元素所在数组
        int tail = arr[firstNonEmpty].length;
        int tmp = this.arr[idx][pos]; // 待删除，1,0
        this.arr[idx][pos] = arr[firstNonEmpty][tail - 1];
        arr[firstNonEmpty][tail - 1] = tmp;
        if (firstNonEmpty != idx) {
            Arrays.sort(this.arr[idx]); // idx代表待删除元素所在的arr，说明在高区，不参与后面的split
        } else if (arr[idx].length >= 2) { // 待删除元素就是自己，那么需要精致的排序,不要把最后一位给带入排序
            Arrays.sort(this.arr[idx], 0, arr[idx].length - 2);
        }
        // split!
        int len = 1;
        int mark = 0;
        for (int i = 0; i < firstNonEmpty; i++) { // 逐批次把len长度的内容复制到低区
            arr[i] = new int[len];
            System.arraycopy(arr[firstNonEmpty], mark, arr[i], 0, len);
            fullArr[i] = true;
            mark += len;
            len = len * 2;
        }
        arr[firstNonEmpty] = null; // 散伙
        fullArr[firstNonEmpty] = false;
    }

    public void report() {
        System.out.println("==============BEGIN===============");
        for (int i = 0; i < N; i++) {
            System.out.println(Arrays.toString(arr[i]));
        }
        System.out.println(Arrays.toString(fullArr));
        System.out.println("==============END===============");
    }

    public static void main(String[] args) {
        AmortizedBinaryArray arr = new AmortizedBinaryArray();
        arr.init(4); // 15元素最多
        arr.insert(22);
        arr.report();
        arr.insert(33);
        arr.report();
        arr.insert(55);
        arr.report();
        arr.insert(44);
        arr.report();
        arr.insert(66);
        arr.report();
        arr.insert(88);
        arr.report();
        arr.insert(123);
        arr.report();
        arr.insert(144);
        arr.report();
        arr.insert(152);
        arr.report();
        arr.insert(122);
        arr.report();
        System.out.println("-------------------------------------------");
        arr.delete(88);
        arr.report();
        arr.delete(55);
        arr.report();
        arr.delete(33);
        arr.report();
        arr.delete(22);
        arr.report();
    }
}
