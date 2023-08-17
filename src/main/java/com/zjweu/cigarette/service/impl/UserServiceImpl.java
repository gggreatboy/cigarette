package com.zjweu.cigarette.service.impl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zjweu.cigarette.mapper.UserMapper;
import com.zjweu.cigarette.pojo.PageBean;
import com.zjweu.cigarette.pojo.User;
import com.zjweu.cigarette.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageBean pageUser(Integer page, Integer pageSize, String userName, Integer userAge, Integer userBanci, Integer userCategory) {
        PageHelper.startPage(page,pageSize);
        List<User> userList=userMapper.list(userName,userAge,userBanci,userCategory);
        Page<User> p = (Page<User>)userList;//封装类强转page类型
        PageBean pageBean=new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }

    @Override
    public void delete(List<Integer> ids) {
        userMapper.delete(ids); //删除对应用户
    }

    @Override
    public void save(User user) {
        userMapper.insert(user);
    }

    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public User login(User user) {
        return userMapper.getByUsernameAndPassword(user);
    }
}
