package com.zjweu.cigarette.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashItem {
    private int ItemId;
    private Commodity commodity;
    private BigDecimal priceFinal;//折扣后单价
    private int comCount;  //数量


}
