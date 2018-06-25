package com.software.dm.swallow.stormy.ssm.sys.entity;

import org.springframework.util.StringUtils;

import java.util.Arrays;

public class User {

    private String id;
    private String username;
    private String birthday;
    private String sex;
    private String address;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", birthday='" + birthday + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public static void main(String[] args) {
        String[] vals = StringUtils.tokenizeToStringArray("com.software.dm;.swallow.stormy.,ssm. sys.dto.**", ",; \t\n");

        System.out.println(Arrays.toString(vals));
    }
}
