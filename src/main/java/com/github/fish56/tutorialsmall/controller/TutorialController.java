package com.github.fish56.tutorialsmall.controller;

import com.github.fish56.tutorialsmall.entity.Order;
import com.github.fish56.tutorialsmall.entity.Tutorial;
import com.github.fish56.tutorialsmall.entity.User;
import com.github.fish56.tutorialsmall.repository.TutorialCrudRepository;
import com.github.fish56.tutorialsmall.repository.UserCrudRepository;
import com.github.fish56.tutorialsmall.service.OrderService;
import com.github.fish56.tutorialsmall.service.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Configuration
@RestController
@RequestMapping("/tutorials")
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
    public Object getTutorials(@PathVariable String owner, @PathVariable String repoName,
                               @CookieValue(value = "github_token", name = "token") String token){
        User user = userCrudRepository.findByToken(token);
        if (user == null) {
            return ResponseEntity.status(302).header("Location", authUrl);
        }

        Tutorial tutorial = tutorialCrudRepository.findByOwnerAndRepositoryName(owner, repoName);

        // todo 教程不存在，请前往。。。查阅
        if (tutorial == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("教程不存在");
        }

        ServiceResponse<Order> orderServiceResponse = orderService.fetch(user, tutorial);

        if (orderServiceResponse.hasError()) {
            return ResponseEntity.status(orderServiceResponse.getErrorStatusCode())
                    .body(orderServiceResponse.getErrorMessage());
        }

        String qrcode = orderServiceResponse.getData().getQrcode();
        return qrcode;

        // todo: 邀请到github


        // return null;
    }
    @RequestMapping("/*")
    public String all(){
        return "页面开发中，请访问。。。。";
    }
}
