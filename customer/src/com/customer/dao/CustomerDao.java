package com.customer.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.customer.domain.Customer;
import com.customer.domain.PageBean;

import cn.itcast.jdbc.TxQueryRunner;

/**
 * 数据层
 * @author Yorick
 *
 */
public class CustomerDao {
	//创建QueryRunner对象
	private QueryRunner qr = new TxQueryRunner();
	/**
	 * 插入用户
	 */
	public void add(Customer cstm){
		try {
			//给出SQL模板
			String insert = "insert into customer values(?,?,?,?,?,?,?)";
			//给出SQL模板中对应的问号的值
			Object[] objects = {cstm.getCid(),cstm.getCname(),cstm.getGender(),cstm.getBirthday()
					,cstm.getCellphone(),cstm.getEmail(),cstm.getDescription()};
			//更新数据库
			qr.update(insert,objects);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 查询所有客户
	 */
//	public List<Customer> findAll(){
//		try {
//			//SQL模板
//			String select = "select * from customer";
//			return qr.query(select, new BeanListHandler<Customer>(Customer.class));
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
//	}
	/**
	 * 分页查询
	 * 我们在list页面发现,会将数据库中,所有的用户信息都显示出来,但如果数据库中有几亿条数据,此时怎么做呢,分页或许是最好的手段
	 */
	//传入参数为每页记录数和当前页码
	public PageBean<Customer> findAll(int cp,int ps){
		try {
			//得到所有记录的数目
			String sql = "select count(*) from customer";
			Number num = (Number) qr.query(sql, new ScalarHandler());
			int tr = num.intValue();
			//得到当前页面数据
			sql = "select * from customer limit ?,?";
			//第一页,显示第1到第10条数据,第二页是11到20,(pagenumber-1)*10
			Object[] objects = {(cp-1)*ps,ps};
			List<Customer> beans = qr.query(sql, new BeanListHandler<Customer>(Customer.class),objects);
			/*
			 * 3. 使用tr、beans、cp、ps创建PageBean，返回！
			 */
			PageBean<Customer> pb = new PageBean<>();
			pb.setBeans(beans);
			pb.setCp(cp);
			pb.setTr(tr);
			pb.setPs(ps);
			return pb;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 按cid查询客户
	 */
	public Customer load(String cid){	
		try {
			//SQL模板
			String select = "select * from customer where cid=?";
			return qr.query(select, new BeanHandler<Customer>(Customer.class),cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 修改用户
	 */
	public void edit(Customer cstm){
		try {
			String update = "update customer set cname=?,gender=?,birthday=?,cellphone=?" +
					", email=?, description=? where cid=?";
			Object[] params = {cstm.getCname(), cstm.getGender(),
				cstm.getBirthday(), cstm.getCellphone(), cstm.getEmail(),
				cstm.getDescription(), cstm.getCid()};
			qr.update(update, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 删除用户信息
	 */
	public void delete(String cid){
		try {
			String delete = "delete from customer where cid=?";
			qr.update(delete, cid);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * 多条件组合查询
	 */
	public PageBean<Customer> query(int cp,int ps,Customer cstm){
		try {
			/*
			 * 1. 拼凑sql模板
			 * 2. 给出?对应的值
			 * 3. 调用qr的query方法，使用BeanListHandler
			 */
			//拼凑SQL模板
			//创建List存储?
			List<Object> params = new ArrayList<Object>();
			StringBuilder sb= new StringBuilder("from customer where 1=1");
			// 判断条件中是否包含cname，如果包含添加条件子句！
			String cname = cstm.getCname();
			if(cname!=null && !cname.trim().isEmpty()){
				sb.append(" and cname like ?");
				params.add("%"+cname+"%");//当这个问号存在时，添加对应的值到params中
			}
			// 判断条件中是否包含gender，如果包含添加条件子句！
			String gender = cstm.getGender();
			if(gender!=null && !gender.trim().isEmpty()){
				sb.append("and gender=?");
				params.add(gender);
			}
			// 判断条件中是否包含cellphone，如果包含添加条件子句！
			String cellphone = cstm.getCellphone();
			if(cellphone != null && !cellphone.trim().isEmpty()){
				sb.append("and cellphone like ?");
				params.add("%"+cellphone+"%");
			}
			// 判断条件中是否包含email，如果包含添加条件子句！
			String email = cstm.getEmail();
			if(email!=null && !email.trim().isEmpty()){
				sb.append("and email like ?");
				params.add("%"+email+"%");
			}
			// 拼凑count语句，用来得到总记录数
			StringBuilder cnt = new StringBuilder("select count(*)").append(sb);
			Number num = (Number)qr.query(cnt.toString(), new ScalarHandler(), params.toArray());
			int tr = num.intValue();
			// 拼凑查询当前页记录的SQL语句
			StringBuilder sql = new StringBuilder("select *").append(sb);
			sql.append(" limit ?,?");
			// 向参数中添加两个值：
			params.add((cp-1)*ps);
			params.add(ps);
			// 查询
			List<Customer> list = qr.query(sql.toString(), new BeanListHandler<Customer>(Customer.class), params.toArray());
			/*
			 * 创建PageBean
			 */
			PageBean<Customer> pb = new PageBean<Customer>();
			pb.setCp(cp);
			pb.setPs(ps);
			pb.setTr(tr);
			pb.setBeans(list);
			return pb;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
		
	}
	
}
