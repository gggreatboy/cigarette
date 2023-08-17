package com.zjweu.cigarette.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zjweu.cigarette.mapper.InventoryMapper;
import com.zjweu.cigarette.pojo.Commodity;
import com.zjweu.cigarette.pojo.Inventory;
import com.zjweu.cigarette.pojo.PageBean;
import com.zjweu.cigarette.pojo.Result;
import com.zjweu.cigarette.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public Inventory getByComId(Integer comId) {
        return inventoryMapper.getByComId(comId);
    }


    @Override
    public void update(Inventory inventory) {
        inventoryMapper.update(inventory);
    }

    @Override
    public PageBean pageInventory(Integer page, Integer pageSize, String comName, String comProvider, String ComNumberLow, String ComNumberHigh) {
        PageHelper.startPage(page,pageSize);
        List<Inventory> inventoryList = inventoryMapper.list(comName, comProvider, ComNumberLow, ComNumberHigh);
        Page<Inventory> p = (Page<Inventory>)inventoryList;//封装类强转page类型
        PageBean pageBean=new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }

}

