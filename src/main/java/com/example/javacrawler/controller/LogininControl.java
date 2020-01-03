package com.example.javacrawler.controller;



import com.example.javacrawler.entity.Spot;
import com.example.javacrawler.entity.User;
import com.example.javacrawler.service.InternationalAreaService;

import com.example.javacrawler.service.SpotService;
import com.example.javacrawler.service.UserService;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class LogininControl {

    @Autowired
    private InternationalAreaService internationalAreaService;

    @Autowired
    private UserService userservice;

    @Autowired
    private SpotService spotservice;

    /**
     * @RequestMapping("/login.html")
     * Model ： 将传入下一个页面
     */
    @RequestMapping("/io/index.html")
    public String loginHtml(Model model) {

        model.addAttribute("result", "登陆页面");
        return "io/index.html";
    }

    @RequestMapping("/onclick_login")
    @ResponseBody
    public String  onclick_login(@RequestBody List<Map<String,Object>> userList,HttpSession httpSession) {
        String xm = "";
        String psd = "";
        Map<String,Object> userinfo = userList.get(0);
        xm = (String) userinfo.get("name");
        psd =(String) userinfo.get("password");
        User user = userservice.getUserByName(xm);
        if(user !=null){
            if(user.getPassword().equalsIgnoreCase(psd)){
                httpSession.setAttribute("userId",user.getId());
                httpSession.setAttribute("username",user.getName());
                httpSession.setAttribute("password",user.getPassword());
                return String.valueOf(user.getId());
            }
        }
        return "0";
    }

    @RequestMapping("/load_data")
    @ResponseBody
    public List<Spot> loaddata(){
        Map map = new HashMap();
        map.put("page",1);
        map.put("pageSize",9);
        PageInfo<Spot> Spotlist = spotservice.selectSpotList(map);
//        model.addAttribute("spotlist",Spotlist);

        return Spotlist.getList();
    }

    @RequestMapping("/onclick_search")
    @ResponseBody
    public PageInfo<Spot> onclick_search(@RequestBody List<Map<String,Object>> searchList, HttpSession httpSession) {
        String text = "";
        String type = "";
        Map<String, Object> searchifo = searchList.get(0);
        text = (String) searchifo.get("text");
        type = (String) searchifo.get("type");
        Map map = new HashMap();
        map.put("page", 1);
        map.put("pageSize", 9);
        map.put("input", text);
        map.put("size", "3");

        if (type.equals("spot")) {
            PageInfo<Spot> spotPageInfo = spotservice.searchSpot(map);
            System.out.println("当前页码：" + spotPageInfo.getPageNum());
            System.out.println("每页记录条数：" + spotPageInfo.getPageSize());
            System.out.println("总记录数：" + spotPageInfo.getTotal());
            System.out.println("总页数：" + spotPageInfo.getPages());
            System.out.println();
            return spotPageInfo;
        }
        return null;
    }
}

