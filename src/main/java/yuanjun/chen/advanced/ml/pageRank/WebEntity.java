package yuanjun.chen.advanced.ml.pageRank;
 
import java.util.ArrayList;
/*
 * code by 邦柳
 * 
 * web页面对应类
 * */
public class WebEntity {
 
	public ArrayList<String> outUrl;
	public String Url;
 
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	public WebEntity(String url){
		outUrl=new ArrayList<String>();
		Url=url;
	}
 
	public int outUrlNum(){
		return outUrl.size();
	}
 
	public void addOutUrl(String url){
		outUrl.add(url);
	}
 
	public boolean outContians(String url){
		return outUrl.contains(url);
	}
}
