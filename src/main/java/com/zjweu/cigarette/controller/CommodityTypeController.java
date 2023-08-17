package com.zjweu.cigarette.controller;

import com.zjweu.cigarette.pojo.CigaretteType;
import com.zjweu.cigarette.pojo.PageBean;
import com.zjweu.cigarette.pojo.Result;
import com.zjweu.cigarette.pojo.User;
import com.zjweu.cigarette.service.CommodityTypeService;
import com.zjweu.cigarette.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/type")
public class CommodityTypeController {

    @Autowired
    private CommodityTypeService typeService;

    //分页查询商品分类列表
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String typeName, Integer typeFlag, Integer typeBig) {
        log.info("分页查询，参数:{},{},{},{},{},{}", page, pageSize, typeName, typeFlag, typeBig);
        PageBean pageBean = typeService.pageCommodityType(page, pageSize, typeName, typeFlag, typeBig);
        return Result.success(pageBean);
    }

    //根据id删除商品分类
    @DeleteMapping("/{idsString}")
    public Result delete(@PathVariable List<Integer> idsString) {
        log.info("批量删除，ids:{}", idsString);
        List<Integer> ids = idsString.subList(0, idsString.size());
        typeService.deleteById(ids);
        return Result.success();
    }

    //新增商品分类
    @PostMapping
    public Result save(@RequestBody CigaretteType type) {
        log.info("新增商品分类，type:{}", type);
        typeService.insert(type);
        return Result.success();
    }

    //根据类别id查询分类
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("根据ID查询商品类别信息，id：{}", id);
        CigaretteType type = typeService.getById(id);
        return Result.success(type);
    }

    //更新类别信息
    @PutMapping
    public Result update(@RequestBody CigaretteType type) {
        log.info("更新商品类别信息:{}", type);
        typeService.update(type);
        return Result.success();
    }

    //计算大类个数
    @GetMapping("/big")
    public Result getBigSum() {
        int bigSum=typeService.findAllBig();
        return Result.success(bigSum);
    }

    //计算小类个数
    @GetMapping("/small")
    public Result getSmallSum() {
        int smallSum=typeService.findAllSmall();
        return Result.success(smallSum);
    }

    //计算每个大类中小类个数
    @GetMapping("/smallinbig")
    public Result getSmallInBig() {
        Map<Integer, Object> smalledInBig = typeService.smallInBig();
        return Result.success(smalledInBig);
    }

    //计算每个小类中含有的商品数
    @GetMapping("/cominsmall")
    public Result getComInSmall() {
        Map<Integer, Object> comNum = typeService.comInSmall();
        return Result.success(comNum);
    }
}
