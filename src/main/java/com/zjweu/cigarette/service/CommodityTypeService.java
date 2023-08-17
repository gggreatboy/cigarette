package com.zjweu.cigarette.service;

import com.zjweu.cigarette.pojo.CigaretteType;
import com.zjweu.cigarette.pojo.PageBean;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface CommodityTypeService {
    //根据商品类别ID查询一条类别
    CigaretteType getById(Integer typeId);

    //新增一条类别
    void insert(CigaretteType type);

    //更新类别信息
    void update(CigaretteType type);

    //根据类别id批量删除商品类别
    void deleteById(List<Integer> ids);

    //分页查询类别
    PageBean pageCommodityType(Integer page, Integer pageSize,String typeName, Integer typeFlag, Integer typeBig);

    //列出所有大类
    Integer findAllBig();

    //列出所有小类
    Integer findAllSmall();

    //查询每个大类中包含的小类个数
    Map<Integer,Object> smallInBig();

    //查询每个小类中含有的商品数
    Map<Integer, Object> comInSmall();

}
