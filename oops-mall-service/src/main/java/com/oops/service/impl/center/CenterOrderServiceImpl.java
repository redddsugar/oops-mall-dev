package com.oops.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oops.enums.OrderStatusEnum;
import com.oops.enums.YesOrNo;
import com.oops.mapper.OrderStatusMapper;
import com.oops.mapper.OrdersMapper;
import com.oops.mapper.OrdersMapperCustom;
import com.oops.pojo.OrderStatus;
import com.oops.pojo.Orders;
import com.oops.pojo.vo.OrderStatusCountsVO;
import com.oops.pojo.vo.UserCenterOrderVO;
import com.oops.service.center.CenterOrderService;
import com.oops.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年02月01日 22:34
 */
@Service
public class CenterOrderServiceImpl implements CenterOrderService {
    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    OrdersMapperCustom ordersMapperCustom;
    @Autowired
    OrderStatusMapper orderStatusMapper;

    @Override
    public PagedGridResult queryCenterOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        if (orderStatus != null) {
            map.put("orderStatus", orderStatus);
        }
        PageHelper.startPage(page, pageSize);
        List<UserCenterOrderVO> list = ordersMapperCustom.queryUserCenterOrder(map);
        return setterPagedGrid(list, page);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateDeliverOrderStatus(String orderId) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        orderStatus.setDeliverTime(new Date());

        Example example = new Example(OrderStatus.class);
        example.createCriteria().andEqualTo("orderId", orderId).andEqualTo("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);
        orderStatusMapper.updateByExampleSelective(orderStatus, example);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateReceiveOrderStatus(String orderId) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatus(OrderStatusEnum.SUCCESS.type);
        orderStatus.setSuccessTime(new Date());

        Example example = new Example(OrderStatus.class);
        example.createCriteria().andEqualTo("orderId", orderId).andEqualTo("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
        int result = orderStatusMapper.updateByExampleSelective(orderStatus, example);
        return result == 1;
    }

    @Override
    public boolean deleteCenterOrder(String userId, String orderId) {
        Orders order = new Orders();
        order.setIsDelete(YesOrNo.YES.type);
        order.setUpdatedTime(new Date());

        Example example = new Example(Orders.class);
        example.createCriteria().andEqualTo("id", orderId).andEqualTo("userId", userId);
        int result = ordersMapper.updateByExampleSelective(order, example);
        return result == 1;
    }

    @Override
    public OrderStatusCountsVO getOrderStatusCounts(String userId) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        map.put("orderStatus", OrderStatusEnum.WAIT_PAY.type);
        int waitPayCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);
        int waitDeliverCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
        int waitReceiveCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.SUCCESS.type);
        map.put("isComment", YesOrNo.NO.type);
        int waitCommentCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        OrderStatusCountsVO countsVO = new OrderStatusCountsVO(waitPayCounts,
                waitDeliverCounts,
                waitReceiveCounts,
                waitCommentCounts);
        return countsVO;
    }

    @Override
    public PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        PageHelper.startPage(page, pageSize);
        List<OrderStatus> list = ordersMapperCustom.getMyOrderTrend(map);
        return setterPagedGrid(list, page);
    }

    @Override
    public Orders checkAuth(String userId, String orderId) {
        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setId(orderId);
        orders.setIsDelete(YesOrNo.NO.type);

        return ordersMapper.selectOne(orders);
    }
    private PagedGridResult setterPagedGrid(List<?> list, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }
}
