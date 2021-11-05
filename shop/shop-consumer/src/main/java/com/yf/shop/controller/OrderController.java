package com.yf.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yf.shop.bean.UserAddress;
import com.yf.shop.service.OrderService;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @ResponseBody   //以json格式返回
    @RequestMapping("/initOrder")
    public List<UserAddress> initOrder(@RequestParam("uid") String userId){

       return orderService.initOrder(userId);
    }

}