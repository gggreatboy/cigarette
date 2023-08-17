package com.zjweu.cigarette.utils;

import com.zjweu.cigarette.pojo.User;
import com.zjweu.cigarette.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
@Slf4j
public class GetUser {


    private static final String AUTHORIZE_TOKEN = "token";
    public static User getUser(HttpServletRequest servletRequest) {
        HttpServletRequest request = servletRequest;
        String token = request.getHeader(AUTHORIZE_TOKEN);
        log.info("从请求头中获取的令牌：{}",token);
        Claims userinfo = JwtUtils.parseJWT(token);

        Integer userId = (Integer) userinfo.get("userId");
        String userName= userinfo.get("userName").toString();
        Integer userBanci = (Integer) userinfo.get("userBanci");
        User user = new User();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setUserBanci(userBanci);
        return user;
    }

}
