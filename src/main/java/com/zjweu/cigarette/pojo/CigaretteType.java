package com.zjweu.cigarette.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CigaretteType {
    private int typeId;
    private int typeFlag;
    private String typeName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format= "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime typeTime;
    private int typeBig;
}
