package com.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import com.jdbc.practice.utils.JDBCUtilsNew;

/**
 * ��DBUtils��,�����Ѿ�����QueryRunner��update����,���򵥿��˲�ѯ,����,�������������о�һ��query����
 * @author Yorick
 */
public class DBUtilsQueryTest {
	//���в�ѯ
	@Test
	public void queryTest() throws SQLException{
		String select = "select * from user where username=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtilsNew.getDataSource());
		ResultSetHandler<User> rsh = new ResultSetHandler<User>() {
			
			@Override
			public User handle(ResultSet rs) throws SQLException {
				//�ѽ����װ����User����
				rs.next();
				return new User(rs.getString(1), rs.getString("username"), rs.getString("password"));
			}
		};
		/*
		 * 1. sqlģ��
		 * 2. �����������
		 * 3. ��Ӧģ���е��ʺ�
		 * 1. ִ�в�ѯ���õ�RequestSet
		 * 2. ʹ��ResultSetȥ����ResultSetHandler��handle()��������ResultSet���ݸ���
		 * �õ�User��Ȼ�󷵻�User
		 */
		User user = queryRunner.query(select, rsh,"½ѷ");
		System.out.println(user);
	}
	/*
	 * BeanHandler�����н���������������ѽ������װ��һ��JavaBean�����У�
	 *   1. ����Ҫһ��Class��ͨ��Class����ʵ��
	 *   2. �����������������ƣ��������������ƵĶ�Ӧ��ϵ��ɷ�װ��
	 */
	@Test
	public void queryTest1() throws SQLException{
		String select ="select * from user where username=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtilsNew.getDataSource());
		User user = queryRunner.query(select, new BeanHandler<User>(User.class) ,"�����");
		System.out.println(user);
	}
	/*
	 * BeanListHandler�����н��������������һ�н������Ӧһ��JavaBean���󣬶��оͶ�Ӧһ��List
	 *   1. ����Ҫһ��Class��ͨ��Class����ʵ��
	 *   2. �����������������ƣ��������������ƵĶ�Ӧ��ϵ��ɷ�װ��
	 */
	@Test
	public void queryListTest() throws SQLException{
		String select = "select * from user";
		QueryRunner queryRunner = new QueryRunner(JDBCUtilsNew.getDataSource());
		List<User> list = queryRunner.query(select, new BeanListHandler<>(User.class));
		for (User user : list) {
			System.out.println(user);
		}
	}
	/*
	 * MapHandler�����н����������������һ�н������װ��һ��map��
	 * * key��������
	 * * value���е�ֵ
	 */
	@Test
	public void queryMapTest() throws SQLException{
		String select = "select * from user where username=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtilsNew.getDataSource());
		Map<String, Object> map = queryRunner.query(select, new MapHandler(), "����");
		System.out.println(map.get("uid"));
	}
	/*
	 * MapListHandler�����н����������������һ�н������װ��һ��Map�У����о��Ƕ��Map������List<Map>
	 */
	@Test
	public void queryMapListHandler() throws SQLException{
		String select = "select * from user";
		QueryRunner queryRunner = new QueryRunner(JDBCUtilsNew.getDataSource());
		List<Map<String, Object>> list = queryRunner.query(select, new MapListHandler());
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	/*
	 * ScalarHandler�����е��н��������������ͨ������select count(*) from ...��ѯ
	 */
	@Test
	public void queryTest2() throws SQLException{
		/*
		 * count(*)�����������ݿ���������汾�йأ�
		 * mysql-3.0.7����������Ϊint
		 * mysql-5.x������Long
		 * orcale10g������BigInteger
		 */
		String select = "select count(*) from user";
		QueryRunner queryRunner = new QueryRunner(JDBCUtilsNew.getDataSource());
		Object value = queryRunner.query(select, new ScalarHandler());
		Number count = (Number) value;
		int c = count.intValue();
		System.out.println(c);
	}
}