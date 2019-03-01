/**  
 * @Title: NoblePerson.java   
 * @Package: yuanjun.chen.performance.jdk8   
 * @Description: TODO(用一句话描述该文件做什么)   
 * @author: 陈元俊     
 * @date: 2018年12月6日 下午12:49:33   
 * @version V1.0 
 * @Copyright: 2018 All rights reserved. 
 */
package yuanjun.chen.performance.jdk8;

import java.util.List;

/**   
 * @ClassName: NoblePerson   
 * @Description: TODO(这里用一句话描述这个类的作用)   
 * @author: 陈元俊 
 * @date: 2018年12月6日 下午12:49:33  
 */
public class NoblePerson {
    String name;
    List<Hobby> hobbyList;
    public List<Hobby> getHobbyList() {
        return hobbyList;
    }
    public void setHobbyList(List<Hobby> hobbyList) {
        this.hobbyList = hobbyList;
    }
    
}
