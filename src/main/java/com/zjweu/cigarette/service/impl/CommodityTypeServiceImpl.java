package com.zjweu.cigarette.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zjweu.cigarette.mapper.CommodityMapper;
import com.zjweu.cigarette.mapper.CommodityTypeMapper;
import com.zjweu.cigarette.mapper.InventoryMapper;
import com.zjweu.cigarette.pojo.CigaretteType;
import com.zjweu.cigarette.pojo.Commodity;
import com.zjweu.cigarette.pojo.PageBean;
import com.zjweu.cigarette.service.CommodityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class CommodityTypeServiceImpl implements CommodityTypeService {

    @Autowired
    private CommodityTypeMapper typeMapper;

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public CigaretteType getById(Integer typeId) {
        return typeMapper.getById(typeId);
    }

    @Override
    public void insert(CigaretteType type) {
        if(type.getTypeFlag()==1){
            type.setTypeBig(type.getTypeId());
        }
        typeMapper.insert(type);
    }

    @Override
    public void update(CigaretteType type) {
        typeMapper.update(type);
    }

    @Transactional
    @Override
    public void deleteById(List<Integer> ids) {
        typeMapper.deleteById(ids);
        commodityMapper.deleteByComId(ids);
        inventoryMapper.deleteByComId(ids);

    }

    @Override
    public PageBean pageCommodityType(Integer page, Integer pageSize, String typeName, Integer typeFlag, Integer typeBig) {
        PageHelper.startPage(page,pageSize);
        List<CigaretteType> typeList = typeMapper.list(typeName, typeFlag, typeBig);
        Page<CigaretteType> p = (Page<CigaretteType>)typeList;//封装类强转page类型
        PageBean pageBean=new PageBean(p.getTotal(),p.getResult());
        return pageBean;

    }

    @Override
    public Integer findAllBig() {
        return typeMapper.findAllBig();
    }

    @Override
    public Integer findAllSmall() {
        return typeMapper.findAllSmall();
    }

    @Override
    public Map<Integer, Object> smallInBig() {
        return typeMapper.smallInBig();
    }

    @Override
    public Map<Integer, Object> comInSmall() {
        return commodityMapper.comInSmall();
    }
}
