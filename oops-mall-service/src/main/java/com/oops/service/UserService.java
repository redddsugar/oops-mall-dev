package com.oops.service;

import com.oops.pojo.Users;
import com.oops.pojo.bo.UserBo;

/**
 * @author L-N
 */
public interface UserService {
    //判用户名存在否
    boolean queryUsernameIsExist(String username);

    Users createUser(UserBo userBo);

    Users queryUserForLogin(String username, String password) throws Exception;
}
