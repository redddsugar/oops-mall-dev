package com.oops.controller.center;

import com.oops.controller.BaseController;
import com.oops.enums.YesOrNo;
import com.oops.pojo.OrderItems;
import com.oops.pojo.Orders;
import com.oops.pojo.Users;
import com.oops.pojo.bo.OrderItemsCommentBO;
import com.oops.service.center.CenterCommentService;
import com.oops.service.center.CenterUserService;
import com.oops.utils.OopsResult;
import com.oops.utils.PagedGridResult;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年01月30日 15:40
 */
@Api(value = "个人中心评价相关controller", tags = {"个人中心评价相关接口"})
@RestController
@RequestMapping("/mycomments")
public class CenterCommentController extends BaseController {
    @Autowired
    CenterCommentService centerCommentService;

    @PostMapping("/pending")
    public OopsResult queryItems(@RequestParam String userId, @RequestParam String orderId) {
        //判断被操作订单是否为本人订单
        OopsResult checkResult = checkAuth(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }
        // 判断该笔订单是否已经评价过，评价过了就不再继续
        Orders myOrder = (Orders)checkResult.getData();
        if (myOrder.getIsComment().equals(YesOrNo.YES.type)) {
            return OopsResult.errorMsg("该笔订单已经评价");
        }

        List<OrderItems> list = centerCommentService.queryItems(orderId);
        return OopsResult.ok(list);
    }

    @PostMapping("/saveList")
    public OopsResult saveComment(@RequestParam String userId, @RequestParam String orderId,
                                   @RequestBody List<OrderItemsCommentBO>itemCommentList) {
        //判断被操作订单是否为本人订单
        OopsResult checkResult = checkAuth(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }
        // 判断评论内容list不能为空
        if (itemCommentList == null || itemCommentList.isEmpty()) {
            return OopsResult.errorMsg("评论内容不能为空！");
        }

        centerCommentService.saveItemComment(userId, orderId, itemCommentList);
        return OopsResult.ok();
    }

    @PostMapping("/query")
    public OopsResult query(@RequestParam String userId, Integer page, Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return OopsResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }

        PagedGridResult grid = centerCommentService.queryMyComments(userId, page, pageSize);
        return OopsResult.ok(grid);
    }
}
