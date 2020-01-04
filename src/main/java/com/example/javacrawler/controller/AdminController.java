package com.example.javacrawler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final String USERNAME_PASSWORD_NOT_MATCH = "用户名或密码错误";

    @RequestMapping({"", "/"})
    public String adminLogin() {
//        return "Admin/index";
        return "redirect:/";
    }

    @RequestMapping("/links")
    public String links() {
        return "Admin/linksList";
    }

    @RequestMapping("/index")
    public String index(){
        return "Admin/index";
    }

    @RequestMapping(value = {"/crawlHotelReg"})
    public String crawlRegulate(){
        return "Admin/hotelRegulation";
    }

    @RequestMapping(value = {"/crawlSpotReg"})
    public String crawlSpotReg(){
        return "Admin/spotRegulation";
    }
    @RequestMapping(value = {"/crawlScenicHotelReg"})
    public String crwalScenicHotelReg(){
        return "Admin/sceHotelRegulation";
    }
    @RequestMapping(value = {"/crawlGroupTrvReg"})
    public String crwalGroupReg(){
        return "Admin/groupTrvRegulation";
    }
    @RequestMapping(value = {"/allUsers.html"})
    public String allUser(){
        return "Admin/allUsers";
    }




}
