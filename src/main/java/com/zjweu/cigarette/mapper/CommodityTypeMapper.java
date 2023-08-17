package com.zjweu.cigarette.mapper;

import com.zjweu.cigarette.pojo.CigaretteType;
import com.zjweu.cigarette.pojo.Inventory;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommodityTypeMapper {
    //根据商品类别ID查询一条类别
    CigaretteType getById(Integer typeId);

    //新增一条类别
    void insert(CigaretteType type);

    //更新类别信息
    void update(CigaretteType type);

    //根据类别id批量删除商品类别
    void deleteById(List<Integer> ids);

    //分页查询类别
    public List<CigaretteType> list(String typeName, Integer typeFlag, Integer typeBig);

    //列出所有大类
    @Select("SELECT count(*) from type where type_flag=1")
    Integer findAllBig();

    //列出所有小类
    @Select("SELECT count(*) from type where type_flag=2")
    Integer findAllSmall();

    @MapKey("type_big")
    Map<Integer,Object> smallInBig();
}
