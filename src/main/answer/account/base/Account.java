package answer.account.base;

import answer.exception.BalanceNotEnoughException;

import java.util.Objects;

/**
 * 账户抽象类
 */
public abstract class Account {
	/** 自动生成序列号 */
	private static long num = 100000;

	/** 账户编号 */
	private long id;
	/** 账户密码 */
	private String password;
	/** 真实姓名 */
	private String name;
	/** 身份证号码 */
	private String personId;
	/** 账户余额 */
	private double balance;

	/**
	 * 默认构造方法
	 */
	public Account() {
	}

	/**
	 * 有参构造方法
	 *
	 * @param id       账户编号
	 * @param password 账户密码
	 * @param name     真实姓名
	 * @param personId 身份证号码
	 * @param balance  账户余额
	 */
	public Account(long id, String password, String name, String personId, double balance) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.personId = personId;
		this.balance = balance;
	}

	/**
	 * 自动生成账号
	 *
	 * @return 自动生成账号
	 */
	public long getNextId() {
		return ++num;
	}

	/**
	 * 获取余额
	 *
	 * @return 余额
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * 存款
	 *
	 * @param amount 存入金额
	 */
	public final void deposit(double amount) {
		balance += amount;
	}

	/**
	 * 取款
	 *
	 * @param amount 取出金额
	 * @throws BalanceNotEnoughException 余额不足异常
	 */
	public abstract void withdraw(double amount) throws BalanceNotEnoughException;

	/**
	 * @return get the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return get the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return get the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return get the personId
	 */
	public String getPersonId() {
		return personId;
	}

	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(String personId) {
		this.personId = personId;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * 重写toString方法
	 *
	 * @return 账户属性字符串
	 */
	@Override
	public String toString() {
		return "账户号码：" + id + " 账户名：" + name + " 余额：" + balance;
	}

	/**
	 * 重写equal方法，判断两个账户是否为同一账户
	 *
	 * @param obj 比较的对象
	 * @return 是否相同
	 */
	@Override
	public boolean equals(Object obj) {
		// 比较对象为空
		if (obj == null) {
			return false;
		}
		// 比较对象的地址和当前对象相同
		if (obj == this) {
			return true;
		}

		// 比较对象不是同一个类型账户
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		// 比较对象是同一类型
		Account acc = (Account) obj;
		return Objects.equals(acc.personId, this.personId);
	}

}
