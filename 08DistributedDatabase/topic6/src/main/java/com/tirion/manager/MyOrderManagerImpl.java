package com.tirion.manager;

import com.tirion.dao.MyOrderMapper;
import com.tirion.model.MyOrderDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Manager for MyOrder.
 */

@Component
public class MyOrderManagerImpl implements MyOrderManager{


    
    @Autowired
    protected MyOrderMapper myOrderMapper;


    /**
     * insert selective.
     */
    @Override
    public long insertSelective(MyOrderDO record){
        return myOrderMapper.insertSelective(record);
    }

    /**
     * select by query condition.
     */
    @Override
    public List<MyOrderDO> selectByQuery(MyOrderDO query){
        return myOrderMapper.selectByQuery(query);
    }

}