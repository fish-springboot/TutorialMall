package com.github.fish56.tutorialsmall.repository;

import com.alibaba.fastjson.JSONObject;
import com.github.fish56.tutorialsmall.TutorialsMallApplicationTests;
import com.github.fish56.tutorialsmall.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserCrudRepositoryTest extends TutorialsMallApplicationTests {
    @Autowired
    private UserCrudRepository repository;

    private String token = "asfweasdfa";
    @Test
    public void save(){
        User user = new User();
        user.setLogin("Jon Snow");
        user.setToken(token);
        repository.save(user);
        assertNotNull(user.getId());
        System.out.println(JSONObject.toJSONString(user, true));
    }

    @Test
    public void find(){
        User user = repository.findByLogin("Jon Snow");
        System.out.println(JSONObject.toJSONString(user, true));

        user = repository.findByToken(token);
        System.out.println(JSONObject.toJSONString(user, true));
    }
}