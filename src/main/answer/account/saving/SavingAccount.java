package answer.account.saving;

import answer.account.base.Account;
import answer.exception.BalanceNotEnoughException;

import java.text.MessageFormat;

/**
 * 储蓄账户类
 */
public class SavingAccount extends Account {
	/**
	 * 默认构造方法
	 */
	public SavingAccount() {
	}

	/**
	 * 储蓄账户初始化
	 *
	 * @param password 账户密码
	 * @param name     真实姓名
	 * @param personId 身份证号码
	 * @param balance  账户余额
	 */
	public SavingAccount(long id, String password, String name, String personId, double balance) {
		super(id, password, name, personId, balance);
	}

	/**
	 * 重写取款
	 *
	 * @param amount 取出金额
	 * @throws BalanceNotEnoughException 余额不足异常
	 */
	public void withdraw(double amount) throws BalanceNotEnoughException {
		if (amount < 0) {
			System.out.println("取款金额不能小于0");
		} else if (amount <= this.getBalance()) {
			this.setBalance(this.getBalance() - amount);
			System.out.println(MessageFormat.format("账户：{0}，成功取款{1}，当前账户余额{2}元",
					this.getId(), amount, this.getBalance()));
		} else {
			throw new BalanceNotEnoughException("账户余额不足！");
		}
	}
}
