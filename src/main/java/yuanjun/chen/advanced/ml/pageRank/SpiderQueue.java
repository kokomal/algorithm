package yuanjun.chen.advanced.ml.pageRank;

import java.util.HashSet;
import java.util.Set;

/*
 * code by 邦柳
 * 
 * 爬虫主体队列
 */
public class SpiderQueue {
    private static Set<Object> visitedUrl = new HashSet<>();
    private static WebQueue unVisitedUrl = new WebQueue();

    public void addVisiteUrl(String Url) {
        visitedUrl.add(Url);
    }

    public void removeVisitedUrl(String url) {
        visitedUrl.remove(url);
    }

    public int getVisitedUrlNum() {
        return visitedUrl.size();
    }

    public Object unVisitedUrlDequeue() {
        return unVisitedUrl.deQueue();
    }

    public void addUnvisitedUrl(String url) {
        if (url != null && !url.trim().equals("") && !visitedUrl.contains(url) && !unVisitedUrl.contians(url)) {
            unVisitedUrl.enQueue(url);
            // System.out.println("spiderQueue add to list success:"+url);
        } else if (url == null) {
            // System.out.println("spiderQueue url=null");
        } else if (url.trim().equals("")) {
            // System.out.println("spiderQueue url equals null");
        } else if (visitedUrl.contains(url)) {
            // System.out.println("spiderQueue vistedList alearld have");
        } else if (unVisitedUrl.contians(url)) {
            // System.out.println("spiderQueue unVisitedList alearld have");
        } else
            System.out.println("spiderQueue something happened");

    }

    public boolean unVisitedUrlsEmpty() {
        return unVisitedUrl.empty();
    }

    public int getUnVisitedUrlNum() {
        return unVisitedUrl.getNum();
    }
}
