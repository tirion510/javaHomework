package com.tirion.model;


import lombok.ToString;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.Date;

@Mapper
@ToString
public class MyOrderDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * This field corresponds to the database column my_order.id
     */
    private Long id;

    /**
     * This field corresponds to the database column my_order.order_id
     */
    private Long orderId;

    /**
     * This field corresponds to the database column my_order.user_id
     */
    private Long userId;

    /**
     * This field corresponds to the database column my_order.order_price
     */
    private String orderPrice;

    /**
     * This field corresponds to the database column my_order.order_status
     */
    private Integer orderStatus;

    /**
     * This field corresponds to the database column my_order.pay_status
     */
    private Integer payStatus;

    /**
     * This field corresponds to the database column my_order.is_delete
     */
    private Integer isDelete;

    /**
     * This field corresponds to the database column my_order.gmt_create
     */
    private Date gmtCreate;

    /**
     * This field corresponds to the database column my_order.gmt_modified
     */
    private Date gmtModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}