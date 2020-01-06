package com.example.javacrawler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/io")
public class SceneryController {


    @RequestMapping("/index.html")
    public String index(){
        return "io/index";
    }


}
