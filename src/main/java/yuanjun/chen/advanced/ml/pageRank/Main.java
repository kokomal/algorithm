package yuanjun.chen.advanced.ml.pageRank;

import java.util.ArrayList;

/*
 * code by 邦柳
 * 
 */
public class Main {

    public static void main(String[] args) {
        String url = "http://www.zhihu.com/explore/recommendations";
        Draw draw = new Draw();
        Spider spider = new Spider();
        // 获取地址集
        ArrayList<WebEntity> retList = new ArrayList<WebEntity>();
        retList.addAll(spider.getWebList(url));

        int len = retList.size();

        PageRankEntity page = new PageRankEntity(len + 1);

        // 生成对应矩阵
        int j = 0;
        for (int i = 0; i < len; i++) {
            System.out.println(retList.get(i).Url);
            for (String s : retList.get(i).outUrl) {
                j = spider.webUrlContain(retList, s);
                if (j != -1) {
                    // System.out.println("get page");
                    page.num[i][j] = (double) 1;
                }
            }
        }
        page.PageRank();
        draw.drawPic(len, page);
        page.show_V(retList);
        System.out.println("PROGRAM END");
    }
}
