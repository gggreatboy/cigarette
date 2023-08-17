package com.zjweu.cigarette.mapper;

import com.zjweu.cigarette.pojo.Forward;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface InventoryForwardMapper {

    //新增一条结转记录
    void insert(Forward forward);

    //查询所有结转记录
    public List<Forward> list(String comName,String people,LocalDateTime begin,LocalDateTime end);

    //批量删除结转记录
    void deleteById(List<Integer> ids);

    //查询一条结转记录
    Forward getById(Integer id);

}
