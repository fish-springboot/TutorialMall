package com.github.fish56.tutorialsmall.repository;

import com.alibaba.fastjson.JSONObject;
import com.github.fish56.tutorialsmall.TutorialsMallApplicationTests;
import com.github.fish56.tutorialsmall.entity.Tutorial;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.Assert.*;

public class TutorialCrudRepositoryTest extends TutorialsMallApplicationTests {
    @Autowired
    private TutorialCrudRepository repository;

    /**
     * 创建一个课程
     */
    @Test
    public void save(){
        Tutorial tutorial = new Tutorial();
        tutorial.setOwner("fish56");
        tutorial.setRepositoryName("EMall-SSM");
        tutorial.setPrice(300);
        repository.save(tutorial);
        assertNotNull(tutorial.getId());
    }

    /**
     * 查询课程
     */
    @Test
    public void find(){
        Optional<Tutorial> tutorial = repository.findById(1);
        assertTrue(tutorial.isPresent());

        Tutorial tutorial1 = repository.findByOwnerAndRepositoryName("fish56", "EMall-SSM");
        System.out.println(JSONObject.toJSONString(tutorial1));
    }
}