package answer.bank;

import answer.account.base.Account;
import answer.account.base.Loanable;
import answer.account.credit.CreditAccount;
import answer.account.credit.LoanCreditAccount;
import answer.account.saving.LoanSavingAccount;
import answer.account.saving.SavingAccount;
import answer.exception.BalanceNotEnoughException;
import answer.exception.LoanException;
import answer.exception.LoginException;
import answer.exception.RegisterException;

import java.util.*;

import static java.util.stream.Collectors.toMap;

/**
 * 银行类
 */
public class Bank {
	/** 账户集合 */
	public final Map<Long, Account> accountMap = new HashMap<>();

	/**
	 * 单例：在程序运行过程中，只能有一个类的实例（只能为这个类创建一个对象）
	 * 1、声明一个类的变量，该变量是类变量，用static修饰
	 * 2、把构造方法设为private
	 * 3、创建一个获取类实例的静态方法，访问权限public，返回值是这个类的对象
	 */
	private static Bank bank = null;

	/**
	 * 默认构造方法
	 */
	private Bank() {
	}

	/**
	 * 获取银行对象
	 *
	 * @return 银行对象
	 */
	public static Bank getInstance() {
		if (bank == null) {
			bank = new Bank();
		}
		return bank;
	}

	/**
	 * 用户开户
	 *
	 * @param password  密码
	 * @param password2 密码确认
	 * @param name      姓名
	 * @param personId  身份证号码
	 * @param type      账户类型（0：储蓄，1；信用，2；贷款储蓄，3；贷款信用）
	 * @return 账户
	 * @throws RegisterException 注册异常
	 */
	public Account registerAccount(String password, String password2,
								   String name, String personId, int type) throws RegisterException {
		Account acc;

		// 当两次密码输入一致时，可以进行账户开户
		if (Objects.equals(password, password2)) {
			switch (type) {
				case 0:
					acc = new SavingAccount();
					break;
				case 1:
					acc = new CreditAccount();
					break;
				case 2:
					acc = new LoanSavingAccount();
					break;
				case 3:
					acc = new LoanCreditAccount();
					break;
				default:
					throw new RegisterException("账户类型不存在！");
			}
			// 为账户属性赋值
			acc.setId(acc.getNextId());
			acc.setName(name);
			acc.setPassword(password);
			acc.setPersonId(personId);

			// 将用户加入Bank
			accountMap.put(acc.getId(), acc);
		} else {
			throw new RegisterException("密码不能为空，或两次密码输入不一致");
		}

		return acc;
	}

	/**
	 * 用户登录
	 *
	 * @param id       账户编号
	 * @param password 密码
	 * @return 账户
	 * @throws LoginException 登录异常
	 */
	public Account loginAccount(long id, String password) throws LoginException {
		Account acc = null;

		// 查看账户是否存在
		if (accountMap.containsKey(id)) {
			acc = accountMap.get(id);
		}

		if (acc == null) {
			throw new LoginException("账户不存在！");
		} else if (!Objects.equals(acc.getPassword(), password)) {
			throw new LoginException("密码输入错误");
		}

		return acc;
	}

	/**
	 * 用户存款
	 *
	 * @param id     账户编号
	 * @param amount 存款金额
	 * @return 账户
	 */
	public Account depositAccount(long id, double amount) {
		Account acc = null;

		// 查看账户是否存在
		if (accountMap.containsKey(id)) {
			acc = accountMap.get(id);
			// 存款
			acc.deposit(amount);
		} else {
			System.out.println("存入账户不存在");
		}

		return acc;
	}

	/**
	 * 用户取款
	 *
	 * @param id     账户编号
	 * @param amount 取款金额
	 * @return 账户
	 * @throws BalanceNotEnoughException 余额不足异常
	 */
	public Account withdrawAccount(long id, double amount) throws BalanceNotEnoughException {
		Account acc = null;

		// 查看账户是否存在
		if (accountMap.containsKey(id)) {
			acc = accountMap.get(id);
			// 取款
			acc.withdraw(amount);
		} else {
			System.out.println("取款账户不存在");
		}

		return acc;
	}

	/**
	 * 请求贷款
	 *
	 * @param id     账户编号
	 * @param amount 贷款金额
	 * @return 账户
	 * @throws LoanException 贷款异常
	 */
	public Account rquestLoanAccount(long id, double amount)
			throws LoanException {
		Account acc = null;

		// 查看账户是否存在
		if (accountMap.containsKey(id)) {
			acc = accountMap.get(id);
			// 此账户是贷款账户的情况下
			if (acc instanceof Loanable) {
				((Loanable) acc).requestLoan(amount);
			} else {
				System.out.println("账户:" + id + "无贷款权限");
			}
		} else {
			System.out.println("贷款账户不存在或无贷款权限！");
		}

		return acc;
	}

