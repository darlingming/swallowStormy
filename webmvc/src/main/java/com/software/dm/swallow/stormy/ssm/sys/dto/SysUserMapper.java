package com.software.dm.swallow.stormy.ssm.sys.dto;

import com.software.dm.swallow.stormy.ssm.sys.entity.SysUser;

import java.util.List;


public interface SysUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PLATFORM.T_SYS_USER
     *
     * @mbg.generated Tue Jun 26 16:36:32 CST 2018
     */

    int deleteByPrimaryKey(String loginUserName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PLATFORM.T_SYS_USER
     *
     * @mbg.generated Tue Jun 26 16:36:32 CST 2018
     */
    int insert(SysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PLATFORM.T_SYS_USER
     *
     * @mbg.generated Tue Jun 26 16:36:32 CST 2018
     */
    int insertSelective(SysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PLATFORM.T_SYS_USER
     *
     * @mbg.generated Tue Jun 26 16:36:32 CST 2018
     */
    SysUser selectByPrimaryKey(String loginUserName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PLATFORM.T_SYS_USER
     *
     * @mbg.generated Tue Jun 26 16:36:32 CST 2018
     */
    int updateByPrimaryKeySelective(SysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PLATFORM.T_SYS_USER
     *
     * @mbg.generated Tue Jun 26 16:36:32 CST 2018
     */
    int updateByPrimaryKey(SysUser record);


    List<SysUser> getAll();
}