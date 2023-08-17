package com.zjweu.cigarette.controller;

import com.zjweu.cigarette.pojo.*;
import com.zjweu.cigarette.service.ForwardService;
import com.zjweu.cigarette.utils.GetUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/forward")
public class ForwardController {

    @Autowired
    private ForwardService forwardService;


    //分页查询结转记录
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String comName, String people, LocalDateTime begin, LocalDateTime end) {
        log.info("分页查询，参数:{},{},{},{}", page, pageSize,comName,people,begin,end);
        PageBean pageBean = forwardService.pageForward(page, pageSize,comName,people,begin,end);
        return Result.success(pageBean);
    }

    //根据id删除结转记录
    @DeleteMapping("/{idsString}")
    public Result delete(@PathVariable List<Integer> idsString){
        log.info("批量删除，ids:{}",idsString);
        List<Integer> ids=idsString.subList(0,idsString.size());
        forwardService.deleteById(ids);
        return Result.success();
    }

    //新增结转记录(原有记录上新增）
    @PostMapping("/origin")
    public Result saveOrigin(@RequestBody Forward forward,ServletRequest servletRequest){
        log.info("根据原有结转记录新增结转记录，forward:{}",forward);

        User user = GetUser.getUser((HttpServletRequest) servletRequest);

        forwardService.insert(forward,user);
        return Result.success();
    }


    //新增结转记录(按钮新增）
    @PostMapping
    public Result save(@RequestBody Commodity commodity, ServletRequest servletRequest){
        log.info("根据按钮新增结转记录，commodity:{}",commodity);

        User user = GetUser.getUser((HttpServletRequest) servletRequest);

        forwardService.insert(commodity,user);
        return Result.success();
    }



    //根据id查询结转记录
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("根据ID查询一条结转记录，id：{}",id);
        Forward forward=forwardService.getById(id);
        return  Result.success(forward);
    }
}
