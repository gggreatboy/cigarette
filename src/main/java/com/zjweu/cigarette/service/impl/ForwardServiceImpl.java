package com.zjweu.cigarette.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zjweu.cigarette.mapper.InventoryForwardMapper;
import com.zjweu.cigarette.mapper.InventoryMapper;
import com.zjweu.cigarette.pojo.*;
import com.zjweu.cigarette.service.ForwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ForwardServiceImpl implements ForwardService {

    @Autowired
    private InventoryForwardMapper forwardMapper;

    @Autowired
    private InventoryMapper inventoryMapper;


    //新增一条结转记录(通过原有结转记录条目上新增)
    @Transactional
    @Override
    public void insert(Forward forward,User user) {
        Forward forwardNew = new Forward();

        forwardNew.setForwardComId(forward.getForwardComId());
        forwardNew.setForwardComName(forward.getForwardComName());

        Inventory inventory = inventoryMapper.getByComId(forward.getForwardComId());

        forwardNew.setSum(inventory.getInventoryNumber());
        forwardNew.setForwardPeople(user.getUserName());


        LocalDateTime formatDateTime = LocalDateTime.now();
        forwardNew.setForwardTime(formatDateTime);

        forwardMapper.insert(forwardNew);
    }


    //新增一条结转记录（通过新增按钮新增）
    @Transactional
    @Override
    public void insert(Commodity commodity,User user) {
        Forward forwardNew = new Forward();

        forwardNew.setForwardComId(commodity.getComId());
        forwardNew.setForwardComName(commodity.getComName());

        Inventory inventory = inventoryMapper.getByComId(commodity.getComId());

        forwardNew.setSum(inventory.getInventoryNumber());
        forwardNew.setForwardPeople(user.getUserName());


        LocalDateTime formatDateTime = LocalDateTime.now();
        forwardNew.setForwardTime(formatDateTime);

        forwardMapper.insert(forwardNew);
    }

    @Override
    public void deleteById(List<Integer> ids) {
        forwardMapper.deleteById(ids);
    }

    @Override
    public Forward getById(Integer id) {
        return forwardMapper.getById(id);
    }

    @Override
    public PageBean pageForward(Integer page, Integer pageSize, String comName, String people, LocalDateTime begin, LocalDateTime end) {
        PageHelper.startPage(page,pageSize);
        List<Forward> forwardList = forwardMapper.list(comName, people,  begin, end);
        Page<Forward> p = (Page<Forward>) forwardList;//封装类强转page类型
        PageBean pageBean=new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }
}
