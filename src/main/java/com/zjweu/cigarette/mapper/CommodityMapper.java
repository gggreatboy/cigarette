package com.zjweu.cigarette.mapper;

import com.zjweu.cigarette.pojo.Commodity;
import com.zjweu.cigarette.pojo.User;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommodityMapper {

    //根据ID查询商品信息
    Commodity getById(Integer commodityId);

    //新增商品
    void insert(Commodity commodity);

    //更新商品信息
    void update(Commodity commodity);

    //根据id批量删除商品信息
    void deleteByComId(List<Integer> ids);

    //分页查询商品(商品信息管理页面)
    public List<Commodity> list(String comName, String comCode1, String comCode2, Integer comType1);

    //计算每个小类含有的商品数
    @MapKey("com_type2")
    Map<Integer, Object> comInSmall();
}
