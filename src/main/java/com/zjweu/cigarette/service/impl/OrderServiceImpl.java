package com.zjweu.cigarette.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zjweu.cigarette.mapper.OrderMapper;
import com.zjweu.cigarette.pojo.CigaretteType;
import com.zjweu.cigarette.pojo.Forward;
import com.zjweu.cigarette.pojo.Order;
import com.zjweu.cigarette.pojo.PageBean;
import com.zjweu.cigarette.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;


    @Override
    public void insert(Order order) {
        LocalDateTime formatDateTime = LocalDateTime.now();
        order.setOrderTime(formatDateTime);

        orderMapper.insert(order);
    }

    @Override
    public PageBean pageOrder(Integer page, Integer pageSize, Integer orderUserId, Integer orderPay, LocalDateTime begin, LocalDateTime end, BigDecimal low, BigDecimal high) {
        PageHelper.startPage(page,pageSize);
        List<Order> ordersList = orderMapper.list(orderUserId, orderPay, begin, end, low, high);
        Page<Order> p = (Page<Order>)ordersList;//封装类强转page类型
        PageBean pageBean=new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }

    @Override
    public void deleteById(List<Integer> ids) {
        orderMapper.deleteById(ids);
    }

    @Override
    public Order getById(Integer id) {
        return orderMapper.getById(id);
    }

    //查询销售总收款
    @Override
    public BigDecimal getAllMoney() {
        return orderMapper.getAllMoney();
    }


    //查询各个收款渠道收款量
    @Override
    public Map<Integer, BigDecimal> getMoneyByWay() {
        return orderMapper.getMoneyByWay();
    }
}
