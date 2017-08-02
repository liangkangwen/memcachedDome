package org.lkw.dao;

import java.io.Serializable;

public interface IBaseDao<T> {
	/**
	 * 保存对象
	 * @param object 对象
	 */
	public void save(Object object);
	
	/**
	 * 修改对象
	 * @param object 对象
	 */
	public void update(Object object);
	
	/**
	 * 删除对象
	 * @param object 对象
	 */
	public void delete(Object object);
	
	/**
	 * 获取对象
	 * @param id 序列化ID
	 */
	public T getObject(Serializable id);
}
