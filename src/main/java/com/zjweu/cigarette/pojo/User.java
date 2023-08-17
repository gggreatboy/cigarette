package com.zjweu.cigarette.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//收银员用户实体类
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
   private int userId;
   private String userName;
   private String userPassword;
   private int userAge;
   private int userBanci;
   private int userCategory;
}
