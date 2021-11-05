package com.yf.shop.service;

import java.util.List;

import com.yf.shop.bean.UserAddress;


public interface UserService {
	//按照用户id返回所有的收货地址
    public List<UserAddress> getUserAddressList(String userId);
}
