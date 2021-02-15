package com.oops.controller.center;

import com.oops.pojo.Users;
import com.oops.service.center.CenterUserService;
import com.oops.utils.OopsResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年01月30日 15:40
 */
@Api(value = "用户信息相关controller", tags = {"用户信息相关接口"})
@RestController
@RequestMapping("/center")
public class CenterController {
    @Autowired
    CenterUserService centerUserService;

    @GetMapping("/userInfo")
    public OopsResult queryUserInfo(@RequestParam String userId) {
        Users users = centerUserService.queryUserInfo(userId);
        return OopsResult.ok(users);
    }
}
