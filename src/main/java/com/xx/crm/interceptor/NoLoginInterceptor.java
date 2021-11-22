package com.xx.crm.interceptor;

import com.xx.crm.dao.UserMapper;
import com.xx.crm.exceptions.NoLoginException;
import com.xx.crm.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName NoLoginInterceptor
 * @Description
 *      拦截用户是否是登录状态
 *          preHandle：在目标方法（目标资源）执行前，执行的方法
 *
 *       方法返回布尔类型：
 *            如果返回true，表示目标方法可以被执行
 *            如果返回false，表示阻止目标方法执行
 *
 *        如何判断用户是否是登录状态：
 *              1.判断cookie中是否存在用户信息（获取用户id）
 *              2.数据库中是否存在指定用户id的值
 *       如果用户是登录状态，则允许目标方法执行；如果用户是非登录状态，则抛出未登录异常（在全局异常中做判断，如果是非登录状态，则跳转到登录界面
 * @Author xu
 * @Date 2021/10/23 16:50
 * @Version 1.0
 */
public class NoLoginInterceptor extends HandlerInterceptorAdapter {
    //注入UserMapper
    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取cookie中的用户id
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        //判断用户id是否为空，且数据库中存在该id的用户记录
        if (null == userId || userMapper.selectByPrimaryKey(userId) == null){
            //抛出未登录异常
            throw  new NoLoginException();
        }

        return true;
    }
}
