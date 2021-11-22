package com.xx.crm.model;

/**
 * @ClassName UserModel
 * @Description 返回客户端需要的数据
 * @Author xu
 * @Date 2021/10/22 15:02
 * @Version 1.0
 */
public class UserModel {

//    private Integer userId;
    private String userIdStr;//加密后的用户id
    private String userName;
    private String trueName;

/*    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }*/

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getUserIdStr() {
        return userIdStr;
    }

    public void setUserIdStr(String userIdStr) {
        this.userIdStr = userIdStr;
    }
}
