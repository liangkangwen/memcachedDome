package org.lkw.dao.user;

import java.io.Serializable;

import org.lkw.dao.IBaseDao;
import org.lkw.entity.UserPO;

public interface IUserDao extends IBaseDao<UserPO> {
	
	public UserPO getUser(Serializable id);

}
