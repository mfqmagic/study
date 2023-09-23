package answer.account.base;

import answer.exception.BalanceNotEnoughException;
import answer.exception.LoanException;

/**
 * 可以贷款接口
 */
public interface Loanable {
	/**
	 * 贷款方法
	 *
	 * @param amount 贷款金额
	 * @throws LoanException 贷款异常
	 */
	void requestLoan(double amount) throws LoanException;

	/**
	 * 还贷方法
	 *
	 * @param amount 还款金额
	 * @throws BalanceNotEnoughException 余额不足异常
	 */
	void payLoan(double amount) throws BalanceNotEnoughException;

	/**
	 * 获取贷款总额
	 *
	 * @return 贷款总额
	 */
	double getLoan();
}

