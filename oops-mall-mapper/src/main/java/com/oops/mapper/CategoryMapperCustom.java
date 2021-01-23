package com.oops.mapper;

import com.oops.pojo.vo.NewItemVO;
import com.oops.pojo.vo.SecCategoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryMapperCustom  {
    List<SecCategoryVO> getSubCategoryList(@Param("rootCatId") Integer rootCatId);

    List<NewItemVO> getSixNewItemsLazy(@Param("map") Map<String, Object>map);
}