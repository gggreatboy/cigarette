package com.zjweu.cigarette.controller;

import com.zjweu.cigarette.pojo.*;
import com.zjweu.cigarette.service.CommodityService;
import com.zjweu.cigarette.service.InventoryService;
import com.zjweu.cigarette.service.OrderService;
import com.zjweu.cigarette.utils.GetUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cash")
public class CashController {
    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private OrderService orderService;


    //点击某一商品加入购物车按钮后触发
    @Transactional
    @PostMapping("/{count}")
    public Result addCom (@RequestBody Commodity commodity, @PathVariable Integer count, HttpSession session){
        Inventory inventory = inventoryService.getByComId(commodity.getComId());
        //判断库存是否大于要购买的数量
        if(inventory.getInventoryNumber()<count){
            return Result.error("该商品库存不足！当前库存为："+inventory.getInventoryNumber());
        }

        //判断session中是否存在cashList
        if(session.getAttribute("cashList")==null) {
            session.setMaxInactiveInterval(3600*24);
            session.setAttribute("cashList", new Cash());
        }

        //读取session中原有商品
        Cash cashList = (Cash) session.getAttribute("cashList");
        List<CashItem> cashItemList = cashList.getCashItemList();

        //若增加商品已存在购物车，则只增加数量
        for (CashItem cashItem : cashItemList) {
            if(cashItem.getCommodity().getComId()==commodity.getComId()){
                //增加数量
                cashItem.setComCount(cashItem.getComCount()+count);
                //更改总价
                cashList.setPriceAll(cashList.getPriceAll().add(
                        (cashItem.getPriceFinal())
                                .multiply(BigDecimal
                                        .valueOf(cashItem.getComCount())
                                )
                        )
                );
                //暂时修改仓库值
                inventory.setInventoryNumber(inventory.getInventoryNumber()-count);

                //向session中返回最新购物车
                cashList.setCashItemList(cashItemList);
                session.setAttribute("cashList", cashList);

                //返回最新购物车信息
                return  Result.success(cashList);
            }
        }

        //若增加商品未在购物车，则加入购物车
        //编号从1开始
        int itemId= cashItemList.size()+1;
        CashItem cashItem = new CashItem();
        cashItem.setItemId(itemId);
        cashItem.setCommodity(commodity);
        cashItem.setComCount(count);
        BigDecimal priceFinal= commodity.getComRetail().multiply(BigDecimal.valueOf(commodity.getComDiscount()/100));
        cashItem.setPriceFinal(priceFinal);

        //将cashItem加入cashItemList
        cashItemList.add(cashItem);
        cashList.setCashItemList(cashItemList);

        //计算总价
        for (CashItem item : cashItemList) {
            cashList.setPriceAll(cashList.getPriceAll().add
                    (
                            (item.getPriceFinal())
                                    .multiply(
                                            BigDecimal
                                                    .valueOf(item.getComCount())
                                    )
                    )
            );
        }

        //暂时修改仓库值
        inventory.setInventoryNumber(inventory.getInventoryNumber()-count);
        //向session中返回最新购物车
        session.setAttribute("cashList", cashList);
        return Result.success(cashList);

    }

    //在购物车内点击删除某商品后触发
    @Transactional
    @DeleteMapping("/{id}")
    public Result deleteCom(@PathVariable String id,HttpSession session,@RequestBody CashItem cashItem){

        //读取session中原有商品
        Cash cashList = (Cash) session.getAttribute("cashList");
        List<CashItem> cashItemList = cashList.getCashItemList();

        //更改总价
        cashList.setPriceAll(cashList.getPriceAll().subtract(
                        (cashItem.getPriceFinal())
                                .multiply(BigDecimal
                                        .valueOf(cashItem.getComCount())
                                )
                )
        );

        //改回仓库值
        Inventory inventory = inventoryService.getByComId(cashItem.getCommodity().getComId());
        inventory.setInventoryNumber(inventory.getInventoryNumber()+cashItem.getComCount());

        //找出该商品并进行删除
        for (CashItem item : cashItemList) {
            if(item.getItemId()==cashItem.getItemId()){
                cashItemList.remove(item);
            }
        }
        cashList.setCashItemList(cashItemList);

        //向session中返回最新购物车
        session.setAttribute("cashList", cashList);

        //返回最新购物车信息
        return  Result.success(cashList);

    }