	/**
	 * 还贷款
	 *
	 * @param id     账户编号
	 * @param amount 还贷款的金额
	 * @return 账户
	 * @throws BalanceNotEnoughException 余额不足异常
	 */
	public Account payLoanAccount(long id, double amount) throws BalanceNotEnoughException {
		Account acc = null;

		// 查看账户是否存在
		if (accountMap.containsKey(id)) {
			acc = accountMap.get(id);
			// 判断账户是否为可贷款的账户
			if (acc instanceof Loanable) {
				((Loanable) accountMap.get(id)).payLoan(amount);
			} else {
				System.out.println("账户:" + id + "无贷款权限");
			}
		} else {
			System.out.println("账户不存在！");
		}

		return acc;
	}

	/**
	 * 设置透支额度
	 *
	 * @param id      账户编号
	 * @param ceiling 透支额度
	 * @return 账户
	 */
	public Account setAccountCeiling(long id, double ceiling) {
		Account acc = null;

		// 判断账户是否存在
		if (accountMap.containsKey(id)) {
			acc = accountMap.get(id);
			// 判断账户是否有透支权限
			if (acc instanceof CreditAccount) {
				((CreditAccount) acc).setCeiling(ceiling);
			} else {
				System.out.println("该账户不是信用卡账户");
			}
		} else {
			System.out.println("输入账户不存在");
		}

		return acc;
	}

	/**
	 * 还透支款
	 *
	 * @param id     账户编号
	 * @param amount 还款额
	 * @return 账户
	 */
	public Account payAccountCeiled(long id, double amount) {
		Account acc = null;

		// 判断账户是否存在
		if (accountMap.containsKey(id)) {
			acc = accountMap.get(id);
			// 判断账户是否是信用账户
			if (acc instanceof CreditAccount) {
				((CreditAccount) acc).payCeil(amount);
			} else {
				System.out.println("该账户不是信用卡账户");
			}
		} else {
			System.out.println("输入账户不存在");
		}

		return acc;
	}

	/**
	 * 统计银行所有账户余额总数
	 *
	 * @return 所有账户总余额
	 */
	public double getAllBalance() {
		double allBalance = 0;

		// 遍历银行账户Map
		for (Map.Entry<Long, Account> entry : accountMap.entrySet()) {
			Account acc = entry.getValue();
			allBalance += acc.getBalance();
		}

		return allBalance;
	}

	/**
	 * 统计所有信用账户的透支总额
	 *
	 * @return 所有信用账户透支总额
	 */
	public double getAllCeiling() {
		double allCeiled = 0;

		// 遍历银行账户Map
		for (Map.Entry<Long, Account> entry : accountMap.entrySet()) {
			Account acc = entry.getValue();
			// 判断账户是否为信用账户
			if (acc instanceof CreditAccount) {
				allCeiled += ((CreditAccount) acc).getCeiled();
			}
		}

		return allCeiled;
	}

	/**
	 * 统计所有账户贷款总额
	 *
	 * @return 所有账户的贷款总额
	 */
	public double getAllLoan() {
		double allLoan = 0;

		// 遍历银行账户Map
		for (Map.Entry<Long, Account> entry : accountMap.entrySet()) {
			Account acc = entry.getValue();
			// 判断账户是否为可贷款账户
			if (acc instanceof Loanable) {
				// 获取某个账户贷款额
				allLoan += ((Loanable) acc).getLoan();
			}
		}

		return allLoan;
	}

	/**
	 * 获取某身份证号下所有账户的余额
	 *
	 * @param personId 身份证
	 * @return 总余额
	 */
	private double getBalanceByPersonId(String personId) {
		double personBalance = 0;

		// 遍历银行账户Map
		for (Map.Entry<Long, Account> entry : accountMap.entrySet()) {
			Account acc = entry.getValue();
			// 判断是否为要统计的身份证号
			if (Objects.equals(acc.getPersonId(), personId)) {
				personBalance += acc.getBalance();
			}
		}

		return personBalance;
	}

	/**
	 * 打印所有用户的总资产排名
	 */
	public void sortByBalance() {
		// 所有账户的身份证号
		Set<String> personIdSet = new HashSet<>();

		// 遍历银行账户Map
		for (Map.Entry<Long, Account> entry : accountMap.entrySet()) {
			Account acc = entry.getValue();
			String personId = acc.getPersonId();
			personIdSet.add(personId);
		}

		// 所有身份证号账户的余额
		Map<String, Double> balanceByPersonMap = new HashMap<>();
		for (String personId : personIdSet) {
			balanceByPersonMap.put(personId, getBalanceByPersonId(personId));
		}

		// 按照余额排序
		Map<String, Double> sortByBalanceMap = balanceByPersonMap.entrySet().stream()
				.sorted(Map.Entry.<String, Double>comparingByValue().reversed())        // 按值降序排序
				.collect(
						toMap(Map.Entry::getKey, Map.Entry::getValue,                   // 转成LinkedHashMap
								(e1, e2) -> e2, LinkedHashMap::new
						)
				);

		// 打印排名
		int num = 1;
		for (Map.Entry<String, Double> entry : sortByBalanceMap.entrySet()) {
			System.out.println(num + "." + entry.getKey() + "的资产余额为：" + entry.getValue());
			num++;
		}
	}

}
