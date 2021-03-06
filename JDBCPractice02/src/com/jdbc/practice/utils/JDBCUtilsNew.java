package com.jdbc.practice.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtilsNew {
	//获取数据库连接池对象
	private static DataSource ds = new ComboPooledDataSource();
	//返回连接池对象
	public static DataSource getDataSource(){
		return ds;
	}
	//返回连接
	public static Connection getConnection() throws SQLException{
		return ds.getConnection();
	}
}
