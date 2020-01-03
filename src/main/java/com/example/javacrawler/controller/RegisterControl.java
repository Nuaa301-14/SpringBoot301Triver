//package com.example.javacrawler.controller;
//
//
//import com.example.javacrawler.entity.User;
//import com.example.javacrawler.service.AreaService;
//import com.example.javacrawler.service.InternationalAreaService;
//import com.example.javacrawler.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpSession;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//
//@Controller
//public class RegisterControl {
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
//    @RequestMapping("/register")
//    public String loginHtml(Model model) {
//
//        model.addAttribute("result", "登陆页面");
//        return "register";
//    }
//
//    @RequestMapping("/onclick_register")
//    @ResponseBody
//    public int  onclick_login(@RequestBody List<Map<String,Object>> userList,HttpSession httpSession) {
//        String xm = "";
//        String psd = "";
//        Map<String,Object> userinfo = userList.get(0);
//        xm = (String) userinfo.get("name");
//        psd =(String) userinfo.get("password");
//        User user = userservice.getUserByName(xm);
//        if(user !=null){
//            return 0;
//        }
//       else{
//            User newuser = new User();
//            Random r = new Random();
//            newuser.setId(r.nextInt());
//            newuser.setName(xm);
//            newuser.setPassword(psd);
//            int id = userservice.insert(newuser);
//            return id;
//        }
//    }
//
//
//}
//
