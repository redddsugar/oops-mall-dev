package com.oops.service.impl;

import com.oops.mapper.CategoryMapper;
import com.oops.mapper.CategoryMapperCustom;
import com.oops.pojo.Category;
import com.oops.pojo.vo.NewItemVO;
import com.oops.pojo.vo.SecCategoryVO;
import com.oops.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年01月20日 19:52
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    CategoryMapperCustom categoryMapperCustom;

    @Override
    public List<Category> queryRootCategory() {
        Example example = new Example(Category.class);
        example.createCriteria().andEqualTo("type", 1);

        List<Category> list = categoryMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<SecCategoryVO> querySecCatList(Integer rootCatId) {
        return categoryMapperCustom.getSubCategoryList(rootCatId);
    }

    @Override
    public List<NewItemVO> getSixNewItemsLazy(Integer rootCatId) {
        Map<String, Object> map = new HashMap<>();
        map.put("rootCatId", rootCatId);
        return categoryMapperCustom.getSixNewItemsLazy(map);
    }
}
