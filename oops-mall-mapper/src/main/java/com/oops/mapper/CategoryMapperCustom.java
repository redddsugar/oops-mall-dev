package com.oops.mapper;

import com.oops.pojo.vo.SecCategoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapperCustom  {
    public List<SecCategoryVO> getSubCategoryList(@Param("rootCatId") Integer rootCatId);
}