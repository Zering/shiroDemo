package c3;

import java.util.Arrays;

import org.apache.shiro.subject.Subject;
import org.junit.Assert;

/**
 * 角色权限验证
 * 
 * @author zhanghaojie
 *
 */
public class RoleTest extends BaseTest{

	public void hasRole(){
		Subject subject = login("classpath:c3/shiro-role.ini","zhang","123");
		Assert.assertTrue(subject.hasRole("role1"));
		Assert.assertTrue(subject.hasAllRoles(Arrays.asList("role1","role2")));
		boolean[] result = subject.hasRoles(Arrays.asList("role1","role2","role3"));
		Assert.assertTrue(result[0]);
		Assert.assertTrue(result[1]);
		Assert.assertFalse(result[2]);
		
		subject.checkRole("role1");
//		subject.checkRoles("role1",);
	}
}
