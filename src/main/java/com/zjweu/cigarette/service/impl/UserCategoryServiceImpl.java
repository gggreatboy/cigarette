package com.zjweu.cigarette.service.impl;

import com.zjweu.cigarette.mapper.UserCategoryMapper;
import com.zjweu.cigarette.mapper.UserMapper;
import com.zjweu.cigarette.pojo.User;
import com.zjweu.cigarette.pojo.UserCategory;
import com.zjweu.cigarette.service.UserCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class UserCategoryServiceImpl implements UserCategoryService {

    @Autowired
    private UserCategoryMapper userCategoryMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserCategory> list() {
        List<UserCategory> all = userCategoryMapper.findAll();
        for (UserCategory userCategory : all) {
            userCategory.setCategoryShow(userCategory.getCategoryName()+userCategory.getCategoryPermission());
        }
        return all;
    }

    @Override
    public void update(UserCategory userCategory) {
        userCategoryMapper.modifyCategory(userCategory);
    }

    @Transactional(rollbackFor = Exception.class)//若有部分抛出运行时异常（默认）将会回滚
    @Override
    public void deleteCategory(Integer id) {
        userCategoryMapper.deleteById(id);
        userMapper.deleteByCategoryId(id);
    }

    @Override
    public void save(UserCategory userCategory) {
        userCategoryMapper.save(userCategory);
    }

    @Override
    public UserCategory getById(Integer id) {
        UserCategory category = userCategoryMapper.getById(id);
        category.setCategoryShow(category.getCategoryName()+category.getCategoryPermission());
        return category;
    }
}
