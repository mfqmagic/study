package answer.exception;

/**
 * 注册异常类
 */
public class RegisterException extends Exception {
	/**
	 * 默认构造方法
	 */
	public RegisterException() {
	}

	/**
	 * 初始化注册异常
	 *
	 * @param message 错误消息
	 */
	public RegisterException(String message) {
		super(message);
	}
}
