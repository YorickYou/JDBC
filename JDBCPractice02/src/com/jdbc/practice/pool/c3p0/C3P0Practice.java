package com.jdbc.practice.pool.c3p0;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import com.jdbc.practice.utils.JDBCUtilsNew;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Practice {
	//����c3p0���ӳ�,�����ڷ�����
	@Test
	public void test() throws PropertyVetoException, SQLException{
		//����c3p0���ӳض���
		ComboPooledDataSource db = new ComboPooledDataSource();
		//���ò���
		db.setDriverClass("com.mysql.jdbc.Driver");
		db.setUser("root");
		db.setPassword("123");
		db.setJdbcUrl("jdbc:mysql://localhost:3306/test1");
		//��ȡ����
		Connection conn = db.getConnection();
		System.out.println(conn.getClass().getName());
		//�黹����
		conn.close();
	}
	//����c3p0���ӳ�,�����������ļ���
	@Test
	public void test1() throws SQLException{
		//�������ӳض���
		ComboPooledDataSource db = new ComboPooledDataSource();
		/**������������ò���,��ô�Ḳ�������ļ�
		 * ���ò���
		db.setDriverClass("com.mysql.jdbc.Driver");
		db.setUser("root");
		db.setPassword("123");
		db.setJdbcUrl("jdbc:mysql://localhost:3306/test1");
		 */
		//��ȡ����
		Connection conn = db.getConnection();
		System.out.println(conn.getClass().getName());
		//�黹����
		conn.close();
	}
	//ʹ��c3p0�����ļ�,��������
	@Test
	public void test3() throws SQLException{
		ComboPooledDataSource db = new ComboPooledDataSource("orcale");
		Connection conn = db.getConnection();
		System.out.println(conn.getClass().getName());
		//�黹����
		conn.close();
	}
	//��Ϊ�������ӳ�,�������ǿ��Ը������ǵ�JDBCUtils������,���������в���
	@Test
	public void test4() throws SQLException{
		Connection conn = JDBCUtilsNew.getConnection();
		System.out.println(conn.getClass().getName());
	}
}