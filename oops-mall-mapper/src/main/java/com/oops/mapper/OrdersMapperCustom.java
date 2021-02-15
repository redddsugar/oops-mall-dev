package com.oops.mapper;

import com.oops.pojo.OrderStatus;
import com.oops.pojo.vo.UserCenterOrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author L-N
 */
public interface OrdersMapperCustom {
    List<UserCenterOrderVO> queryUserCenterOrder(@Param("paramsMap") Map<String,Object> map);

    int getMyOrderStatusCounts(@Param("paramsMap") Map<String, Object> map);

    List<OrderStatus> getMyOrderTrend(@Param("paramsMap") Map<String, Object> map);
}
