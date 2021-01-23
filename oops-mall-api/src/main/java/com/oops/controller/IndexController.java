package com.oops.controller;

import com.oops.enums.YesOrNo;
import com.oops.pojo.Carousel;
import com.oops.pojo.Category;
import com.oops.pojo.vo.NewItemVO;
import com.oops.pojo.vo.SecCategoryVO;
import com.oops.service.CarouselService;
import com.oops.service.CategoryService;
import com.oops.utils.OopsResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年01月20日 18:46
 */
@Api(value = "首页", tags = {"首页展示相关接口"})
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    CarouselService carouselService;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/carousel")
    public OopsResult carousel() {
        List<Carousel> list = carouselService.queryAll(YesOrNo.YES.type);
        return OopsResult.ok(list);
    }

    @GetMapping("/cats")
    public OopsResult category() {
        List<Category> list = categoryService.queryRootCategory();
        return OopsResult.ok(list);
    }

    @GetMapping("/subCat/{rootCatId}")
    public OopsResult subCategory(@PathVariable Integer rootCatId) {
        if (rootCatId == null)
            return OopsResult.errorMsg("Good Luck!");

        List<SecCategoryVO> list = categoryService.querySecCatList(rootCatId);
        return OopsResult.ok(list);
    }

    @GetMapping("/sixNewItems/{rootCatId}")
    public OopsResult sixNewItems(@PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return OopsResult.errorMsg("分类不存在");
        }

        List<NewItemVO> list = categoryService.getSixNewItemsLazy(rootCatId);
        return OopsResult.ok(list);
    }
}
