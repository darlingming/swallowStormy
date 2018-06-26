package com.software.dm.swallow.stormy.ssm.sys.controller;


import com.software.dm.swallow.stormy.ssm.sys.entity.SysUser;
import com.software.dm.swallow.stormy.ssm.sys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserActionController {

    public static final Logger logger = LoggerFactory.getLogger(UserActionController.class);

    @Autowired
    private UserService userService;


    // 查询所属用户列表
    @RequestMapping(value = "query")
    public String showUserList(HttpServletRequest request,
                               HttpServletResponse response) {
        logger.info("======login===========");
         List<SysUser> userList = this.userService.queryAllUser();
        logger.info(Arrays.toString(userList.toArray()));

        return "/login";
    }

    // 查询所属用户列表
    @RequestMapping(value = "insert")
    public String insUserList(HttpServletRequest request,
                              HttpServletResponse response) {
        logger.info("======insUserList===========");
        try {
            this.userService.queryAllUser();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "/login";
    }

    //返回jsp视图展示
    @RequestMapping(value = "/getUserModel", method = RequestMethod.GET)
    public ModelAndView getUsers1(@RequestParam Integer userId) {
        ModelAndView modelAndView = new ModelAndView();
        //调用service方法得到用户列表
        //List<User> users = userService.getUsers(userId);
        ////将得到的用户列表内容添加到ModelAndView中
        //modelAndView.addObject("users", users);
        ////设置响应的jsp视图
        //modelAndView.setViewName("getUsers");
        logger.info("===============================成功查询用户列表！");
        return modelAndView;
    }

    //返回json格式数据，形式1
    @RequestMapping(value = "/getUserJson1", method = RequestMethod.GET)
    @ResponseBody
    public SysUser getUsers2(@RequestParam String userId, HttpServletRequest request, HttpServletResponse response) {
        //调用service方法得到用户列表
        SysUser  users = userService.queryUser(userId);
        logger.info("===============================成功查询用户列表！");
        return users;
    }
    //返回json格式数据，形式2（自定义了返回的格式）
    //@RequestMapping(value = "/getUserJson2",method = RequestMethod.GET)
    //public void getUsers3(@RequestParam Integer userId, HttpServletRequest request, HttpServletResponse response) {
    //    //调用service方法得到用户列表
    //    List<User> users = userService.getUsers(userId);
    //    logger.info("===============================成功查询用户列表！");
    //    renderSuccessString(response, users);
    //}
}
