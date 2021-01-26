package com.oops.mapper;

import com.oops.pojo.vo.ItemCommentVO;
import com.oops.pojo.vo.SearchItemsVO;
import com.oops.pojo.vo.ShopcartVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom {
    List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> map);

    List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> map);

    List<SearchItemsVO> searchItemsByThrCat(@Param("paramsMap") Map<String, Object> map);

    List<ShopcartVO>queryItemsBySpecIds(@Param("paramsList") List<String> specIds);

    int decreaseItemSpecStock(@Param("specId") String specId,
                                     @Param("pendingCounts") int pendingCounts);
}