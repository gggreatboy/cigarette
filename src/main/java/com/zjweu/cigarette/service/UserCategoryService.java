package com.zjweu.cigarette.service;

import com.zjweu.cigarette.pojo.User;
import com.zjweu.cigarette.pojo.UserCategory;

import java.util.List;


public interface UserCategoryService {
    //列出用户组
    List<UserCategory> list();

    //更改用户组
    void update(UserCategory userCategory);

    //删除用户组
    void deleteCategory(Integer id);

    //新增用户组
    void save(UserCategory userCategory);

    //根据ID查询用户组
    UserCategory getById(Integer id);
}
