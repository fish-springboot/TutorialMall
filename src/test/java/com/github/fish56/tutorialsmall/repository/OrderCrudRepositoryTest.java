package com.github.fish56.tutorialsmall.repository;

import com.alibaba.fastjson.JSONObject;
import com.github.fish56.tutorialsmall.TutorialsMallApplicationTests;
import com.github.fish56.tutorialsmall.entity.Order;
import com.github.fish56.tutorialsmall.entity.Tutorial;
import com.github.fish56.tutorialsmall.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class OrderCrudRepositoryTest extends TutorialsMallApplicationTests {
    @Autowired
    private OrderCrudRepository repository;

    private Order saveOrder(Integer userId, Integer tutorialId){
        User user = new User();
        user.setId(userId);

        Tutorial tutorial = new Tutorial();
        tutorial.setId(tutorialId);

        Order order = new Order();
        order.setQrcode("123");
        order.setMoney(33);
        order.setPaymentId("ddddd");
        order.setTutorial(tutorial);
        order.setUser(user);

        return repository.save(order);
    }

    @Test
    public void save(){
        Order order = saveOrder(23, 33);
        System.out.println(JSONObject.toJSONString(order, true));
    }

    /**
     * 查询ID为2的用户的订单
     */
    @Test
    public void findByUser(){
        User user = new User();
        user.setId(2);
        List<Order> orders = repository.findByUser(user);
        System.out.println(JSONObject.toJSONString(orders));
    }

    @Test
    public void findByUserAndTutorial(){
        /**
         * 查询一个用户对某个课程的订单状况
         */
        User user = new User();
        user.setId(2);
        Tutorial tutorial = new Tutorial();
        tutorial.setId(1);
        Order order = repository.findByUserAndTutorial(user, tutorial);
        assertNull(order);

        /**
         * 创建一个订单，然后用来查询
         *   数据库中的订单应该以（userID + tutorialId）为不重复键
         *     所以这个测试用例不能重复调用
         */
        saveOrder(2, 1);

        order = repository.findByUserAndTutorial(user, tutorial);
        System.out.println(JSONObject.toJSONString(order));
    }
}