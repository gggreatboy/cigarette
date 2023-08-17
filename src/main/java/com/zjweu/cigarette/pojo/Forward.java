package com.zjweu.cigarette.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @TableName forward
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Forward implements Serializable {
    private Integer forwardId;
    private Integer forwardComId;
    private String forwardComName;
    private String forwardComUnit;
    private Integer sum;
    private String forwardPeople;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format= "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime forwardTime;

}