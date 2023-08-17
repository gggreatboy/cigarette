package com.zjweu.cigarette.service;

import com.zjweu.cigarette.pojo.Commodity;
import com.zjweu.cigarette.pojo.PageBean;
import com.zjweu.cigarette.pojo.User;

import java.util.List;
import java.util.Map;

public interface CommodityService {
    //分页查询商品
    PageBean pageCommodity(Integer page, Integer pageSize,String comName, String comCode1, String comCode2, Integer comType1);

    //根据ID查询商品信息
    Commodity getById(Integer commodityId);

    //新增商品
    void insert(Commodity commodity);

    //更新商品信息
    void update(Commodity commodity);

    //根据id批量删除商品信息
    void deleteByComId(List<Integer> ids);
    
}