    //点击清空购物车
    @Transactional
    @DeleteMapping
    public Result deleteAllCom(HttpSession session){
        //读取session中原有商品
        Cash cashList = (Cash) session.getAttribute("cashList");
        List<CashItem> cashItemList = cashList.getCashItemList();

        //复原库存
        for (CashItem cashItem : cashItemList) {
            Inventory inventory = inventoryService.getByComId(cashItem.getCommodity().getComId());
            inventory.setInventoryNumber(inventory.getInventoryNumber()+cashItem.getComCount());
        }

        //删除session中的内容
        session.invalidate();
        Cash newCash=new Cash();
        return  Result.success(newCash);
    }

    //在购物车内点击商品数量增加后触发
    @Transactional
    @GetMapping("/add/{count}")
    public Result addCount(@PathVariable Integer count,@RequestBody CashItem cashItem,HttpSession session){
        //判断库存是否足够
        Inventory inventory = inventoryService.getByComId(cashItem.getCommodity().getComId());
        //判断库存是否大于要购买的数量
        if(inventory.getInventoryNumber()<count){
            return Result.error("该商品库存不足！当前库存为："+inventory.getInventoryNumber());
        }

        //获取session中数据
        Cash cashList = (Cash) session.getAttribute("cashList");
        List<CashItem> cashItemList = cashList.getCashItemList();
        //找到该商品并增加该商品数量
        for (CashItem item : cashItemList) {
            if(item.getItemId()==cashItem.getItemId()){
                item.setComCount(item.getComCount()+count);

                //更改总价
                cashList.setPriceAll(
                        cashList.getPriceAll()
                        .add(
                                item.getPriceFinal()
                                        .multiply(BigDecimal.valueOf(count))
                        )
                );
            }
        }
        //更改仓库值
        inventory.setInventoryNumber(inventory.getInventoryNumber()-count);

        //向session中存入最新购物车
        cashList.setCashItemList(cashItemList);
        session.setAttribute("cashList", cashList);
        //返回最新购物车信息
        return  Result.success(cashList);
    }


    //在购物车内点击商品数量减少后触发
    @Transactional
    @GetMapping("/sub/{count}")
    public Result subCount(@PathVariable Integer count,@RequestBody CashItem cashItem,HttpSession session){
        //判断当前商品数量减少后是否大于0
        if(cashItem.getComCount()<=count){
            return Result.error("当前无法继续减少数量！若需在订单删除该商品请点击删除按钮！");
        }

        //获取session中数据
        Cash cashList = (Cash) session.getAttribute("cashList");
        List<CashItem> cashItemList = cashList.getCashItemList();
        //找到该商品并增加该商品数量
        for (CashItem item : cashItemList) {
            if(item.getItemId()==cashItem.getItemId()){
                item.setComCount(item.getComCount()-count);

                //更改总价
                cashList.setPriceAll(
                        cashList.getPriceAll()
                                .subtract(
                                        item.getPriceFinal()
                                                .multiply(BigDecimal.valueOf(count))
                                )
                );
            }
        }
        //更改仓库值
        Inventory inventory = inventoryService.getByComId(cashItem.getCommodity().getComId());
        inventory.setInventoryNumber(inventory.getInventoryNumber()+count);

        //向session中存入最新购物车
        cashList.setCashItemList(cashItemList);
        session.setAttribute("cashList", cashList);
        //返回最新购物车信息
        return  Result.success(cashList);
    }

    //提交购物车按钮跳转付款弹窗
    @GetMapping("/confirm")
    public Result confirmCash(HttpSession session, HttpServletRequest request){
        //判断session中是否存在cashList
        if(session.getAttribute("cashList")==null) {
            return Result.error("清单中无商品！");
        }

        //获取session中数据
        Cash cashList = (Cash) session.getAttribute("cashList");
        BigDecimal priceAll = cashList.getPriceAll();
        Order order = new Order();
        order.setOrderMoney(priceAll);
        User userNow = GetUser.getUser(request);
        order.setOrderUserId(userNow.getUserId());
        return Result.success(order);
    }


    //完成付款，生成订单,清空购物车
    @Transactional
    @GetMapping("/check")
    public Result checkAll(@RequestBody Order order, HttpSession session){
        LocalDateTime formatDateTime = LocalDateTime.now();
        order.setOrderTime(formatDateTime);

        //生成订单
        orderService.insert(order);

        //清空session
        session.invalidate();
        Cash newCash=new Cash();
        return Result.success(newCash);
    }

}

