package org.lkw.web.aop;

import java.io.IOException;
import java.io.StringWriter;
import java.net.SocketAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.lkw.utils.Telnet;
import org.springframework.beans.factory.annotation.Autowired;

import net.spy.memcached.MemcachedClient;


public class CacheInteceptor {
	@Autowired
	private MemcachedClient memcachedClient;
	
	public static final int TIMEOUT = 360000;//秒
	
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		//去Memcached中看看有没有我们的数据  包名+ 类名 + 方法名 + 参数(多个)
				String cacheKey = getCacheKey(pjp);
				System.out.println(cacheKey);
				//如果Memcached 连接不上呢
				if(memcachedClient.getStats().isEmpty()){
					System.out.println("Memcached服务器可能不存在或是连接不上");
					return pjp.proceed();
				}
				
				//返回值
				if(null == memcachedClient.get(cacheKey)){
					//回Service
					Object proceed = pjp.proceed();
					//先放到Memcached中一份
					memcachedClient.set(cacheKey, TIMEOUT,proceed);
				}
				return memcachedClient.get(cacheKey);
	}
	
	/**
	 * 后置由于数据库数据变更  清理get*
	 * @param jp
	 */
	public void doAfter(JoinPoint jp){
		//包名+ 类名 
		String packageName = jp.getTarget().getClass().getName();
		Map<SocketAddress, Map<String, String>> keyMap = memcachedClient.getStats();
		Set<SocketAddress> addressSet = keyMap.keySet();
		Iterator<SocketAddress> it = addressSet.iterator();
		while (it.hasNext()) {
			SocketAddress addr = it.next();
			String[] allkeys = Telnet.allkeys(addr);
			for (String key : allkeys) {
				if (key.startsWith(packageName)) {
					memcachedClient.delete(key);
				}
			}
			/*Map<String, String> stat = keyMap.get(addr);
			Set<String> keys = stat.keySet();
			Iterator<String> key = keys.iterator();
			while (key.hasNext()) {
				String keyVal = key.next();
				//包名+ 类名  开始的 都清理
				if (keyVal.startsWith(packageName)) {
					memcachedClient.delete(keyVal);
				}
			}*/
		}
	}
	
	/**
	 * 获取 memcached 的 key 值
	 * @param pjp
	 * @return
	 */
	private String getCacheKey(ProceedingJoinPoint pjp) {
		StringBuffer cacheKey = new StringBuffer();
		//取得  包名 + 类名
		String packageName = pjp.getTarget().getClass().getName();
		cacheKey.append(packageName);
		//取得方法名
		String methodName = pjp.getSignature().getName();
		cacheKey.append(".").append(methodName);
		//取得所有参数
		Object[] args = pjp.getArgs();
		ObjectMapper  om = new ObjectMapper();
		om.setSerializationInclusion(Inclusion.NON_NULL);
		for (Object arg : args) {
			StringWriter strWriter = new StringWriter();
			try {
				om.writeValue(strWriter, arg);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			cacheKey.append(".").append(strWriter);
		}
		return cacheKey.toString();
	}

}
