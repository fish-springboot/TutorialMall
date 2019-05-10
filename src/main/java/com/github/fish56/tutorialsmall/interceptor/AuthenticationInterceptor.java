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
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * 进行统一的登录认证
 */
@Configuration
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private UserCrudRepository userCrudRepository;

    @Value("${github.auth_url}")
    private String authUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 这是我们的登录路由，不拦截
        if (request.getServletPath().startsWith("/github")){
            log.debug("不拦截" + request.getServletPath() + "的请求");
            return true;
        }
        log.info("getting: " + request.getRequestURL().toString());
        Cookie[] cookies = request.getCookies();
        User user;

        // 遍历Cookie，寻找token
        for (Cookie cookie : cookies){
            // 如果找到了，就判断token的有效性
            if (cookie.getName().equals("github_token")){
                log.info("用户尝试使用token登录" + cookie.getValue());
                user = userCrudRepository.findByToken(cookie.getValue());
                if (user != null) {

                    log.info("用户" + user.getLogin() + "登录了");
                    request.setAttribute("user", user);
                    return true;
                }
            }
        }

        log.info("没有找到token，让用户跳转到GitHub的登录页面登录");
        response.setStatus(302);
        response.setHeader("Location", authUrl);

        // 将当前url记录到session，方便登陆后跳转
        HttpSession session = request.getSession();
        session.setAttribute("lastVisitUrl", request.getRequestURL());

        return false;
    }
}
