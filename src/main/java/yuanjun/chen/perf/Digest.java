/**
 * @Title: Digest.java
 * @Package: yuanjun.chen.perf
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2019年2月2日 下午2:10:48
 * @version V1.0
 * @Copyright: 2019 All rights reserved.
 */
package yuanjun.chen.perf;

import java.util.zip.CRC32;

/**
 * @ClassName: Digest
 * @Description: java自带的CRC32摘要算法
 * @author: 陈元俊
 * @date: 2019年2月2日 下午2:10:48
 */
public class Digest {
    public static void main(String[] args) {
        CRC32 crc32 = new CRC32();
        crc32.update("hello-world".getBytes());
        System.out.println(crc32.getValue());
    }
}
