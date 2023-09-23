package answer.account.credit;

import answer.account.base.Account;
import answer.exception.BalanceNotEnoughException;

import java.text.MessageFormat;

/**
 * 信用账户
 */
public class CreditAccount extends Account {
	/** 可透支额度 */
	private double ceiling = 2000;
	/** 已透支金额 */
	private double ceiled;

	/**
	 * 默认构造方法
	 */
	public CreditAccount() {
	}

	/**
	 * 信用账户初始化
	 *
	 * @param password 账户密码
	 * @param name     真实姓名
	 * @param personId 身份证号码
	 * @param balance  账户余额
	 * @param ceiling  可透支额度
	 */
	public CreditAccount(long id, String password, String name, String personId, double balance, double ceiling) {
		super(id, password, name, personId, balance);
		this.ceiling = ceiling;
	}

	/**
	 * 重写取款
	 *
	 * @param amount 取出金额
	 * @throws BalanceNotEnoughException 余额不足异常
	 */
	@Override
	public void withdraw(double amount) throws BalanceNotEnoughException {
		if (amount < 0) {
			// 取款额为0
			System.out.println("取款金额不能小于0");
		} else if (amount <= (this.getBalance() + getCeilBalance())) {
			// 如果余额与可透支额大于取款额，可以取款
			if (amount <= this.getBalance()) {
				// 取款额 小于 余额
				this.setBalance(this.getBalance() - amount);
			} else {
				// 取款额 大于 余额
				ceiled += (amount - this.getBalance());
				this.setBalance(0);
			}
			System.out.println(MessageFormat.format("账户：{0}，成功取款{1}，当前账户余额{2}元，已透支{3}元，仍可透支{4}元",
					this.getId(), amount, this.getBalance(), ceiled, getCeilBalance()));
		} else {
			throw new BalanceNotEnoughException("账户余额不足！");
		}
	}

	/**
	 * 透支款还款
	 *
	 * @param amount 还款金额
	 */
	public void payCeil(double amount) {
		if (amount < 0) {
			//还款额不能为负数
			System.out.println("还款额不能小于0");
		} else {
			if (amount <= ceiled) {
				//还款额少于已透支额
				ceiled -= amount;
			} else {
				//还款额多于透支额
				this.setBalance(this.getBalance() + amount - ceiled);
				ceiled = 0;
			}
			System.out.println(MessageFormat.format("账户：{0}，成功还款{1}，当前账户余额{2}元，已透支{3}元，仍可透支{4}元",
					this.getId(), amount, this.getBalance(), ceiled, getCeilBalance()));
		}
	}

	/**
	 * 获取信用余额
	 *
	 * @return 信用余额
	 */
	public double getCeilBalance() {
		return ceiling - ceiled;
	}

	/**
	 * @return get the ceiling
	 */
	public double getCeiling() {
		return ceiling;
	}

	/**
	 * @param ceiling the ceiling to set
	 */
	public void setCeiling(double ceiling) {
		this.ceiling = ceiling;
	}

	/**
	 * @return get the ceiled
	 */
	public double getCeiled() {
		return ceiled;
	}

	/**
	 * @param ceiled the ceiled to set
	 */
	public void setCeiled(double ceiled) {
		this.ceiled = ceiled;
	}
}
