package cn.itcast.store.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.User;
import cn.itcast.store.service.UserService;
import cn.itcast.store.service.serviceImpl.UserServiceImpl;
import cn.itcast.store.utils.MailUtils;
import cn.itcast.store.utils.MyBeanUtils;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.web.base.BaseServlet;

public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
 
	// userRegist
	public String userRegist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.接收表单参数
		Map<String, String[]> map = request.getParameterMap();
		User user = new User();
		MyBeanUtils.populate(user, map);
		// 2.为用户其他属性赋值
		user.setUid(UUIDUtils.getId());
		user.setState(0);// 默认状态值
		user.setCode(UUIDUtils.getCode());// 激活码
		System.out.println(user);

		// 遍历map中的数据
		/*
		 * Set<String> keySet = map.keySet(); Iterator<String> iterator =
		 * keySet.iterator(); while(iterator.hasNext()) { String str = iterator.next();
		 * String[] strs = map.get(str); for(String string :strs) {
		 * System.out.println(string); } }
		 */
		// 调用业务层注册功能
		UserService userService = new UserServiceImpl();
		try {
			userService.userRegist(user);
			// 注册成功，向邮件发送信息，跳转到提示页面
			// 发送邮件
			MailUtils.sendMail(user.getEmail(), user.getCode());
			request.setAttribute("msg", "用户注册成功，请激活！");
			return "/jsp/login.jsp";
		} catch (Exception e) {
			request.setAttribute("msg", "用户注册失败，请重新注册！");
			// 注册失败，跳转到提示页面
			return "/jsp/info.jsp";
		}

	}
	public String active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// 1.获取激活码
		String code = request.getParameter("code");
		//调用业务层激活功能
		UserService userService = new UserServiceImpl();
		boolean flag = userService.userActive(code);
		if (flag) {
			//用户激活成功，向request放入提示信息，转发到登录页面
			request.setAttribute("msg", "用户激活成功，请登录！");
			return "/jsp/login.jsp";
			
		}else {
			//用户激活失败，向request放入提示信息，转发到登录页面
			request.setAttribute("msg", "用户激活失败，请重新激活！");
			return "/jsp/info.jsp";
		
		}
	 

	}
}
