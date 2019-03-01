package yuanjun.chen.perf;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by littlewolf on 11/1/2016.
 * -Xms5M -Xmx5M
 */
public class OOMDemo {
    public static void main(String[] args) throws Exception {
        //heapOOM();
        dirMemOOM();
    }
    
    // 传统意义的堆溢出
    public static void heapOOM() {
        ArrayList<String> strs = new ArrayList<String>(100000000);
        for ( int i = 0; i <= 100000000; i++ ) {
            strs.add(Integer.toString(i));
            if( i % 10000 == 0 ) {
                System.out.println("i: " + i);
            }
        }
    }
    
    // nio buffer导致的非堆OOM
    public static void dirMemOOM() {
        List<ByteBuffer> buffers = new ArrayList<>();
        while (true) {
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 1024);
            buffers.add(buffer);
        }
    }
}
