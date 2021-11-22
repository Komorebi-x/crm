package com.xx.crm.dao;

import com.xx.crm.base.BaseMapper;
import com.xx.crm.vo.User;

public interface UserMapper extends BaseMapper<User,Integer> {

    //通过用户名查询用户记录，返回用户对象
    public User queryUserByName(String username);


}