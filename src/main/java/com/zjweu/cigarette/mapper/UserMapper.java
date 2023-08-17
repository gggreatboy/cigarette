package com.zjweu.cigarette.mapper;

import com.zjweu.cigarette.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserMapper {
    //根据ID查询用户信息
    User getById(Integer userId);

    //新增用户
    void insert(User user);

    //员工信息条件查询，插件实现分页
    public List<User> list(String userName, Integer userAge, Integer userBanci, Integer userCategory);

    //更新用户
    void update(User user);

    //根据id批量删除用户
    void delete(List<Integer> ids);

    //根据用户组id批量删除用户
    void deleteByCategoryId(Integer id);

    //根据用户名和密码查询用户
    User getByUsernameAndPassword(User user);
}
