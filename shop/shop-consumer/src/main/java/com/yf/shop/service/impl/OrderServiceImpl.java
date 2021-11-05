package com.yf.shop.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yf.shop.bean.UserAddress;
import com.yf.shop.service.OrderService;
import com.yf.shop.service.UserService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Reference
    UserService userService;

    @Override
    public List<UserAddress> initOrder(String userId) {
		System.out.println("用户id："+userId);
		List<UserAddress> addressList = userService.getUserAddressList("1");
		for (UserAddress userAddress : addressList) {
			System.out.println(userAddress.getUserAddress());
		}
		return addressList;
   }}