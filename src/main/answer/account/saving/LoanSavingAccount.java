package answer.account.saving;

import answer.account.base.Loanable;
import answer.exception.BalanceNotEnoughException;
import answer.exception.LoanException;

import java.text.MessageFormat;

/**
 * 贷款储蓄账户类
 */
public class LoanSavingAccount extends SavingAccount implements Loanable {
	/** 贷款额 */
	private double loan;

	/**
	 * 默认构造方法
	 */
	public LoanSavingAccount() {
	}

	/**
	 * 贷款储蓄账户初始化
	 *
	 * @param password 密码
	 * @param name     密码确认
	 * @param personId 身份证
	 * @param balance  余额
	 */
	public LoanSavingAccount(long id, String password, String name, String personId, double balance) {
		super(id, password, name, personId, balance);
	}

	/**
	 * 重写获取贷款额
	 *
	 * @return 贷款额
	 */
	@Override
	public double getLoan() {
		return loan;
	}

	/**
	 * 重写还款
	 *
	 * @param amount 还款金额
	 * @throws BalanceNotEnoughException 余额不足
	 */
	@Override
	public void payLoan(double amount) throws BalanceNotEnoughException {
		if (amount < 0) {
			System.out.println("还款额必须大于0");
		} else if (amount > loan) {
			System.out.println("还款金额超出贷款金额，请重新操作");
		} else if (amount <= this.getBalance()) {
			// 还贷能力之内
			this.setBalance(this.getBalance() - amount);

			loan -= amount;
			System.out.println(MessageFormat.format("账户：{0}，成功还贷{1}，当前账户余额{2}元，剩余贷款额{3}元",
					this.getId(), amount, this.getBalance(), loan));
		} else {
			throw new BalanceNotEnoughException("账户余额不足，无法还贷");
		}
	}

	/**
	 * 贷款
	 *
	 * @param amount 贷款金额
	 * @throws LoanException 贷款异常
	 */
	@Override
	public void requestLoan(double amount) throws LoanException {
		if (amount >= 0) {
			loan += amount;
			System.out.println(MessageFormat.format("账户：{0}，成功贷款{1}元，上次贷款额{2}元，当前贷款总额{3}元",
					this.getId(), amount, (loan - amount), loan));
		} else {
			throw new LoanException("请输入大于0的贷款金额");
		}
	}

}
