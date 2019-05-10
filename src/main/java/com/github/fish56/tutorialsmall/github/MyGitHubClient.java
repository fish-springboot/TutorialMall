package com.github.fish56.tutorialsmall.github;

import com.alibaba.fastjson.JSONObject;
import com.github.fish56.tutorialsmall.entity.User;
import com.github.fish56.tutorialsmall.service.ServiceResponse;
import com.github.fish56.tutorialsmall.util.UrlUtil;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Slf4j
@Configuration
public class MyGitHubClient {

    @Value("${github.access_token_url}")
    private String tokenUrl;

    public ServiceResponse<String> getTokenFromCode(String code){
        ServiceResponse<String> actionResponse = new ServiceResponse<>();

        String url = tokenUrl + code;
        log.info("准备获得token：" + url);

        HttpResponse<String> response;
        try{
            response = Unirest.post(url).asString();
        }catch (Exception e) {
            log.error(e.getMessage());
            actionResponse.setErrorMessage(e.getMessage());
            actionResponse.setErrorStatusCode(500);
            return actionResponse;
        }
        log.info("get response after send code to github: " + response.getBody());

        String token = UrlUtil.queryToMap(response.getBody()).get("access_token");
        if (token == null) {
            log.error("无法获得token：" + response.getBody());
            actionResponse.setErrorMessage(response.getBody());
            actionResponse.setErrorStatusCode(500);
        }
        log.info("access_token: " + token);
        return actionResponse.setData(token);
    }

    public ServiceResponse<User> getUserFromToken(String token) {
        ServiceResponse<User> actionResponse = new ServiceResponse<>();

        // 这个东西是一个用户的token对应一个client，所以不是单例模式
        GitHubClient gitHubClient = new GitHubClient();
        gitHubClient.setOAuth2Token(token);

        UserService userService = new UserService(gitHubClient);
        org.eclipse.egit.github.core.User githubUser;
        try {
            githubUser = userService.getUser();
            log.info("用户登录信息是：" + JSONObject.toJSONString(githubUser));
        } catch (IOException e) {
            e.printStackTrace();
            log.warn("IO异常： " + e.getMessage());
            actionResponse.setErrorMessage(e.getMessage());
            actionResponse.setErrorStatusCode(500);
            return actionResponse;
        }

        User user = new User();
        user.setToken(token);
        user.setLogin(githubUser.getLogin());

        return actionResponse.setData(user);
    }
}
