package com.software.dm.swallow.stormy.ssm.sys.dto;

import com.software.dm.swallow.stormy.ssm.sys.entity.User;

import java.util.List;


public interface UserDao {

    List<User> getUsers(Integer userId);

    public List<User> selectAll();

    void insert(User user);
}
