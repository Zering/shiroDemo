package c3;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;

/**
 * 通用
 * 
 * @author zhanghaojie
 *
 */
public class BaseTest {

	public void teardown(){
		ThreadContext.unbindSubject();
	}
	
	protected Subject login(String conf,String username,String password){
		Factory<SecurityManager> factory = new IniSecurityManagerFactory(conf);
		SecurityManager manager = factory.getInstance();
		SecurityUtils.setSecurityManager(manager);
		Subject subject = SecurityUtils.getSubject();
		AuthenticationToken token = new UsernamePasswordToken(username, password);
		subject.login(token);
		return subject;
	}
	
}
