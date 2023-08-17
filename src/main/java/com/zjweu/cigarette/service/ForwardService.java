package com.zjweu.cigarette.service;

import com.zjweu.cigarette.pojo.Commodity;
import com.zjweu.cigarette.pojo.Forward;
import com.zjweu.cigarette.pojo.PageBean;
import com.zjweu.cigarette.pojo.User;

import java.time.LocalDateTime;
import java.util.List;

public interface ForwardService {

    //新增一条结转记录(通过原有结转记录条目上新增)
    void insert(Forward forward,User user);


    //新增一条结转记录（通过新增按钮新增）
    void insert(Commodity commodity,User user);

    //批量删除结转记录
    void deleteById(List<Integer> ids);

    //查询一条结转记录
    Forward getById(Integer id);

    //查询所有结转记录
    PageBean pageForward(Integer page, Integer pageSize, String comName, String people, LocalDateTime begin, LocalDateTime end);
}
