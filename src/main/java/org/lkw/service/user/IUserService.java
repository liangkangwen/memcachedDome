package org.lkw.service.user;

import java.io.Serializable;

public interface IUserService<UserPO> {
	
	void save(UserPO userPO);
	
	UserPO get(Serializable id);
	
	void update(UserPO userPO);
}
