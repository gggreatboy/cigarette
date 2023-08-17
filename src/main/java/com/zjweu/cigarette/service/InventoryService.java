package com.zjweu.cigarette.service;

import com.zjweu.cigarette.pojo.Inventory;
import com.zjweu.cigarette.pojo.PageBean;

import java.util.List;

public interface InventoryService {
    //根据商品ID查询库存信息
    Inventory getByComId(Integer comId);


    //更新库存信息（商品进货、出货、销售、退货时需要同步完成该操作/在库存管理也可进行该操作）
    void update(Inventory inventory);


    //分页查询库存列表（仅在库存管理中使用）
    PageBean pageInventory(Integer page, Integer pageSize, String comName, String comProvider, String ComNumberLow, String ComNumberHigh);
}
