package org.lkw.dao;

import java.io.Serializable;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.lkw.utils.GenericsUtils;


@SuppressWarnings("unchecked")
public class BaseDaoImpl<T> implements IBaseDao<T> {
	
protected Class<T> entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());
	
	@Resource
	protected SessionFactory factory;
	
	private Session getOpenSession() {
		return factory.openSession();
	}

	@Override
	public void save(Object object) {
		getOpenSession().save(object);
		
	}

	@Override
	public void update(Object object) {
		getOpenSession().update(object);
	}

	@Override
	public void delete(Object object) {
		getOpenSession().delete(object);
	}

	@Override
	public T getObject(Serializable id) {
		return (T) getOpenSession().get(entityClass, id);
	}

}
