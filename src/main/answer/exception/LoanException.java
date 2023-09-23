package answer.exception;

/**
 * 贷款异常类
 */
public class LoanException extends Exception {
	/**
	 * 默认构造方法
	 */
	public LoanException() {
	}

	/**
	 * 初始化贷款异常
	 *
	 * @param message 错误消息
	 */
	public LoanException(String message) {
		super(message);
	}
}
