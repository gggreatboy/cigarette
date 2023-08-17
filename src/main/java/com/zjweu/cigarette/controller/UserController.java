package com.zjweu.cigarette.controller;

import com.zjweu.cigarette.pojo.PageBean;
import com.zjweu.cigarette.pojo.Result;
import com.zjweu.cigarette.pojo.User;
import com.zjweu.cigarette.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //分页查询用户
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String userName, Integer userAge,Integer userBanci,Integer userCategory) {
        log.info("分页查询，参数:{},{},{},{},{},{}", page, pageSize,userName,userAge,userBanci,userCategory);
        PageBean pageBean = userService.pageUser(page, pageSize,userName,userAge,userBanci,userCategory);
        return Result.success(pageBean);
    }

    //根据id删除用户
    @DeleteMapping("/{idsString}")
    public Result delete(@PathVariable List<Integer> idsString){
        log.info("批量删除，ids:{}",idsString);
        List<Integer> ids=idsString.subList(0,idsString.size());
        userService.delete(ids);
        return Result.success();
    }

    //新增用户
    @PostMapping
    public Result save(@RequestBody User user){
        log.info("新增用户，user:{}",user);
        userService.save(user);
        return Result.success();
    }

    //根据id查询用户
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("根据ID查询用户信息，id：{}",id);
        User user=userService.getById(id);
        return  Result.success(user);
    }

    //更新用户信息
    @PutMapping
    public Result update(@RequestBody User user){
        log.info("更新用户信息:{}",user);
        userService.update(user);
        return Result.success();
    }
}
