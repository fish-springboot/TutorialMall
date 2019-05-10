package com.github.fish56.tutorialsmall.interceptor;

import com.github.fish56.tutorialsmall.entity.User;
import com.github.fish56.tutorialsmall.repository.UserCrudRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Configuration
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private UserCrudRepository userCrudRepository;

    @Value("${github.auth_url}")
    private String authUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        PrintWriter printWriter = response.getWriter();
        User user;

        // 遍历Cookie，寻找token
        for (Cookie cookie : cookies){
            // 如果找到了，就判断token的有效性
            if (cookie.getName().equals("github_token")){
                log.info("用户尝试使用token登录" + cookie.getValue());
                user = userCrudRepository.findByToken(cookie.getValue());
                if (user == null) {
                    // 在数据库中没有找到token对应的用户
                    response.setStatus(401);
                    printWriter.print("token过期或者实现");
                    return false;
                }
                // 找到了用户，就放行，交给Controller层
                request.setAttribute("user", user);
                return true;
            }
        }

        log.info("没有找到token，让用户跳转到GitHub的登录页面登录");
        response.setStatus(302);
        response.setHeader("Location", authUrl);
        return false;
    }
}
