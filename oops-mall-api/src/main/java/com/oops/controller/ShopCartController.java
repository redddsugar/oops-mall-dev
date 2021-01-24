package com.oops.controller;

import com.oops.pojo.bo.ShopcartBO;
import com.oops.pojo.vo.ShopcartVO;
import com.oops.service.ItemService;
import com.oops.utils.OopsResult;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年01月23日 12:52
 */
@Api(value = "购物车接口Controller", tags = {"购物车接口相关API"})
@RestController
@RequestMapping("/shopcart")
public class ShopCartController {

    @Autowired
    ItemService itemService;

    @PostMapping("/add")
    public OopsResult addCart(@RequestParam String userId, @RequestBody ShopcartBO shopcartBO,
                          HttpServletResponse response, HttpServletRequest request) {
        if (StringUtils.isBlank(userId)) {
            return OopsResult.errorMsg("");
        }
        System.out.println(shopcartBO);
        //TODO 前端用户在登录下，将购物车同步至redis
        return OopsResult.ok();
    }

    @PostMapping("/del")
    public OopsResult delCart(@RequestParam String userId, @RequestParam String itemSpecId) {
        if (StringUtils.isBlank(itemSpecId) || StringUtils.isBlank(userId)) {
            return OopsResult.errorMsg("");
        }
        //TODO 用户在登录的状态下删除购物车商品，redis同步操作

        return OopsResult.ok();
    }
}
