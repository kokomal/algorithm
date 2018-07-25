/**
 * @Title: UnsupportedTypeException.java
 * @Package: yuanjun.chen.base.exception
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: 陈元俊
 * @date: 2018年7月25日 上午9:39:37
 * @version V1.0
 * @Copyright: 2018 All rights reserved.
 */
package yuanjun.chen.base.exception;

/**
 * @ClassName: UnsupportedTypeException
 * @Description: 生成随机参数时抛出不支持类型的异常
 * @author: 陈元俊
 * @date: 2018年7月25日 上午9:39:37
 */
public class UnsupportedTypeException extends Exception {

    /**
     * @Fields serialVersionUID : 生成随机参数时抛出不支持类型的异常
     */
    private static final long serialVersionUID = 1L;

    private String msg;

    /**
     * @param msg
     */
    public UnsupportedTypeException(String msg) {
        super(msg);
        this.setMsg(msg);
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
