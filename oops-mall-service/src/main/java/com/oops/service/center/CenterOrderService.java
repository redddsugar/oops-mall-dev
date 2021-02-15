package com.oops.service.center;

import com.oops.pojo.Orders;
import com.oops.pojo.vo.OrderStatusCountsVO;
import com.oops.utils.PagedGridResult;

/**
 * @author L-N
 */
public interface CenterOrderService {
    PagedGridResult queryCenterOrders(String userId, Integer orderStatus,Integer page, Integer pageSize);

    void updateDeliverOrderStatus(String orderId);

    boolean deleteCenterOrder(String userId, String orderId);

    boolean updateReceiveOrderStatus(String orderId);

    OrderStatusCountsVO getOrderStatusCounts(String userId);

    PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize);

    /**
     * 核对被操作订单是否为本人订单
     * @param userId
     * @param orderId
     * @return
     */
    Orders checkAuth(String userId, String orderId);
}
