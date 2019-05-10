package com.github.fish56.tutorialsmall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.fish56.tutorialsmall.TutorialsMallApplicationTests;
import com.github.fish56.tutorialsmall.entity.Order;
import com.github.fish56.tutorialsmall.entity.Tutorial;
import com.github.fish56.tutorialsmall.entity.User;
import com.github.fish56.tutorialsmall.repository.TutorialCrudRepository;
import com.github.fish56.tutorialsmall.repository.UserCrudRepository;
import com.github.fish56.tutorialsmall.service.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

@Slf4j
public class OrderServiceImplTest extends TutorialsMallApplicationTests {
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private UserCrudRepository userCrudRepository;
    @Autowired
    private TutorialCrudRepository tutorialCrudRepository;

    @Test
    public void fetch() {
        User user = new User();
        user.setId(2);
        user.setLogin("Jon");
        //userCrudRepository.save(user);

        Tutorial tutorial = new Tutorial();
        tutorial.setPrice(3);
        tutorial.setOwner("fish56");
        tutorial.setRepositoryName("EMall-SSM");
        tutorial.setId(3);
        //tutorialCrudRepository.save(tutorial);

        ServiceResponse<Order> orderServiceResponse = orderService.fetch(user, tutorial);
        if (orderServiceResponse.hasError()) {
            log.error(orderServiceResponse.getErrorMessage());
        }
        System.out.println(JSONObject.toJSONString(orderServiceResponse.getData()));
    }
}