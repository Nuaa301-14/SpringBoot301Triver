//package com.example.javacrawler.controller;
//
//
//import com.alibaba.fastjson.JSONException;
//
//import com.alibaba.fastjson.JSONObject;
//import com.example.javacrawler.entity.BookInfo;
//import com.example.javacrawler.entity.User;
//import com.example.javacrawler.service.AreaService;
//import com.example.javacrawler.service.HotelService;
//import com.example.javacrawler.service.InternationalAreaService;
//
//import com.example.javacrawler.service.UserService;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.IncorrectCredentialsException;
//import org.apache.shiro.authc.UnknownAccountException;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.subject.Subject;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//
//@Controller
//public class LogininControl {
//
//    @Autowired
//    private InternationalAreaService internationalAreaService;
//
//    @Autowired
//    private UserService userservice;
//
//    @Autowired
//    private AreaService areaService;
//
//    /**
//     * @RequestMapping("/login.html")
//     * Model ： 将传入下一个页面
//     */
//    @RequestMapping("/loginin")
//    public String loginHtml(Model model) {
//
//        model.addAttribute("result", "登陆页面");
//        return "loginin";
//    }
//
//    @RequestMapping("/onclick_login")
//    @ResponseBody
//    public String  onclick_login(@RequestBody List<Map<String,Object>> userList,HttpSession httpSession) {
//        String xm = "";
//        String psd = "";
//        Map<String,Object> userinfo = userList.get(0);
//        xm = (String) userinfo.get("name");
//        psd =(String) userinfo.get("password");
//        User user = userservice.getUserByName(xm);
//        if(user !=null){
//            if(user.getPassword().equalsIgnoreCase(psd)){
//                httpSession.setAttribute("userId",user.getId());
//                httpSession.setAttribute("username",user.getName());
//                httpSession.setAttribute("password",user.getPassword());
//                return String.valueOf(user.getId());
//            }
//        }
//        return "0";
//    }
//
//
//}
//
