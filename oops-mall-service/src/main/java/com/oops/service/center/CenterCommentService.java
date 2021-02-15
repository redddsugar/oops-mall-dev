package com.oops.service.center;

import com.oops.pojo.OrderItems;
import com.oops.pojo.bo.OrderItemsCommentBO;
import com.oops.utils.PagedGridResult;

import java.util.List;

/**
 * @author L-N
 */
public interface CenterCommentService {
    List<OrderItems> queryItems(String orderId);

    void saveItemComment(String userId, String orderId, List<OrderItemsCommentBO> itemCommentList);

    PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize);
}
