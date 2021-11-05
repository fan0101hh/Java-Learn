package com.yf.shop.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yf.shop.bean.UserAddress;
import com.yf.shop.service.UserService;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Service   //属于Dubbo的@Service注解，非Spring  作用：暴露服务
@Component
public class UserServiceImpl implements UserService {

    @Override
    public List<UserAddress> getUserAddressList(String userId) {
    	UserAddress address1 = new UserAddress();
        UserAddress address2 = new UserAddress();
        
        address1.setId(1);
    	address1.setUserAddress("北京市昌平区宏福科技园综合楼3层");
    	address1.setUserId("1");
    	address1.setConsignee("李老师");
    	address1.setPhoneNum("010-56253825");
    	address1.setIsDefault("Y");
    	
    	address2.setId(2);
    	address2.setUserAddress("深圳市宝安区西部硅谷大厦B座9层");
    	address2.setUserId("1");
    	address2.setConsignee("王老师");
    	address2.setPhoneNum("010-56253825");
    	address2.setIsDefault("N");
        return Arrays.asList(address1, address2);
    }}