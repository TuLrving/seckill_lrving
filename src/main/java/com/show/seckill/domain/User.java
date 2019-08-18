package com.show.seckill.domain;


/**
 * @Auther: 涂成
 * @Date: 2019/6/18 10:34
 * @Description: 对应数据库User表
 */
public class User {

    private Integer userId;
    private String userName;

    public User(Integer userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public User() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
