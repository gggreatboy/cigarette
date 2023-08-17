package com.zjweu.cigarette.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commodity {
    private int comId;
    private String comName;
    private String comCode1;
    private String comCode2;
    private String comUnit;
    private int comType1;
    private int comType2;
    private BigDecimal comRetail;
    private BigDecimal comCost;
    private int comDiscount;
    private int comNumber;  //当前库存，默认为null，从库存表查到后存入
    private String comTypeName1; //大类名称，默认为null，用于前端展示
    private String comTypeName2; //小类名称，默认为null，用于前端展示
}
