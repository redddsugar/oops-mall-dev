package com.oops.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oops.enums.CommentLevel;
import com.oops.mapper.*;
import com.oops.pojo.*;
import com.oops.pojo.vo.CommentLevelCountVO;
import com.oops.pojo.vo.ItemCommentVO;
import com.oops.pojo.vo.SearchItemsVO;
import com.oops.service.ItemService;
import com.oops.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年01月22日 11:30
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ItemsMapper itemsMapper;
    @Autowired
    ItemsImgMapper itemsImgMapper;
    @Autowired
    ItemsSpecMapper itemsSpecMapper;
    @Autowired
    ItemsParamMapper itemsParamMapper;
    @Autowired
    ItemsCommentsMapper itemsCommentsMapper;
    @Autowired
    ItemsMapperCustom itemsMapperCustom;

    @Override
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example example = new Example(ItemsImg.class);
        example.createCriteria().andEqualTo("itemId", itemId);
        return itemsImgMapper.selectByExample(example);
    }

    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example = new Example(ItemsSpec.class);
        example.createCriteria().andEqualTo("itemId", itemId);
        return itemsSpecMapper.selectByExample(example);
    }

    @Override
    public ItemsParam queryItemParam(String itemId) {
        Example example = new Example(ItemsParam.class);
        example.createCriteria().andEqualTo("itemId", itemId);
        return itemsParamMapper.selectOneByExample(example);
    }

    @Override
    public CommentLevelCountVO queryCommentCounts(String itemId) {
        Integer goodCounts = getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = getCommentCounts(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = getCommentCounts(itemId, CommentLevel.BAD.type);
        Integer totalCounts = goodCounts+normalCounts+badCounts;
        CommentLevelCountVO commentLevelCountVO = new CommentLevelCountVO();
        commentLevelCountVO.setGoodCounts(goodCounts);
        commentLevelCountVO.setNormalCounts(normalCounts);
        commentLevelCountVO.setBadCounts(badCounts);
        commentLevelCountVO.setTotalCounts(totalCounts);
        return commentLevelCountVO;
    }

    @Override
    public PagedGridResult queryPageComments(String itemId, Integer level, Integer page, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("level", level);

        //pageHelper SQL拦截形成分页
        PageHelper.startPage(page, pageSize);
        List<ItemCommentVO> list = itemsMapperCustom.queryItemComments(map);
        return setterPagedGrid(list, page);
    }

    @Override
    public PagedGridResult searchItems(String keywords, String sort,
                                       Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("keywords", keywords);
        map.put("sort", sort);

        //pageHelper SQL拦截形成分页
        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> list = itemsMapperCustom.searchItems(map);
        return setterPagedGrid(list, page);
    }



    @Override
    public PagedGridResult searchItemsByThrCat(String catId, String sort,
                                       Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("catId", catId);
        map.put("sort", sort);

        //pageHelper SQL拦截形成分页
        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> list = itemsMapperCustom.searchItems(map);
        return setterPagedGrid(list, page);
    }

    private Integer getCommentCounts(String itemId, Integer level) {
        ItemsComments condition = new ItemsComments();
        condition.setItemId(itemId);
        if (level != null) {
            condition.setCommentLevel(level);
        }
        return itemsCommentsMapper.selectCount(condition);
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
