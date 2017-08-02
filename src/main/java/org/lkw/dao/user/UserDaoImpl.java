package org.lkw.dao.user;

import java.io.Serializable;

import org.lkw.dao.BaseDaoImpl;
import org.lkw.entity.UserPO;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Lazy
public class UserDaoImpl extends BaseDaoImpl<UserPO> implements IUserDao {

	@Override
	public UserPO getUser(Serializable id) {
		return super.getObject(id);
	}

}
