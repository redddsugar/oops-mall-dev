package com.oops.controller.center;

import com.oops.controller.BaseController;
import com.oops.pojo.Orders;
import com.oops.pojo.vo.OrderStatusCountsVO;
import com.oops.service.center.CenterOrderService;
import com.oops.utils.OopsResult;
import com.oops.utils.PagedGridResult;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年02月01日 22:04
 */
@Api(value = "个人中心订单", tags = {"个人中心订单展示相关接口"})
@RestController
@RequestMapping("/myorders")
public class CenterOrderController extends BaseController {
    @PostMapping("/query")
    public OopsResult queryCenterOrder(@RequestParam String userId, @RequestParam Integer orderStatus,
                                       @RequestParam Integer page, @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return OopsResult.errorMsg("用户ID为空");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        PagedGridResult grid = centerOrderService.queryCenterOrders(userId, orderStatus, page, pageSize);
        return OopsResult.ok(grid);
    }

    @PostMapping("/confirmReceive")
    public OopsResult confirmReceive(@RequestParam String userId, @RequestParam String orderId) {
        //判断被操作订单是否为本人订单
        OopsResult checkResult = checkAuth(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }
        boolean res = centerOrderService.updateReceiveOrderStatus(orderId);
        if (!res) {
            return OopsResult.errorMsg("订单确认收货失败！");
        }
        return OopsResult.ok();
    }

    //该接口的删除实际上是将该条订单的is_delete属性更为yes状态，致使查询时过滤此条订单
    @PostMapping("/delete")
    public OopsResult deleteCenterOrder(@RequestParam String userId, @RequestParam String orderId) {
        //判断被操作订单是否为本人订单
        OopsResult checkResult = checkAuth(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }
        boolean res = centerOrderService.deleteCenterOrder(userId, orderId);
        if (!res) {
            return OopsResult.errorMsg("订单删除失败！");
        }
        return OopsResult.ok();
    }

    //该接口用于模拟发货行为
    @GetMapping("/deliver")
    public OopsResult deliver(@RequestParam String orderId) {
        if (StringUtils.isBlank(orderId)) {
            return OopsResult.errorMsg("订单ID不能为空");
        }
        centerOrderService.updateDeliverOrderStatus(orderId);
        return OopsResult.ok();
    }

    @PostMapping("/statusCounts")
    public OopsResult queryCounts(@RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            return OopsResult.errorMsg("用户ID为空");
        }

        OrderStatusCountsVO counts = centerOrderService.getOrderStatusCounts(userId);
        return OopsResult.ok(counts);
    }

    @PostMapping("/trend")
    public OopsResult trend(@RequestParam String userId,
                            @RequestParam Integer page, @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return OopsResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 5;
        }

        PagedGridResult grid = centerOrderService.getOrdersTrend(userId,
                page,
                pageSize);
        return OopsResult.ok(grid);
    }
}
