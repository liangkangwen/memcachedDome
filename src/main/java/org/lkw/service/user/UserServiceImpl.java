package org.lkw.service.user;

import java.io.Serializable;

import org.lkw.dao.user.IUserDao;
import org.lkw.entity.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Lazy
public class UserServiceImpl implements IUserService<UserPO> {
	
	@Autowired
	private IUserDao userDaoImpl;
	

	@Override
	public void save(UserPO userPO) {
		userDaoImpl.save(userPO);
	}

	@Override
	public UserPO get(Serializable id) {
		return userDaoImpl.getObject(id);
	}

	@Override
	public void update(UserPO userPO) {
		userDaoImpl.update(userPO);
	}

}
