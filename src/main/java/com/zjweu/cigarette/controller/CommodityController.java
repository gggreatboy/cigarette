package com.zjweu.cigarette.controller;


import com.zjweu.cigarette.pojo.Commodity;
import com.zjweu.cigarette.pojo.PageBean;
import com.zjweu.cigarette.pojo.Result;
import com.zjweu.cigarette.service.CommodityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/commodities")
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    //分页查询商品
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String comName, String comCode1, String comCode2, Integer comType1) {
        log.info("分页查询，参数:{},{},{},{},{},{}", page, pageSize,comName,comCode1,comCode2,comType1);
        PageBean pageBean = commodityService.pageCommodity(page, pageSize,comName,comCode1,comCode2,comType1);
        return Result.success(pageBean);
    }

    //根据id删除商品
    @DeleteMapping("/{idsString}")
    public Result delete(@PathVariable List<Integer> idsString){
        log.info("批量删除，ids:{}",idsString);
        List<Integer> ids=idsString.subList(0,idsString.size());
        commodityService.deleteByComId(ids);
        return Result.success();
    }

    //新增商品
    @PostMapping
    public Result save(@RequestBody Commodity commodity){
        log.info("新增商品，commodity:{}",commodity);
        commodityService.insert(commodity);
        return Result.success();
    }

    //根据id查询商品
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("根据ID查询商品信息，id：{}",id);
        Commodity commodity = commodityService.getById(id);
        return  Result.success(commodity);
    }

    //更新商品信息
    @PutMapping
    public Result update(@RequestBody Commodity commodity){
        log.info("更新商品信息:{}",commodity);
        commodityService.update(commodity);
        return Result.success();
    }



}
