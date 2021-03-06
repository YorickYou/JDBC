package com.jdbc.practice.business.service;

import java.sql.SQLException;

import org.junit.Test;

public class AccountService {
	private AccountDao dao = new AccountDao();
	/**
	 * 转账功能
	 * @param from
	 * @param to
	 * @param money
	 * 
	 */
	public void zz(String from,String to,double money){
		try {
			//开启事务
			JdbcUtils.beginTransaction();
			dao.update(from, money);
			dao.update(to, -money);
			//提交事务
			JdbcUtils.commitTransaction();
		} catch (Exception e) {
			//进行到这里,说明要回滚事务
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	@Test
	public void test(){
		zz("zs", "ls", 10000);
	}
}
