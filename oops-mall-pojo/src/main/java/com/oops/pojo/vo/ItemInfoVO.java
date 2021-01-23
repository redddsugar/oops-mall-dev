package com.oops.pojo.vo;

import com.oops.pojo.Items;
import com.oops.pojo.ItemsImg;
import com.oops.pojo.ItemsParam;
import com.oops.pojo.ItemsSpec;

import java.util.List;

/**
 * @author L-N
 * @Description 商品详情VO
 * @createTime 2021年01月22日 11:43
 */
public class ItemInfoVO {
    Items items;
    List<ItemsImg> itemImgList;
    List<ItemsSpec> itemSpecList;
    ItemsParam itemParams;

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public List<ItemsImg> getItemImgList() {
        return itemImgList;
    }

    public void setItemImgList(List<ItemsImg> itemImgList) {
        this.itemImgList = itemImgList;
    }

    public ItemsParam getItemParams() {
        return itemParams;
    }

    public void setItemParams(ItemsParam itemParams) {
        this.itemParams = itemParams;
    }

    public List<ItemsSpec> getItemSpecList() {
        return itemSpecList;
    }

    public void setItemSpecList(List<ItemsSpec> itemSpecList) {
        this.itemSpecList = itemSpecList;
    }
}
