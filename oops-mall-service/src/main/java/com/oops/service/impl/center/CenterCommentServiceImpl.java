package com.oops.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oops.enums.YesOrNo;
import com.oops.mapper.ItemsCommentsMapperCustom;
import com.oops.mapper.OrderItemsMapper;
import com.oops.mapper.OrderStatusMapper;
import com.oops.mapper.OrdersMapper;
import com.oops.pojo.OrderItems;
import com.oops.pojo.OrderStatus;
import com.oops.pojo.Orders;
import com.oops.pojo.bo.OrderItemsCommentBO;
import com.oops.pojo.vo.MyCommentVO;
import com.oops.service.center.CenterCommentService;
import com.oops.utils.OopsResult;
import com.oops.utils.PagedGridResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年02月02日 19:46
 */
@Service
public class CenterCommentServiceImpl implements CenterCommentService {
    @Autowired
    OrderItemsMapper orderItemsMapper;
    @Autowired
    ItemsCommentsMapperCustom itemsCommentsMapperCustom;
    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    OrderStatusMapper orderStatusMapper;
    @Autowired
    Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<OrderItems> queryItems(String orderId) {
        OrderItems orderItems = new OrderItems();
        orderItems.setOrderId(orderId);

        return orderItemsMapper.select(orderItems);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveItemComment(String userId, String orderId, List<OrderItemsCommentBO> itemCommentList) {
        Map<String, Object> map = new HashMap<>();

        //保存评价 item_comments表
        for (OrderItemsCommentBO i: itemCommentList) {
            i.setCommentId(sid.nextShort());
        }
        map.put("userId", userId);
        map.put("commentList", itemCommentList);
        itemsCommentsMapperCustom.saveComments(map);

        //修改订单表更正状态为已评价   orders表
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsComment(YesOrNo.YES.type);
        ordersMapper.updateByPrimaryKeySelective(order);

        //修改订单状态表的留言时间   order_status表
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    @Override
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        PageHelper.startPage(page ,pageSize);
        List<MyCommentVO> list = itemsCommentsMapperCustom.queryMyComments(map);
        return setterPagedGrid(list, page);
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
