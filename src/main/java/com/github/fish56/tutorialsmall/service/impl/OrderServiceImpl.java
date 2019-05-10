package com.github.fish56.tutorialsmall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.fish56.payjs.PayJS;
import com.github.fish56.payjs.request.CheckRequest;
import com.github.fish56.payjs.request.NativeRequest;
import com.github.fish56.payjs.response.CheckResponse;
import com.github.fish56.payjs.response.NativeResponse;
import com.github.fish56.tutorialsmall.entity.Order;
import com.github.fish56.tutorialsmall.entity.Tutorial;
import com.github.fish56.tutorialsmall.entity.User;
import com.github.fish56.tutorialsmall.repository.OrderCrudRepository;
import com.github.fish56.tutorialsmall.service.OrderService;
import com.github.fish56.tutorialsmall.service.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderCrudRepository repository;
    @Autowired
    private PayJS payJS;

    @Override
    public ServiceResponse<Order> fetch(User user, Tutorial tutorial) {
        ServiceResponse<Order> response = new ServiceResponse<>();

        log.info("fetching order. user : " + user.getId() + ", tutorial: " + tutorial.getId());
        Order order = repository.findByUserAndTutorial(user, tutorial);

        // 数据库已经有记录而且以及完成支付的话，直接返回当前订单
        if (order != null && order.getIsPaid()) {
            return response.setData(order);
        }

        // 对于处于未支付状态的订单，向PayJS查询订单
        if (order != null && !order.getIsPaid()) {
            CheckRequest checkRequest = new CheckRequest(order.getPaymentId());
            CheckResponse checkResponse = payJS.apiCheck(checkRequest);
            log.info("查询到的订单状态是：" + JSONObject.toJSONString(checkResponse));
            if (checkResponse.getStatus() == 1) {
                order.setIsPaid(true);
                repository.save(order);
            }
            return response.setData(order);
        }

        // 创建新的订单
        order = new Order();
        order.setTutorial(tutorial);
        order.setUser(user);
        order.setMoney(tutorial.getPrice());
        repository.save(order);
        if (order.getId() == null) {
            log.error("无法创建订单. user: " + user.getId() + ", tutorial: " + tutorial.getId());
            response.setErrorMessage("无法将订单插入数据库");
            return response.setErrorStatusCode(500);
        }

        log.info("准备向PayJS请求订单");
        NativeRequest nativeRequest =
                new NativeRequest(tutorial.getPrice(), String.valueOf(order.getId()));
        NativeResponse nativeResponse = payJS.apiNative(nativeRequest);

        if (nativeResponse.getReturn_code() == 0) {
            log.error("PayJs 请求订单出错: " + order.getId() + nativeResponse.getReturn_msg());
            response.setErrorMessage(nativeResponse.getReturn_msg());
            return response.setErrorStatusCode(500);
        }
        log.info("创建订单成功");

        order.setPaymentId(nativeResponse.getPayjs_order_id());
        order.setQrcode(nativeResponse.getQrcode());

        repository.save(order);

        return response.setData(order);
    }



    @Override
    public List<Order> findAll() {
        //return repository.findAll();
        return null;
    }
}
