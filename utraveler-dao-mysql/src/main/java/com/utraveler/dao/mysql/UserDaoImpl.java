package com.utraveler.dao.mysql;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.utraveler.dao.UserDao;
import com.utraveler.dao.entity.UserEntity;
import com.utraveler.dao.mysql.common.BaseDao;
import com.utraveler.dao.mysql.entity.UserEntityImpl;

@Repository("userDao")
public class UserDaoImpl extends BaseDao<UserEntity> implements UserDao {


    @Override
    public UserEntity findUserByEmail(String email) {
        Query query = getSession().getNamedQuery("User.findUserByEmail");
        query.setParameter("email", email);
        List users = query.list();
        return (users.size() == 1) ? (UserEntity)users.get(0) : null;
    }


    @Override
    public boolean isEmailExist(String email) {
        Query query = getSession().getNamedQuery("User.isEmailExists");
        query.setParameter("email", email);
        return ((Long)query.uniqueResult()).intValue() > 0;
    }


    @Override
    public UserEntity getById(long id) {
        return (UserEntity)getSession().get(UserEntityImpl.class, id);
    }

}
