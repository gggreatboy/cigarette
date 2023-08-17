package com.zjweu.cigarette.controller;

import com.zjweu.cigarette.pojo.Result;
import com.zjweu.cigarette.pojo.UserCategory;
import com.zjweu.cigarette.service.UserCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 用户组管理Controller
 */
@Slf4j
@RequestMapping("/category")
@RestController
public class CategoryController {

    @Autowired
    private UserCategoryService categoryService;

    @GetMapping
    public Result list(HttpServletRequest request){
        log.info("查询全部用户组信息");
        log.info("当前使用的实际地址为：{}",request.getServerName());
        List<UserCategory> categoryList=categoryService.list();
        return Result.success(categoryList);
    }

    //删除一个用户组
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("根据id删除用户组:{}",id);
        //调用service删除会员类型
        categoryService.deleteCategory(id);
        return Result.success();

    }
    //新增一个用户组
    @PostMapping
    public Result add(@RequestBody UserCategory userCategory){
        log.info("新增用户组:{}",userCategory);
        categoryService.save(userCategory);
        return Result.success();
    }

    //根据id查询一个用户组
    @GetMapping("/{id}")
    public Result getCategoryById(@PathVariable Integer id){
        log.info("根据id:{}查询用户组",id);
        UserCategory userCategory =categoryService.getById(id);
        return Result.success(userCategory);
    }

    //根据id修改一个用户组
    @PutMapping
    public Result modifyCategory(@RequestBody UserCategory userCategory){
        categoryService.update(userCategory);
        return Result.success();
    }
}
