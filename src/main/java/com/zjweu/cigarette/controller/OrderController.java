package com.zjweu.cigarette.controller;

import com.zjweu.cigarette.pojo.*;
import com.zjweu.cigarette.service.OrderService;
import com.zjweu.cigarette.utils.GetUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //分页查询订单
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       Integer orderUserId, Integer orderPay, LocalDateTime begin, LocalDateTime end, BigDecimal low, BigDecimal high) {
        log.info("分页查询，参数:{},{},{},{}", page, pageSize,orderUserId,orderPay,begin,end,low,high);
        PageBean pageBean = orderService.pageOrder(page, pageSize,orderUserId,orderPay,begin,end,low,high);
        return Result.success(pageBean);
    }

    //根据id删除订单
    @DeleteMapping("/{idsString}")
    public Result delete(@PathVariable List<Integer> idsString){
        log.info("批量删除，ids:{}",idsString);
        List<Integer> ids=idsString.subList(0,idsString.size());
        orderService.deleteById(ids);
        return Result.success();
    }


    //新增订单
    @PostMapping
    public Result save(@RequestBody Order order, ServletRequest servletRequest){
        log.info("新增订单，order:{}",order);

        User user = GetUser.getUser((HttpServletRequest) servletRequest);

        order.setOrderUserId(user.getUserId());

        orderService.insert(order);
        return Result.success();
    }


    //根据id查询订单
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("根据ID查询一条订单记录，id：{}",id);
        Order order = orderService.getById(id);
        return  Result.success(order);
    }

    @GetMapping("/allmoney")
    public  Result getAllMoney(){
        BigDecimal allMoney = orderService.getAllMoney();
        return  Result.success(allMoney);
    }

    //查询查询各个收款渠道收款量
    @GetMapping("/moneybyway")
    public  Result getMoneyByWay(){
        Map<Integer, BigDecimal> moneyByWay = orderService.getMoneyByWay();
        return  Result.success(moneyByWay);
    }
}
