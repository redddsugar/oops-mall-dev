package com.oops.pojo.vo;

import java.util.List;

/**
 * @author L-N
 * @Description 二级分类
 * @createTime 2021年01月20日 20:13
 */
public class SecCategoryVO {
    Integer id;
    String name;
    String type;
    Integer fatherId;

    List<ThrCategoryVO> subCatList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }

    public List<ThrCategoryVO> getSubCatList() {
        return subCatList;
    }

    public void setSubCatList(List<ThrCategoryVO> subCatList) {
        this.subCatList = subCatList;
    }
}
