package answer.account.credit;

import answer.account.base.Loanable;
import answer.exception.BalanceNotEnoughException;
import answer.exception.LoanException;

import java.text.MessageFormat;

/**
 * 贷款信用账户
 */
public class LoanCreditAccount extends CreditAccount implements Loanable {
	/** 贷款额 */
	private double loan;

	/**
	 * 默认构造方法
	 */
	public LoanCreditAccount() {
	}

	/**
	 * 贷款账户初始化
	 *
	 * @param password 账户密码
	 * @param name     真实姓名
	 * @param personId 身份证号码
	 * @param balance  账户余额
	 * @param ceiling  可透支额度
	 */
	public LoanCreditAccount(long id, String password, String name, String personId, double balance, double ceiling) {
		super(id, password, name, personId, balance, ceiling);
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
	 * 重写还贷款
	 *
	 * @param amount 还款金额
	 * @throws BalanceNotEnoughException 余额不足
	 */
	@Override
	public void payLoan(double amount) throws BalanceNotEnoughException {
		if (amount < 0) {
			System.out.println("还款金额不能小于0");
		} else if (amount > loan) {
			System.out.println("还款额大于贷款额，请重新输入");
		} else if (amount <= this.getBalance() + this.getCeilBalance()) {
			// 还款额 不大于 账户余额和透支额
			if (amount <= this.getBalance()) {
				// 还款额 不大于 余额
				this.setBalance(this.getBalance() - amount);
			} else {
				// 还款额 大于 余额，但是可以透支还款
				this.setBalance(0);
				this.setCeiled(amount - this.getBalance() + this.getCeiled());
			}

			loan -= amount;
			System.out.println(MessageFormat.format("账户：{0}，成功还贷{1}，当前账户余额{2}元，剩余贷款额{3}元",
					this.getId(), amount, this.getBalance(), loan));
		} else {
			// 余额与贷款额不足
			throw new BalanceNotEnoughException("账户余额不足无法还款");
		}

	}

	/**
	 * 请求贷款
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
			throw new LoanException("贷款额不能为负数");
		}
	}

}
