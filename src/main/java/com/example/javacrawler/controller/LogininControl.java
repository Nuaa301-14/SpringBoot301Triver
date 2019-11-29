package com.example.javacrawler.controller;


import com.example.javacrawler.entity.BookInfo;
import com.example.javacrawler.service.AreaService;
import com.example.javacrawler.service.HotelService;
import com.example.javacrawler.service.InternationalAreaService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class LogininControl {

    @Autowired
    private InternationalAreaService internationalAreaService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private AreaService areaService;

    /**
     * @RequestMapping("/login.html")
     * Model ： 将传入下一个页面
     */
    @RequestMapping("/loginin")
    public String loginHtml(Model model) {

        model.addAttribute("result", "登陆页面");
        return "loginin";
    }

    @RequestMapping(value = "loginin", method = RequestMethod.POST)
    public String login(Model model,
                        @RequestParam("userName") String name,
                        @RequestParam("password") String psw) {

        /**
         * 使用shiro编写认证操作
         *
         */
        // 1 获取Subject对象
        Subject subject= SecurityUtils.getSubject();

        // 2 封装用户数据
        UsernamePasswordToken token=new UsernamePasswordToken(name,psw);
        try {
            // 3执行登陆方法
            subject.login(token);
            // 登陆成功
            List<BookInfo> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                BookInfo bookInfo = new BookInfo();
                bookInfo.title = "小王子";
                bookInfo.author = "老外";
                bookInfo.data = "2019/09/12";
                list.add(bookInfo);
            }
            model.addAttribute("list", list);
            model.addAttribute("name", name);
            return "list";
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户名不存在");
            return "login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "login";
        }
    }


}

