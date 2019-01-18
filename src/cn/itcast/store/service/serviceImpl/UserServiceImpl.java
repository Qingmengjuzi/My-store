package cn.itcast.store.service.serviceImpl;

import java.sql.SQLException;

import cn.itcast.store.dao.UserDao;
import cn.itcast.store.dao.Impl.UserDaoImp;
import cn.itcast.store.domain.User;
import cn.itcast.store.service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public void userRegist(User user) throws SQLException {
		// 实现注册功能
		UserDao userDao = new UserDaoImp();
		userDao.userRegist(user);

	}

	@Override
	public boolean userActive(String code) throws SQLException {
		// 实现注册功能
		UserDao userDao = new UserDaoImp();
		// 对DB发送select * from user where code=?
		User user = userDao.userActive(code);

		if (null != user) {
			// 可以根据激活码查询到一个用户
			// 修改用户的状态，清除激活码
			user.setState(1);
			user.setCode(null);
			// 对数据库执行一次真实的更新操作
			userDao.updateUser(user);
			return true;

		} else {
			// 不可以根据激活码查询到一个用户
			return false;

		}

	}
}
