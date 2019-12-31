package com.example.javacrawler.controller;


import com.example.javacrawler.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于页面的重定向
 */
@Controller
public class RedirectController {
    @RequestMapping({"/",""})
    public String goMainPage(){
        return "redirect:/login.html";
    }

    /**
     * @RequestMapping("/login.html") 就可以直接访问login.html了
     * Model ： 将传入下一个页面
     */
    @RequestMapping("/login.html")
    public String loginHtml(Model model) {

        return "Admin/login";
    }

    @RequestMapping(value="/user/register")
    public String register(){
        return "register";
    }

    @RequestMapping(value = "/registerSuccess")
    public String registerSuccess(HttpServletRequest request,Model model){
        User registerUser = (User) request.getSession().getAttribute("registerUser");
        model.addAttribute("registerUser",registerUser);
        return "registerSuccess";
    }


    /**
     * 返回linksAdd界面
     * @return
     */
    @RequestMapping(value = "/linksAdd.html")
    public String toLinksAdd(){
        System.out.println("here");
        return "Admin/linksAdd";
    }
}
