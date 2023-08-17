package com.zjweu.cigarette.mapper;

import com.zjweu.cigarette.pojo.UserCategory;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface UserCategoryMapper  {
    @Select("SELECT * from category")
    List<UserCategory> findAll();

    //根据id删除用户组
    void deleteById(Integer id);

    //新增一个用户组
    @Insert("insert into category(category_name, category_permission) values (#{categoryName},#{categoryPermission})")
    void save(UserCategory userCategory);

    //根据id查找用户组
    @Select("SELECT category_id,category_name, category_permission from category where category_id=#{id}")
    UserCategory getById(Integer id);

    //修改部门
    void modifyCategory(UserCategory userCategory);
}
