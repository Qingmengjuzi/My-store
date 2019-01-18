package cn.itcast.store.dao.Impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.sun.org.apache.bcel.internal.generic.NEW;

import cn.itcast.store.dao.UserDao;
import cn.itcast.store.domain.User;
import cn.itcast.store.utils.JDBCUtils;

public class UserDaoImp implements UserDao {

	@Override
	public void userRegist(User user) throws SQLException {
	//创建sql语句
		String sql="INSERT INTO USER VALUES(?,?,?,?,?,?,?,?,?,?)";
		Object[] params={user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode()};
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		qr.update(sql,params);
	}

	@Override
	public User userActive(String code) throws SQLException{
		String sql = "select * from user where code=?";
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		User user = qr.query(sql, new BeanHandler<User>(User.class),code);
		return user;
	}

	@Override
	public void updateUser(User user) throws SQLException {

		String sql="UPDATE USER SET username= ? ,PASSWORD=? ,NAME =? ,email =? ,telephone =? , birthday =?  ,sex =? ,state= ? , CODE = ? WHERE uid=?";
		Object[] params={user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode(),user.getUid()};
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		qr.update(sql,params);
		
	}

}
