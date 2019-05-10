package com.github.fish56.tutorialsmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RootController {
    @RequestMapping("/**")
    public String undefinedPath(){
        return "all";
    }
}
