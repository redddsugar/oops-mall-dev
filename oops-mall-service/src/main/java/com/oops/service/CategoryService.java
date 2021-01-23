package com.oops.service;

import com.oops.pojo.Category;
import com.oops.pojo.vo.NewItemVO;
import com.oops.pojo.vo.SecCategoryVO;

import java.util.List;

/**
 * @author L-N
 */
public interface CategoryService {
    /*

     */
    List<Category> queryRootCategory();

    List<SecCategoryVO> querySecCatList(Integer rootCatId);

    /**
     * 查询首页每个一级分类下的6条最新商品数据
     * @param rootCatId
     * @return
     */
    List<NewItemVO> getSixNewItemsLazy(Integer rootCatId);
}
