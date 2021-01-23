package com.oops.service.impl;

import com.oops.mapper.CarouselMapper;
import com.oops.pojo.Carousel;
import com.oops.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年01月20日 18:42
 */
@Service
public class CarouselServiceImpl implements CarouselService {
    @Autowired
    CarouselMapper carouselMapper;

    @Override
    public List<Carousel> queryAll(Integer isShow) {
        Example example = new Example(Carousel.class);
        example.orderBy("sort").desc();
        example.createCriteria().andEqualTo("isShow", isShow);

        List<Carousel> result = carouselMapper.selectByExample(example);
        return result;
    }
}
