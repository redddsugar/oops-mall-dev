package com.oops.controller;

import com.oops.enums.PayMethod;
import com.oops.pojo.vo.SubmitOrderVO;
import com.oops.service.OrderService;
import com.oops.utils.CookieUtils;
import com.oops.utils.OopsResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年01月24日 16:02
 */
@Api(value = "订单相关controller", tags = {"订单相关接口"})
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/create")
    public OopsResult createOrder(@RequestBody SubmitOrderVO submitOrderVO,
                                  HttpServletRequest request, HttpServletResponse response) {

        if (!submitOrderVO.getPayMethod().equals(PayMethod.WECHAT.type)
                && !submitOrderVO.getPayMethod().equals(PayMethod.ALIPAY.type)) {
            return OopsResult.errorMsg("支付方式不支持！");
        }

        System.out.println(submitOrderVO.toString());
        String orderId = orderService.createOrder(submitOrderVO);

        //3. 创建订单后，移除购物车中已结算的商品
        //        TODO：整合redis后移除购物车已结算的商品，并同步到前端cookie
        CookieUtils.setCookie(request,response,"shopcart","",true);
        return OopsResult.ok(orderId);
    }
}
