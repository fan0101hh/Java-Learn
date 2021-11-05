package com.yf.shop.service;

import java.util.List;

import com.yf.shop.bean.UserAddress;



public interface OrderService {
	
	//初始化订单
    public List<UserAddress> initOrder(String userId);
}
