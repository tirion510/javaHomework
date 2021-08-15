package com.tirion.manager;

import com.tirion.model.MyOrderDO;

import java.util.List;


/**
 * Manager for MyOrder.
 */
public interface MyOrderManager {

    /**
     * insert selective.
     */
    long insertSelective(MyOrderDO record);

    /**
     * select by query condition.
     */
    List<MyOrderDO> selectByQuery(MyOrderDO query);

}