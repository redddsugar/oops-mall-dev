package com.oops.controller;


import com.oops.pojo.Items;
import com.oops.pojo.ItemsImg;
import com.oops.pojo.ItemsParam;
import com.oops.pojo.ItemsSpec;
import com.oops.pojo.vo.CommentLevelCountVO;
import com.oops.pojo.vo.ItemInfoVO;
import com.oops.service.ItemService;
import com.oops.utils.OopsResult;
import com.oops.utils.PagedGridResult;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年01月20日 18:46
 */
@Api(value = "商品", tags = {"商品信息展示相关接口"})
@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping("/info/{itemId}")
    public OopsResult info(@PathVariable String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return OopsResult.errorMsg("商品ID为空");
        }

        Items itemsList = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);

        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItems(itemsList);
        itemInfoVO.setItemImgList(itemsImgList);
        itemInfoVO.setItemSpecList(itemsSpecList);
        itemInfoVO.setItemParams(itemsParam);

        return OopsResult.ok(itemInfoVO);
    }

    @GetMapping("/commentLevel")
    public OopsResult commentCount(@RequestParam String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return OopsResult.errorMsg("商品ID为空");
        }
        CommentLevelCountVO countsVO = itemService.queryCommentCounts(itemId);
        return OopsResult.ok(countsVO);
    }

    @GetMapping("/comments")
    public OopsResult comment(@RequestParam String itemId, @RequestParam Integer level,
                                   @RequestParam Integer page, @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(itemId)) {
            return OopsResult.errorMsg("商品ID为空");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        PagedGridResult grid = itemService.queryPageComments(itemId, level, page, pageSize);
        return OopsResult.ok(grid);
    }

    @GetMapping("/search")
    public OopsResult search(@RequestParam String keywords, @RequestParam String sort,
                               @RequestParam Integer page, @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(keywords)) {
            return OopsResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        PagedGridResult grid = itemService.searchItems(keywords, sort, page, pageSize);
        return OopsResult.ok(grid);
    }

    @GetMapping("/catItems")
    public OopsResult searchByCat(@RequestParam String catId, @RequestParam String sort,
                             @RequestParam Integer page, @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(catId)) {
            return OopsResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        PagedGridResult grid = itemService.searchItemsByThrCat(catId, sort, page, pageSize);
        return OopsResult.ok(grid);
    }
}
