package com.zjweu.cigarette.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zjweu.cigarette.mapper.CommodityMapper;
import com.zjweu.cigarette.mapper.CommodityTypeMapper;
import com.zjweu.cigarette.mapper.InventoryMapper;
import com.zjweu.cigarette.pojo.*;
import com.zjweu.cigarette.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private CommodityTypeMapper typeMapper;

    @Transactional
    @Override
    public PageBean pageCommodity(Integer page, Integer pageSize,String comName, String comCode1, String comCode2, Integer comType1) {
        PageHelper.startPage(page,pageSize);
        List<Commodity> comList=commodityMapper.list(comName,comCode1,comCode2,comType1);
        for (Commodity commodity : comList) {
            explain(commodity);
        }
        Page<Commodity> p = (Page<Commodity>)comList;//封装类强转page类型
        PageBean pageBean=new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }

    private void explain(Commodity commodity) {
        Inventory comId = inventoryMapper.getByComId(commodity.getComId());
        commodity.setComNumber(comId.getInventoryNumber());

        CigaretteType type1 = typeMapper.getById(commodity.getComType1());
        commodity.setComTypeName1(type1.getTypeName());

        CigaretteType type2 = typeMapper.getById(commodity.getComType2());
        commodity.setComTypeName2(type2.getTypeName());
    }

    @Transactional
    @Override
    public Commodity getById(Integer commodityId) {
        Commodity com = commodityMapper.getById(commodityId);
        explain(com);
        return com;
    }

    @Transactional
    @Override
    public void insert(Commodity commodity) {
        commodityMapper.insert(commodity);
        Inventory inventory = new Inventory();
        inventory.setInventoryComId(commodity.getComId());
        inventory.setInventoryComName(commodity.getComName());
        inventory.setInventoryNumber(0);
        inventoryMapper.insert(inventory);
    }

    @Override
    public void update(Commodity commodity) {
        commodityMapper.update(commodity);
    }

    @Transactional
    @Override
    public void deleteByComId(List<Integer> ids) {
        commodityMapper.deleteByComId(ids);
        inventoryMapper.deleteByComId(ids);
    }

}
