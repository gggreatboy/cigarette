package com.zjweu.cigarette.service;

import com.zjweu.cigarette.pojo.PageBean;
import com.zjweu.cigarette.pojo.User;
import java.util.List;

public interface UserService {
    //分页查询用户
    PageBean pageUser(Integer page, Integer pageSize, String name, Integer gender,Integer banci,Integer category);

    //根据id批量删除用户
    void delete(List<Integer> ids);

    //新增用户
    void save(User user);

    //根据ID查询用户信息
    User getById(Integer id);

    //更新用户信息
    void update(User user);

    //用户登录
    User login(User user);
}
