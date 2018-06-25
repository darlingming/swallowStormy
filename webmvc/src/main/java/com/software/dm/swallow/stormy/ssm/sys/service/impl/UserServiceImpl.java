package com.software.dm.swallow.stormy.ssm.sys.service.impl;

import com.software.dm.swallow.stormy.ssm.sys.dto.UserDao;
import com.software.dm.swallow.stormy.ssm.sys.entity.User;
import com.software.dm.swallow.stormy.ssm.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public List<User> getUsers(Integer userId) {
        return userDao.getUsers(userId);
    }


    public void insertUsers(User user) throws Exception {
         User user1 = new User();
         user1.setId("1");
         user1.setUsername("dm");
         user1.setAddress("eeeeee");
          this.userDao.insert(user1);
        user1.setId("2");
        user1.setUsername("dm2");
        user1.setAddress("eeeeee2");
        this.userDao.insert(user1);

    }


}
