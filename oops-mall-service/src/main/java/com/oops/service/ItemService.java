package com.oops.service;

import com.oops.pojo.Items;
import com.oops.pojo.ItemsImg;
import com.oops.pojo.ItemsParam;
import com.oops.pojo.ItemsSpec;
import com.oops.pojo.vo.CommentLevelCountVO;
import com.oops.pojo.vo.ShopcartVO;
import com.oops.utils.PagedGridResult;

import java.util.List;

/**
 * @author L-N
 */
public interface ItemService {
    /**

     */
    Items queryItemById(String itemId);

    List<ItemsImg> queryItemImgList(String itemId);

    List<ItemsSpec> queryItemSpecList(String itemId);

    ItemsParam queryItemParam(String itemId);

    CommentLevelCountVO queryCommentCounts(String itemId);

    PagedGridResult queryPageComments(String itemId, Integer level,
                                      Integer page, Integer pageSize);

    PagedGridResult searchItems(String keywords,String sort,
                                      Integer page, Integer pageSize);

    PagedGridResult searchItemsByThrCat(String catId,String sort,
                                        Integer page, Integer pageSize);

    /**
     * 根据商品规格查询最新的购物车商品数据
     * @param specIds
     * @return
     */
    List<ShopcartVO> queryItemsBySpecIds(String specIds);

    ItemsSpec queryItemSpecById(String itemSpecId);

    String queryItemMainImgById(String itemId);

    /**
     * 减库存
     * @param specId 商品规格id
     * @param buyCounts  购买数量
     */
    void decreaseItemSpecStock(String specId, int buyCounts);
}
