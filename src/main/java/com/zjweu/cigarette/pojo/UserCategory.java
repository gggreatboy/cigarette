package com.zjweu.cigarette.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCategory {
    private Integer categoryId; //用户组ID
    private String categoryName; //用户组名称
    private String categoryPermission;//用户组权限
    private String categoryShow ; //名称+权限组合
}
