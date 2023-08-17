package com.zjweu.cigarette.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.math.BigDecimal;


/**
* 
* @TableName order
*/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    private int orderId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format= "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderTime;
    private int orderUserId;
    private BigDecimal orderMoney;
    private int orderPay;


}
