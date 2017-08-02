package org.lkw.test;

import org.junit.Test;
import org.lkw.dao.user.IUserDao;
import org.lkw.entity.UserPO;
import org.springframework.beans.factory.annotation.Autowired;

import net.spy.memcached.MemcachedClient;

public class MemcachedTest extends SpringJunitTest {
	
	@Autowired
	private MemcachedClient memcachedClient;
	@Autowired
	private IUserDao userDaoImpl;
	
	
	@Test
	public void putValue() throws Exception {
		//InetSocketAddress address = new InetSocketAddress("192.168.206.128",11211);
		//MemcachedClient client = new MemcachedClient(address);
		UserPO userPO = new UserPO();
		userPO.setId(3);
		userPO.setName("黎明");
		userDaoImpl.update(userPO);
		//memcachedClient.set("user_2", 1000, userPO);
	}
	
	@Test
	public void getValue() throws Exception {
		//InetSocketAddress address = new InetSocketAddress("192.168.206.128",11211);
		//MemcachedClient client = new MemcachedClient(address);
		//String value = (String) memcachedClient.get("1");
		//UserPO user = (UserPO) memcachedClient.get("user_2");
		//System.out.println(value + "   " + user);
		
		userDaoImpl.getObject(3);
	}
	
	@Test
	public void memTest() {
		UserPO value = (UserPO) memcachedClient.get("org.lkw.dao.user.UserDaoImpl.getObject.3");
		System.out.println(value);
	}
	
}
