package com.oops.controller;

import com.oops.pojo.Orders;
import com.oops.pojo.Users;

import com.oops.service.center.CenterOrderService;
import com.oops.utils.OopsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年01月31日 14:09
 */
@Controller
public class BaseController {
    @Autowired
    public CenterOrderService centerOrderService;

    //将验证字段过程中的错误信息提取出来封装成map
    public Map<String, String> getErrors(BindingResult bindingResult) {
        Map<String,String> map = new HashMap<>();
        List<FieldError> errorList = bindingResult.getFieldErrors();
        for(FieldError error : errorList){
            map.put(error.getField(),error.getDefaultMessage());
        }
        return map;
    }

    public Users setNullProperty(Users user){
        user.setPassword(null);
        user.setMobile(null);
        user.setEmail(null);
        user.setCreatedTime(null);
        user.setUpdatedTime(null);
        user.setBirthday(null);
        return user;
    }

    //用于验证用户和订单是否有关联关系，避免非法用户调用
    public OopsResult checkAuth(@RequestParam String userId, @RequestParam String orderId) {
        Orders order = centerOrderService.checkAuth(userId, orderId);
        if (order == null) {
            return OopsResult.errorMsg("非法操作！");
        }
        return OopsResult.ok(order);
    }
}
