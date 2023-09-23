package answer.exception;

/**
 * 登录异常类
 */
public class LoginException extends Exception {
	/**
	 * 默认构造方法
	 */
	public LoginException() {
	}

	/**
	 * 初始化登录异常
	 *
	 * @param message 错误消息
	 */
	public LoginException(String message) {
		super(message);
	}
}
