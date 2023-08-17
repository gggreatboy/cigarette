package com.zjweu.cigarette.mapper;

import com.zjweu.cigarette.pojo.Forward;
import com.zjweu.cigarette.pojo.Inventory;
import com.zjweu.cigarette.pojo.Order;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    //新增一条订单记录
    void insert(Order order);

    //查询所有订单记录
    public List<Order> list(Integer orderUserId, Integer orderPay, LocalDateTime begin, LocalDateTime end, BigDecimal low,BigDecimal high);

    //批量删除订单记录
    void deleteById(List<Integer> ids);


    //查询一条订单记录
    Order getById(Integer id);


    //查询销售总收款
    BigDecimal getAllMoney();

    @MapKey("order_pay")
    //查询各个收款渠道收款量
    Map<Integer,BigDecimal>getMoneyByWay();
}
