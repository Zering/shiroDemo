package com.shiro.c6;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Before;
import org.springframework.jdbc.core.JdbcTemplate;

import com.shiro.c6.entity.Permission;
import com.shiro.c6.entity.Role;
import com.shiro.c6.entity.User;
import com.shiro.c6.service.IPermissionService;
import com.shiro.c6.service.IRoleService;
import com.shiro.c6.service.IUserService;
import com.shiro.c6.service.impl.PermissionSerivceImpl;
import com.shiro.c6.service.impl.RoleServiceImpl;
import com.shiro.c6.service.impl.UserServiceImpl;
import com.shiro.c6.util.JdbcTemplateUtil;

public abstract class BaseTest {

	protected IUserService userService = new UserServiceImpl();
	protected IRoleService roleService = new RoleServiceImpl();
	protected IPermissionService permissionService = new PermissionSerivceImpl();

	protected String password = "123";
	protected JdbcTemplate jdbcTemplate = JdbcTemplateUtil.template();

	protected User user1;
	protected User user2;
	protected User user3;
	protected User user4;
	protected Role role1;
	protected Role role2;
	protected Permission p1;
	protected Permission p2;
	protected Permission p3;

	@Before
	public void setup() {
		jdbcTemplate.update("delete from sys_users");
		jdbcTemplate.update("delete from sys_users_roles");
		jdbcTemplate.update("delete from sys_roles");
		jdbcTemplate.update("delete from sys_roles_permissions");
		jdbcTemplate.update("delete from sys_permissions");

		// 1.新增权限
		p1 = new Permission("user:create", "用户模块新增", Boolean.TRUE);
		p2 = new Permission("user:update", "用户模块修改", Boolean.TRUE);
		p3 = new Permission("menu:create", "菜单模块新增", Boolean.TRUE);
		permissionService.createPermission(p1);
		permissionService.createPermission(p2);
		permissionService.createPermission(p3);
		// 2.添加角色
		role1 = new Role("admin", "系统管理员", Boolean.TRUE);
		role2 = new Role("user", "用户管理员", Boolean.TRUE);
		roleService.createRole(role1);
		roleService.createRole(role2);
		// 3.添加角色权限
		roleService.correlationPermissions(role1.getId(), p1.getId(), p2.getId(), p3.getId());
		roleService.correlationPermissions(role2.getId(), p1.getId(), p2.getId());

		// 4.新增用户
		user1 = new User("zhang", password);
		user2 = new User("li", password);
		user3 = new User("wu", password);
		user4 = new User("wang", password);
		user4.setLocked(Boolean.TRUE);
		userService.createUser(user1);
		userService.createUser(user2);
		userService.createUser(user3);
		userService.createUser(user4);
		// 5.添加用户角色
		userService.correlationRoles(user1.getId(), role1.getId());

	}

	@After
	public void tearDown() throws Exception {
		ThreadContext.unbindSubject();// 退出时请解除绑定Subject到线程 否则对下次测试造成影响
	}

	protected Subject login(String configFile, String username, String password) {
		// 1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
		Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);
		// 2、得到SecurityManager实例 并绑定给SecurityUtils
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		// 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		subject.login(token);
		return subject;
	}

}
