package answer;

import answer.account.base.Account;
import answer.account.base.Loanable;
import answer.account.credit.CreditAccount;
import answer.account.saving.SavingAccount;
import answer.bank.Bank;
import answer.exception.BalanceNotEnoughException;
import answer.exception.LoanException;
import answer.exception.LoginException;
import answer.exception.RegisterException;

import java.util.Scanner;

/**
 * 银行系统测试菜单类
 */
public class BankMenu {
	/** 管理员编号 */
	private static final long ADMIN_ID = 100000L;

	/**
	 * 银行号菜单主方法
	 *
	 * @param args 参数
	 */
	public static void main(String[] args) {
		Bank bank = Bank.getInstance();
		// 初始化一个管理员账户
		Account admin = new SavingAccount();
		admin.setId(ADMIN_ID);
		admin.setPersonId("admin");
		admin.setName("admin");
		admin.setPassword("admin");
		bank.accountMap.put(ADMIN_ID, admin);

		// 启动欢迎界面
		welcomeMenu(bank);
	}

	/**
	 * 欢迎界面
	 *
	 * @param bank 银行
	 */
	private static void welcomeMenu(Bank bank) {
		while (true) {
			Account acc = null;
			System.out.println("---欢迎使用银行账户信息管理系统---");
			System.out.println("---1.登录---");
			System.out.println("---2.注册---");
			System.out.println("---9.退出---");
			Scanner sc = new Scanner(System.in);
			String choose = sc.nextLine();

			switch (choose) {
				case "1":
					// 登录
					System.out.println("---请输入以下信息---");
					System.out.println("账户编号：");
					long id = sc.nextLong();
					System.out.println("账户密码：");
					String loginPW = sc.next();
					try {
						acc = bank.loginAccount(id, loginPW);
					} catch (LoginException e) {
						System.err.println(e.getMessage());
					}
					if (acc != null) {
						System.out.println("登录成功，欢迎" + acc.getName());
						if (acc.getId() == ADMIN_ID) {
							// 管理员
							adminMenu(bank);
						} else {
							// 客户
							accountMenu(bank, acc);
						}
					} else {
						continue;
					}

					break;
				case "2":
					// 注册
					System.out.println("---请输入以下信息---");
					System.out.println("账户密码：");
					String password = sc.next();
					System.out.println("确认密码：");
					String password2 = sc.next();
					System.out.println("姓    名：");
					String name = sc.next();
					System.out.println("身份证号：");
					String personId = sc.next();
					System.out.println("-请选择账户类型-");
					System.out.println("-0.储蓄账户-");
					System.out.println("-1.信用卡账户-");
					System.out.println("-2.可贷款储蓄账户-");
					System.out.println("-3.可贷款信用卡账户-");
					int type = sc.nextInt();

					try {
						acc = bank.registerAccount(password, password2, name, personId, type);
					} catch (RegisterException e) {
						System.err.println(e.getMessage());
					}
					if (acc != null) {
						System.out.println("注册成功，欢迎" + acc.getName() + " 账户编号:" + acc.getId());
						// 启动客户界面
						accountMenu(bank, acc);
					} else {
						continue;
					}

					break;
				case "9":
					// 退出
					System.exit(0);
				default:
					// 其他
					System.out.println("---请选择正确的操作编号---");
			}
		}

	}

	/**
	 * 管理员界面
	 *
	 * @param bank 银行
	 */
	private static void adminMenu(Bank bank) {

		while (true) {
			System.out.println("---请选择以下的操作---");
			System.out.println("---1.查看所有账户余额总数---");
			System.out.println("---2.查看所有账户已透支额总数---");
			System.out.println("---3.查看所有账户已贷款额总数---");
			System.out.println("---4.设置账户透支额度---");
			System.out.println("---5.查看总资产排名---");
			System.out.println("---9.返回---");

			Scanner sc = new Scanner(System.in);
			String choose = sc.nextLine();
			switch (choose) {
				case "1":
					// 查看所有账户余额总数
					System.out.println("当前所有账户余额总数为：" + bank.getAllBalance() + "元");
					break;
				case "2":
					// 查看所有账户已透支额总数
					System.out.println("当前所有账户已透支额总数为：" + bank.getAllCeiling() + "元");
					break;
				case "3":
					// 查看所有账户已贷款额总数
					System.out.println("当前所有账户已贷款总数为：" + bank.getAllLoan() + "元");
					break;
				case "4":
					// 设置账户透支额度
					System.out.println("---请输入以下信息---");
					System.out.println("账户编号：");
					long id = sc.nextLong();
					System.out.println("新透支额度：");
					double ceiling = sc.nextDouble();
					bank.setAccountCeiling(id, ceiling);
					break;
				case "5":
					// 查看总资产排名
					System.out.println("---总资产排名---");
					bank.sortByBalance();
					break;
				case "9":
					// 返回
					return;
				default:
					System.out.println("请选择正确的操作编号");
			}
		}
	}

	/**
	 * 客户操作界面
	 *
	 * @param bank 银行
	 * @param acc  当前账户
	 */
	private static void accountMenu(Bank bank, Account acc) {

		while (true) {
			System.out.println("---请选择以下的操作---");
			System.out.println("---1.存款---");
			System.out.println("---2.取款---");
			System.out.println("---3.查看账户余额---");
			System.out.println("---4.贷款---");
			System.out.println("---5.还贷---");
			System.out.println("---6.还透支款---");
			System.out.println("---9.返回---");

			Scanner sc = new Scanner(System.in);
			String choose = sc.nextLine();
			switch (choose) {

				case "1":
					// 存款
					System.out.println("---请输入以下信息---");
					System.out.println("存款金额：");
					double depositAmount = sc.nextDouble();
					bank.depositAccount(acc.getId(), depositAmount);

					break;
				case "2":
					// 取款
					System.out.println("---请输入以下信息---");
					System.out.println("取款金额：");
					double withdrawAmount = sc.nextDouble();
					try {
						bank.withdrawAccount(acc.getId(), withdrawAmount);
					} catch (BalanceNotEnoughException e) {
						System.err.println(e.getMessage());
					}

					break;
				case "3":
					// 查看账户余额
					System.out.println("账户余额：" + acc.getBalance() + "元");
					if (acc instanceof CreditAccount) {
						System.out.println("信用余额：" + ((CreditAccount) acc).getCeilBalance() + "元");
					}
					if (acc instanceof Loanable) {
						System.out.println("贷款总额：" + ((Loanable) acc).getLoan() + "元");
					}

					break;
				case "4":
					// 贷款
					System.out.println("---请输入以下信息---");
					System.out.println("贷款金额：");
					double loanAmount = sc.nextDouble();
					try {
						bank.rquestLoanAccount(acc.getId(), loanAmount);
					} catch (LoanException e) {
						System.err.println(e.getMessage());
					}

					break;
				case "5":
					// 还贷
					System.out.println("---请输入以下信息---");
					System.out.println("还贷金额：");
					double payLoanAmount = sc.nextDouble();
					try {
						bank.payLoanAccount(acc.getId(), payLoanAmount);
					} catch (BalanceNotEnoughException e) {
						System.err.println(e.getMessage());
					}

					break;
				case "6":
					// 还透支款
					System.out.println("---请输入以下信息---");
					System.out.println("还款金额：");
					double ceiledAmount = sc.nextDouble();
					bank.payAccountCeiled(acc.getId(), ceiledAmount);

					break;
				case "9":
					// 返回
					return;
				default:
					System.out.println("请选择正确的操作编号");
			}
		}
	}
}
