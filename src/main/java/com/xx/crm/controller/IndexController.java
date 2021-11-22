package com.xx.crm.controller;

import com.xx.crm.service.UserService;
import com.xx.crm.utils.LoginUserUtil;
import com.xx.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.xx.crm.base.BaseController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName IndexController
 * @Description TODO
 * @Author xu
 * @Date 2021/10/22 10:37
 * @Version 1.0
 */
@Controller
public class IndexController extends BaseController {

    @Resource
    private UserService userService;
    /**
     * 系统登录页
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "index";
    }
    // 系统界面欢迎页
    @RequestMapping("welcome")
    public String welcome(){
        return "welcome";
    }


    /**
     * 后端管理主页面
     * ${(user.userName)!""}前端获得用户名
     * request作用域一次使用后失效，所以使用Session作用域
     * @return
     */
    @RequestMapping("main")
    public String main(HttpServletRequest request){

        //获取cookie中的用户id
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        //查询用户对象，设置session作用域
        User user = userService.selectByPrimaryKey(userId);
        request.getSession().setAttribute("user",user);

        return "main";
    }
}

