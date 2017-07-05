package com.jdbc.practice.pool.DBCP;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.junit.Test;

public class DBCPPractice {
	@Test
	public void test() throws SQLException{
		//����DBCP���ӳض���
		BasicDataSource dbsc = new BasicDataSource();
		//��������
		dbsc.setUsername("root");
		dbsc.setPassword("123");
		dbsc.setUrl("jdbc:mysql://localhost:3306/test1");
		dbsc.setDriverClassName("com.mysql.jdbc.Driver");
		//�������ӳز���
		dbsc.setMaxActive(20);//���������
		dbsc.setMaxIdle(10);//������������
		dbsc.setMaxWait(1000);//���ȴ�ʱ��
		dbsc.setMinIdle(2);//��С������
		//ʹ�����ӳ�,��ȡ����
		Connection conn = dbsc.getConnection();
		System.out.println(conn.getClass().getName());
		//�黹����
		conn.close();
		
	}
}