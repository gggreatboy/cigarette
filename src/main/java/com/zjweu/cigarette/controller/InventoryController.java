package com.zjweu.cigarette.controller;

import com.zjweu.cigarette.pojo.*;
import com.zjweu.cigarette.service.CommodityTypeService;
import com.zjweu.cigarette.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    //根据id查询一条库存
    @GetMapping("/{id}")
    public Result getCategoryById(@PathVariable Integer id){
        log.info("根据id:{}查询库存",id);
        Inventory inventory = inventoryService.getByComId(id);
        return Result.success(inventory);
    }

    //根据id更新一个库存
    @PutMapping
    public Result modifyInventory(@RequestBody Inventory inventory){
        inventoryService.update(inventory);
        return Result.success();
    }

    //分页查询库存
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String comName, String comProvider, String ComNumberLow, String ComNumberHigh) {
        log.info("分页查询，参数:{},{},{},{},{},{}", page, pageSize,comName,comProvider,ComNumberLow,ComNumberHigh);
        PageBean pageBean = inventoryService.pageInventory(page, pageSize,comName,comProvider,ComNumberLow,ComNumberHigh);
        return Result.success(pageBean);
    }
}
