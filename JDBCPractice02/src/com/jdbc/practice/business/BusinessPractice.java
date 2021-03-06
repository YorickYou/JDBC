package com.jdbc.practice.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;

import com.jdbc.practice.utils.JDBCUtils;

public class BusinessPractice {

	@Test
	public void businessTest(){
		/*
		 * try外给引用
		 * try内实例化
		 * finally来关闭
		 */
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//获取连接
			conn = JDBCUtils.getConnection();
			//开启事务
			conn.setAutoCommit(false);
			/*3. 修改zs，给他减100
			 * 4. 修改ls，给他加100
			 * */
			String update = "update account set balance=balance+? where name=?";
			pstmt = conn.prepareStatement(update);
			pstmt.setDouble(1, -100);
			pstmt.setString(2, "zs");
			pstmt.executeUpdate();
			pstmt.setDouble(1, 100);
			pstmt.setString(2, "ls");
			pstmt.executeUpdate();
			//判断如果失败抛出运行时异常,否则提交事务
			if(false){
				throw new RuntimeException();
			}
			//事务提交
			conn.commit();
		} catch (SQLException e) {
			//如果进行到这里说明上边出现异常,此时要回滚
			if(conn!=null){
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}finally{
			
				try {
					if(conn!=null) conn.close();
					if(pstmt!=null) pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
