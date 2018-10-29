package yuanjun.chen.game.nPuzzle;

public class IVP {
    /** 全局变量，使用合并排序，计算逆序对数. */
    public long count;

    /** 使用归并排序方法计算数组A中的逆序对数. */
    public void getReverseCount(int[] A) {
        if (A.length > 1) {
            int[] leftA = getHalfArray(A, 0); // 数组A的左半边元素
            int[] rightA = getHalfArray(A, 1); // 数组A的右半边元素
            getReverseCount(leftA);
            getReverseCount(rightA);
            mergeArray(A, leftA, rightA);
        }
    }

    /** 根据judge值判断，获取数组A的左半边元素或者右半边元素. */
    public int[] getHalfArray(int[] A, int judge) {
        int[] result;
        if (judge == 0) { // 返回数组A的左半边
            result = new int[A.length / 2];
            try {
                System.arraycopy(A, 0, result, 0, A.length / 2);
            } catch (IndexOutOfBoundsException e) {
                throw new ArrayIndexOutOfBoundsException(e.getMessage());
            }
        } else { // 返回数组的右半边
            result = new int[A.length - A.length / 2];
            try {
                System.arraycopy(A, A.length / 2, result, 0, A.length - A.length / 2);
            } catch (IndexOutOfBoundsException e) {
                throw new ArrayIndexOutOfBoundsException(e.getMessage());
            }
        }
        return result;
    }

    /** 合并数组A的左半边和右半边元素，并按照非降序序列排列. */
    public void mergeArray(int[] A, int[] leftA, int[] rightA) {
        int len = 0;
        int i = 0;
        int j = 0;
        int lenL = leftA.length;
        int lenR = rightA.length;
        while (i < lenL && j < lenR) {
            if (leftA[i] > rightA[j]) {
                A[len++] = rightA[j++]; // 将rightA[j]放在leftA[i]元素之前，那么leftA[i]之后lenL - i个元素均大于rightA[j]
                count += lenL - i; // 合并之前，leftA中元素是非降序排列，rightA中元素也是非降序排列。所以，此时就新增lenL - i个逆序对
            } else {
                A[len++] = leftA[i++];
            }
        }
        while (i < lenL) {
            A[len++] = leftA[i++];
        }
        while (j < lenR) {
            A[len++] = rightA[j++];
        }
    }

    /** 获取一个随机数数组. */
    public int[] getRandomArray(int n) {
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = (int) (Math.random() * 50); // 生成0~50之间的随机数
        }
        return result;
    }

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        IVP ivp = new IVP();
        int[] A = ivp.getRandomArray(50000);
        ivp.getReverseCount(A);
        long t2 = System.currentTimeMillis();
        System.out.println("分治法得到结果：" + ivp.count + "， 耗时：" + (t2 - t1) + "毫秒");
    }
}
