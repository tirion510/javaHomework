package com.tirion.dao;


import com.tirion.model.MyOrderDO;

import java.util.List;

/**
 * MyBatis Mapper for MyOrder.
 */
public interface MyOrderMapper {

    /**
     * insert selective.
     */
    int insertSelective(MyOrderDO record);

    /**
     * select by query condition.
     */
    List<MyOrderDO> selectByQuery(MyOrderDO query);

}