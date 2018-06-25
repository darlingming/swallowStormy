package com.software.dm.swallow.stormy.ssm.sys.service;

import com.software.dm.swallow.stormy.ssm.sys.entity.User;

import java.util.List;


public interface UserService {
    List<User> getUsers(Integer userId);
    void insertUsers(User user) throws Exception;
}
