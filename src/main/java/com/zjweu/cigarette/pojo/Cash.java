package com.zjweu.cigarette.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cash {
    private List<CashItem> cashItemList;
    private BigDecimal priceAll;
    private int cashPeople;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format= "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cashCreateTime;
}
