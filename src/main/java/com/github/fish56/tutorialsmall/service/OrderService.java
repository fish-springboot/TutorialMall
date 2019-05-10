package com.github.fish56.tutorialsmall.service;

import com.github.fish56.tutorialsmall.entity.Order;
import com.github.fish56.tutorialsmall.entity.Tutorial;
import com.github.fish56.tutorialsmall.entity.User;

import java.util.List;

public interface OrderService {
    /**
     * 通过user + tutorial 获取订单
     *   - 订单不存在： 创建订单并返回
     *   - 订单已经存在，则判断是否完成支付
     *     - 已经处于支付状态，则直接返回该订单
     *     - 处于未支付状态，则发起请求来刷新状态
     * @param user
     * @param tutorial
     * @return
     */
    public ServiceResponse<Order> fetch(User user, Tutorial tutorial);

    /**
     * 获得所有订单，供后台查询使用
     * @return
     */
    public List<Order> findAll();
}
