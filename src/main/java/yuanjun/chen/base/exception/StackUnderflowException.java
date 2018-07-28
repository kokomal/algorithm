/**
 * 
 */
package yuanjun.chen.base.exception;

/**
 * @author hp
 *
 */
public class StackUnderflowException extends Exception {
    private static final long serialVersionUID = 1L;

    private String msg;

	/**
	 * @param msg
	 */
	public StackUnderflowException(String msg) {
		super();
		this.msg = msg;
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
