package com.software.dm.swallow.stormy.ssm.sys.service.impl;

import com.software.dm.swallow.stormy.ssm.sys.dto.SysUserMapper;
import com.software.dm.swallow.stormy.ssm.sys.entity.SysUser;
import com.software.dm.swallow.stormy.ssm.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    public SysUser queryUser(String id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    public List<SysUser> queryAllUser() {
        return sysUserMapper.getAll();
    }


    public void insertUsers(SysUser user) throws Exception {
        SysUser user1 = new SysUser();
        user1.setId("1");
        user1.setUserName("dm");
        user1.setUserArea("eeeeee");
        this.sysUserMapper.insert(user1);
        user1.setId("2");

        this.sysUserMapper.insert(user1);

    }


}
