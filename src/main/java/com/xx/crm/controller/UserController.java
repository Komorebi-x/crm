package com.xx.crm.controller;

import com.xx.crm.base.BaseController;
import com.xx.crm.base.ResultInfo;
import com.xx.crm.exceptions.ParamsException;
import com.xx.crm.model.UserModel;
import com.xx.crm.service.UserService;
import com.xx.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName UserController
 * @Description 用户登录
 * 流程：前台传入用户名和密码，为Ajax请求，我们需要在方法上加@ResponseBody表示当前会返回一个JSON给客户端
 *      然后用户名和密码传给Service层，让Service层做各种判断，判断时可能要做数据库的查询，调用dao层即可
 *      判断完后将返回结果给Controller， Controller需要知道返回结果和登录信息，所以返回一个用户对象      Service层可能会出现异常，返回给Controller层用try catch捕获
 * @Author xu
 * @Date 2021/10/22 13:55
 * @Version 1.0
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController{

    @Resource
    private UserService userService;

    @PostMapping("login")
    @ResponseBody

    public ResultInfo userLogin(String userName, String userPwd){

        ResultInfo resultInfo = new ResultInfo();

        //调用service层方法
        UserModel userModel = userService.userLogin(userName,userPwd);

        /**
         * 登录成功后，有两种处理：
         * 1. 将用户的登录信息存入 Session （ 问题：重启服务器，Session 失效，客户端
         需要重复登录 ）
         * 2. 将用户信息返回给客户端，由客户端（Cookie）保存
         */

        //设置ResultInfo的result的值（将数据返回给请求）
        resultInfo.setResult(userModel);

/*        // 通过try catch 捕获service层的异常，如果service层抛出异常，则表示登录失败，否则登录
        try{

        }catch (ParamsException p){// 自定义异常
            resultInfo.setCode(p.getCode());
            resultInfo.setMsg(p.getMsg());
            p.printStackTrace();
        }catch (Exception e){
            resultInfo.setCode(500);
            resultInfo.setMsg("登录失败！");
            e.printStackTrace();
        }*/

        //返回目标对象
        return resultInfo;

    }

    @PostMapping("updatePwd")
    @ResponseBody
    public ResultInfo updateUserPassword(HttpServletRequest request,
                                         String oldPassword,String newPassword,String repeatPassword){
        ResultInfo resultInfo = new ResultInfo();

        try{
            //获取cookie中userId
            Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
            //调用Service层修改密码方法
            userService.updatePassWord(userId,oldPassword,newPassword,repeatPassword);

        }catch (ParamsException p){
            resultInfo.setCode(p.getCode());
            resultInfo.setMsg(p.getMsg());
            p.printStackTrace();
        }catch (Exception e){
            resultInfo.setCode(500);
            resultInfo.setMsg("修改密码失败！");
            e.printStackTrace();
        }

        return resultInfo;
    }

    /**
     * 进入修改密码的界面
     * @return
     */
    @RequestMapping("toPasswordPage")
    public String toPasswordPage(){

        return "user/password";
    }
}
