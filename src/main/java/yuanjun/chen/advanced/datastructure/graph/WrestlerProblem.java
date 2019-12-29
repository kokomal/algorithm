/**
 * @Title: WrestlerProblem.java
 * @Package: yuanjun.chen.advanced.datastructure.graph
 * @Description:
 * @author: 陈元俊
 * @date: 2019年12月28日 下午9:31:30
 * @version V1.0
 * @Copyright: 2019 All rights reserved.
 */
package yuanjun.chen.advanced.datastructure.graph;

/**
 * @ClassName: WrestlerProblem
 * @Description: 职业摔跤手可以分为两种各类型：“娃娃脸”（“好人”）型和“高跟鞋”（“坏人”）型。在任意一堆职业摔跤手之间都有可能存在竞争关系。假定有 n
 *               个职业摔跤手，并且有一个给出竞争关系的 r 对摔跤手的链表。请给出一个时间为 O( n+r )
 *               的算法来判断是否可以将某些摔跤手划分为“娃娃脸”型，而剩下的划分为“高跟鞋”型，使得所有的竞争关系均只存在于娃娃脸型和高跟鞋型选手之间。如果可以进行这种划分，则算法还应当生成一种这样的划分。
 * @author: 陈元俊
 * @date: 2019年12月28日 下午9:31:30
 */
public class WrestlerProblem {
    /*
     * 思路1(CLRS) 以s为边BFS一遍后，遍历每一条边，如果不符合（距离s）的距离同偶同奇（说明是友军，返回false）
     * 思路2，扩展BFS为三色，BFS过程中，先将所有置为白色（代表未处理），然后逐个间隔染色，如果遇到同为蓝色/红色的，就返回false
     */
}
