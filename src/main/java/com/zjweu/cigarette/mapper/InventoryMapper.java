package com.zjweu.cigarette.mapper;
import com.zjweu.cigarette.pojo.Inventory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InventoryMapper {

    //根据商品ID查询库存信息
    Inventory getByComId(Integer comId);

    //新增一条库存（新增商品时同步完成，不允许在库存管理中新增）
    void insert(Inventory inventory);

    //根据商品id批量删除商品库存信息（删除商品时同步完成，不允许在库存管理中删除）
    void deleteByComId(List<Integer> ids);


    //更新库存信息（商品进货、出货、销售、退货时需要同步完成该操作/在库存管理也可进行该操作）
    void update(Inventory inventory);


    //分页查询库存列表（仅在库存管理中使用）
    public List<Inventory> list(String comName,String comProvider,String ComNumberLow,String ComNumberHigh);
}
