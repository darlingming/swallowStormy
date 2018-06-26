package com.software.dm.swallow.stormy.ssm.sys.service;


import com.software.dm.swallow.stormy.ssm.sys.entity.SysUser;

import java.util.List;

/**
 *
 */
public interface UserService {
    List<SysUser> queryAllUser();

    SysUser  queryUser(String id);
    void insertUsers(SysUser user) throws Exception;
}
