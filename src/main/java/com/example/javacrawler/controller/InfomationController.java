package com.example.javacrawler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/io")
public class InfomationController {


    @RequestMapping("/informations.html")
    public String index(){
        return "io/informations.html";
    }


}
