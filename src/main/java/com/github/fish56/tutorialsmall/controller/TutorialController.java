package com.github.fish56.tutorialsmall.controller;

import com.github.fish56.tutorialsmall.entity.Order;
import com.github.fish56.tutorialsmall.entity.Tutorial;
import com.github.fish56.tutorialsmall.entity.User;
import com.github.fish56.tutorialsmall.repository.TutorialCrudRepository;
import com.github.fish56.tutorialsmall.repository.UserCrudRepository;
import com.github.fish56.tutorialsmall.service.OrderService;
import com.github.fish56.tutorialsmall.service.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Configuration
@Controller
@RequestMapping("/tutorials")
@Slf4j
public class TutorialController {
    @Autowired
    private UserCrudRepository userCrudRepository;
    @Autowired
    private TutorialCrudRepository tutorialCrudRepository;
    @Autowired
    private OrderService orderService;

    @Value("${github.auth_url}")
    private String authUrl;

    @RequestMapping("/{owner}/{repoName}")
    public String getTutorials(@PathVariable String owner, @PathVariable String repoName,
                               @RequestAttribute User user, Model model){
        ServiceResponse<Order> orderServiceResponse = new ServiceResponse<>();

        Tutorial tutorial = tutorialCrudRepository.findByOwnerAndRepositoryName(owner, repoName);

        // todo 教程不存在，请前往。。。查阅
        if (tutorial == null) {
            log.info("用户访问的课程不存在" + owner + repoName);

            orderServiceResponse.setErrorMessage("访问的课程不存在");
            model.addAttribute("orderResponse", orderServiceResponse);
            // 直接返回，模板中自行展示数据
            return "tutorial";
        }

        orderServiceResponse = orderService.fetch(user, tutorial);
        model.addAttribute("orderResponse", orderServiceResponse);
        return "tutorial";

        // todo: 邀请到github


        // return null;
    }
}
