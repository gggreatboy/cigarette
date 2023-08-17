package com.zjweu.cigarette.controller;

import com.zjweu.cigarette.pojo.Result;
import com.zjweu.cigarette.pojo.User;
import com.zjweu.cigarette.service.UserService;
import com.zjweu.cigarette.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        log.info("用户登录：{}",user);
        User e=userService.login(user);

        //下发JWT令牌
        if(e!=null ){
            Map<String, Object> claims=new HashMap<>();
            claims.put("userId",e.getUserId());
            claims.put("userName",e.getUserName());
            claims.put("userBanci",e.getUserBanci());
            String jwt = JwtUtils.generateJwt(claims);
            log.info("登录成功，生成令牌:{}",jwt);
            return Result.success(jwt);
        }
        return Result.error("用户名或密码错误!");
    }
}
