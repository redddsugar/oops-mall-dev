package com.oops.controller;

import com.oops.pojo.Users;
import com.oops.pojo.bo.UserBo;
import com.oops.service.UserService;
import com.oops.utils.CookieUtils;
import com.oops.utils.JsonUtils;
import com.oops.utils.OopsResult;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年01月19日 14:07
 */
@Api(value = "注册登录", tags = {"注册及登录相关接口"})
@RestController
@RequestMapping("/passport")
public class PassPortController {

    @Autowired
    UserService userService;

    @GetMapping("/usernameIsExist")
    public OopsResult usernameIsExist(@RequestParam String username) {
        if (StringUtils.isBlank(username)) {
            return OopsResult.errorMsg("空用户名");
        }
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return OopsResult.errorMsg("已存在的用户名");
        }
        return OopsResult.ok();
    }

    @PostMapping("/regist")
    public OopsResult register(@RequestBody UserBo userBo,
                                HttpServletRequest request, HttpServletResponse response) {
        String username = userBo.getUsername();
        String password = userBo.getPassword();
        String confirmPwd = userBo.getConfirmPassword();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPwd)) {
            return OopsResult.errorMsg("用户名或密码不能为空");
        }

        if (password.length()<6) {
            return OopsResult.errorMsg("密码长度小于6");
        }

        if (!password.equals(confirmPwd)) {
            return OopsResult.errorMsg("两次密码输入一致");
        }

        Users userResult = userService.createUser(userBo);
        Users users = privacy(userResult);
        return OopsResult.ok();
    }

    @PostMapping("/login")
    public OopsResult login(@RequestBody UserBo userBo,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = userBo.getUsername();
        String password = userBo.getPassword();

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return OopsResult.errorMsg("用户名或密码不能为空");
        }

        Users userResult = userService.queryUserForLogin(username, password);
        if (userResult == null) {
            return OopsResult.errorMsg("密码输入错误");
        }

        Users users = privacy(userResult);
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult), true);
        return OopsResult.ok(userResult);
    }

    @PostMapping("/logout")
    public OopsResult logout(@RequestParam String userId,
                             HttpServletRequest request, HttpServletResponse response) {
        //清楚用户相关cookie
        CookieUtils.deleteCookie(request, response, userId);
        return OopsResult.ok();
    }

    private Users privacy(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }
}
