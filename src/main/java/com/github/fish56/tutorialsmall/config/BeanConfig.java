package com.github.fish56.tutorialsmall.config;

import com.github.fish56.payjs.PayJS;
import com.github.fish56.payjs.PayJSUnirest;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 配置一些bean
 */
@Component
public class BeanConfig {
    @Bean
    public PayJS payJS(){
        PayJS payJS = new PayJSUnirest("1528140051", "cGXJm7DH5NvQatOt");
        return payJS;
    }
}
