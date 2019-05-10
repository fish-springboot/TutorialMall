package com.github.fish56.tutorialsmall.controller;

import com.github.fish56.tutorialsmall.entity.User;
import com.github.fish56.tutorialsmall.github.MyGitHubClient;
import com.github.fish56.tutorialsmall.repository.UserCrudRepository;
import com.github.fish56.tutorialsmall.service.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@Slf4j
public class GitHubLoginController {
    @Autowired
    private MyGitHubClient myGitHubClient;
    @Autowired
    private UserCrudRepository userRepository;

    @RequestMapping("/github/login")
    public Object login(@RequestParam String code, HttpServletResponse response, HttpSession session) {
        ServiceResponse<String> actionResponse = myGitHubClient.getTokenFromCode(code);

        if (actionResponse.hasError()) {
            log.error("获得token时出错了: " + actionResponse.getErrorMessage());
            return ResponseEntity.status(actionResponse.getErrorStatusCode())
                    .body(actionResponse.getErrorMessage());
        }

        String token = actionResponse.getData();
        log.info("token: " + token);

        ServiceResponse<User> userActionResponse = myGitHubClient.getUserFromToken(token);
        if (userActionResponse.hasError()) {
            log.error("通过token获得用户信息时出错了: " + actionResponse.getErrorMessage());
            return ResponseEntity.status(userActionResponse.getErrorStatusCode())
                    .body(userActionResponse.getErrorMessage());
        }

        // 将用户信息保存到数据库
        userRepository.save(userActionResponse.getData());
        log.info("请求完成");

        Cookie cookie = new Cookie("github_token", token);
        cookie.setHttpOnly(false);
        cookie.setMaxAge(20000);
        // 不设置的话默认为当前路由/github，肯定是不行的
        cookie.setPath("/");
        response.addCookie(cookie);

        // 返回数据，并设置token
        return ResponseEntity.status(302)
                .header("Location", session.getAttribute("lastVisitUrl").toString())
                .build();
    }
}
