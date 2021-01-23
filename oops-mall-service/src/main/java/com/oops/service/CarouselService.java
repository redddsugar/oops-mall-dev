package com.oops.service;

import com.oops.pojo.Carousel;

import java.util.List;

/**
 * @author L-N
 */
public interface CarouselService {
    List<Carousel> queryAll(Integer isShow);

}
