package com.oops.pojo.bo;

/**
 * @author L-N
 * @Description 用户注册时
 * @createTime 2021年01月19日 14:43
 */
public class UserBo {
    String username;
    String password;
    String confirmPassword;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
