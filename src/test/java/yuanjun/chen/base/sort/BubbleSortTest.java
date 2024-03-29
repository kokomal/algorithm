package yuanjun.chen.base.sort;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import yuanjun.chen.base.common.GenericAlgoTester;
import yuanjun.chen.base.common.SortOrderEnum;

import java.util.Date;

/** 冒泡排序测试. */
public class BubbleSortTest {
    @Test
    public void testBubble001() throws Exception {
        testBubbleSortProto(10000, 8000, SortOrderEnum.ASC, Integer.class);
    }

    @Test
    public void testBubble002() throws Exception {
        testBubbleSortProto(10000, 8000, SortOrderEnum.ASC, Float.class);
    }

    private static class MockDomain {
        public Date dt;
        public String name;
    }
    
    @Test
    public void testDt() {
        MockDomain mm = new MockDomain();
        mm.dt = new Date();
        mm.name = "haha";
        System.out.println(JSONObject.toJSONString(mm));
    }
    
    /**
     * @Title testBucketSortProto
     * @Description 原始测试类
     * @param size
     * @param bound
     * @param order
     * @throws Exception
     * @return void
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void testBubbleSortProto(int size, int bound, SortOrderEnum order, Class clazz) throws Exception {
        new GenericAlgoTester("BUBBLE") {
            @Override
            public void showTime(Comparable[] arr, SortOrderEnum order) {
                BubbleSortAlgo.inplaceSort(arr, order);
            }
        }.genericTest(size, bound, order, clazz);
    }
}
