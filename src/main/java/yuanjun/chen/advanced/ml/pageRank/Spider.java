package yuanjun.chen.advanced.ml.pageRank;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/*
 * code by 邦柳 爬虫主体类
 */
public class Spider {
    /*
     * 用于匹配的正则
     */
    public static String getUrl_question = "question_link.+?href=\"(.+?)\"";
    public static String getUrl_content = "content.+?href=\"(.+?)\"";
    public static String getUrl_visible = "visible-expanded.+?href=\"(.+?)\"";
    public static String getUrl_author = "author-link.+?href=\"(.+?)\"";
    public static String getUrl_editable = "zm-editable-content.+?href=\"(.+?)\"";

    // get请求
    public String SendGet_client(String url) {
        // System.out.println("spider(20) get info from:"+url);

        String ret = "";
        HttpClient client = null;
        try {
            client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            RequestConfig defaultConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
            httpGet.setConfig(defaultConfig);
            HttpResponse resp = client.execute(httpGet);
            
            int code = resp.getStatusLine().getStatusCode();
            System.out.println("CODE=" + code);
            if (code == 200) {
                HttpEntity httpEntity = resp.getEntity();
                String content = EntityUtils.toString(httpEntity);
                // System.out.println("CONTENT=" + content);
                return content;
            }
            return "400";
        } catch (Exception e) {
            System.out.println("function:SendGet_client error in class Spider");
            e.printStackTrace();
        }
        return ret;
    }

    // 执行队列，并获取web中的内容
    public ArrayList<WebEntity> getWebList(String url) {
        int times = 0;

        ArrayList<WebEntity> retList = new ArrayList<WebEntity>();
        ArrayList<String> webUrlList = new ArrayList<String>();
        // init
        SpiderQueue queue = new SpiderQueue();

        queue.addUnvisitedUrl(url);
        // timse次数为所要访问的页面数量
        while (!queue.unVisitedUrlsEmpty() && times <= 600) {

            url = (String) queue.unVisitedUrlDequeue();

            String content = SendGet_client(url);
            if (content.equals("400"))
                continue;
            queue.addVisiteUrl(url);
            webUrlList.addAll(getWebUrl(content, getUrl_question));
            webUrlList.addAll(getWebUrl(content, getUrl_author));
            webUrlList.addAll(getWebUrl(content, getUrl_content));
            webUrlList.addAll(getWebUrl(content, getUrl_editable));
            webUrlList.addAll(getWebUrl(content, getUrl_visible));
            WebEntity tmp = new WebEntity(url);

            for (String urlTmp : webUrlList) {
                queue.addUnvisitedUrl(urlTmp);
                tmp.addOutUrl(urlTmp);
            }
            retList.add(tmp);
            System.out.println("times is :" + times++);
            webUrlList.clear();
        }
        webUrlList.clear();
        return retList;
    }

    // 获取web中的url
    public ArrayList<String> getWebUrl(String content, String reg) {
        ArrayList<String> urlList = new ArrayList<String>();

        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(content);
        while (m.find()) {
            if (m.group(1).indexOf("http") >= 0) {
                urlList.add(m.group(1));
                // System.out.println("spider(97) get:"+m.group(1));
            } else {
                urlList.add("http://www.zhihu.com" + m.group(1));
                // System.out.println("spider(97) get:http://www.zhihu.com"+m.group(1));
            }
        }
        return urlList;
    }

    // 传入的url是否存在于retLIst中
    public int webUrlContain(ArrayList<WebEntity> retList, String url) {
        int count = -1, len = retList.size();
        for (int i = 0; i < len; i++) {
            if (retList.get(i).getUrl().equals(url)) {
                count = i;
                break;
            }
        }
        return count;
    }
}
