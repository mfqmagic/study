package answer.exception;

/**
 * 余额不足异常类
 */
public class BalanceNotEnoughException extends Exception {
	/**
	 * 默认构造方法
	 */
	public BalanceNotEnoughException() {
	}

	/**
	 * 初始化余额不足异常
	 *
	 * @param message 错误消息
	 */
	public BalanceNotEnoughException(String message) {
		super(message);
	}
}
