package com.zjweu.cigarette.service;

import com.zjweu.cigarette.pojo.Forward;
import com.zjweu.cigarette.pojo.Order;
import com.zjweu.cigarette.pojo.PageBean;
import org.apache.ibatis.annotations.MapKey;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface OrderService {

    //新增一条订单记录
    void insert(Order order);

    //查询所有订单记录
    PageBean pageOrder(Integer page, Integer pageSize, Integer orderUserId, Integer orderPay,
                         LocalDateTime begin, LocalDateTime end, BigDecimal low, BigDecimal high);

    //批量删除订单记录
    void deleteById(List<Integer> ids);


    //查询一条订单记录
    Order getById(Integer id);

    //查询销售总收款
    BigDecimal getAllMoney();


    //查询各个收款渠道收款量
    Map<Integer,BigDecimal> getMoneyByWay();

}
