package com.example.javacrawler.controller;



import com.example.javacrawler.entity.Spot;
import com.example.javacrawler.entity.User;
import com.example.javacrawler.service.InternationalAreaService;

import com.example.javacrawler.service.SpotService;
import com.example.javacrawler.service.UserService;


import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;


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
    @RequestMapping("/io/scenery.html")
    public String loginHtml(Model model) {

        model.addAttribute("result", "登陆页面");
//        return "io/index.html";
        return "io/scenery.html";
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

    public List<Spot> randomget(List<Spot> list,int count){
        List<Spot> newlist = new ArrayList<>();
        Random random = new Random();
        for(int i = 0;i< count;i++){
            int target = random.nextInt(list.size());
            newlist.add(list.get(target));
        }
        return newlist;
    }
    @RequestMapping("/load_data")
    @ResponseBody
    public List<Spot> loaddata(){
        Map map = new HashMap();
        map.put("page",1);
        map.put("pageSize",100);
        PageInfo<Spot> Spotlist = spotservice.selectSpotList(map);
//        model.addAttribute("spotlist",Spotlist);
        List<Spot> spotlist = Spotlist.getList();

        return randomget(spotlist,9);
    }

    @RequestMapping("/onclick_search")
    @ResponseBody
    public PageInfo<Spot> onclick_search(@RequestBody List<Map<String,Object>> searchList, HttpSession httpSession) {
        Map<String, Object> inputCondition = searchList.get(0);
        Map<String, Object> pageCondition = searchList.get(1);

        Map map = new HashMap();
        String destination= (String) inputCondition.get("destination");
        String location= (String) inputCondition.get("location");
        String name= (String) inputCondition.get("name");
        String price= (String) inputCondition.get("price");

        List<String> condition= (List<String>) inputCondition.get("condition");
        List<String> sources=new ArrayList<>();
        List<String> degrees=new ArrayList<>();
        for (int i=0;i<condition.size();i++){
            String s = condition.get(i);
            String[] split = StringUtils.split(s,"|");
            if (split[0].equals("景区特色")){
                degrees.add(split[1]);
            }else if (split[0].equals("来源")){
                sources.add(split[1]);
            }
        }

        if (price.equals("")){
            map.put("beginPrice",0);
            map.put("maxPrice",666666);
        }else {
            String temp=StringUtils.split(price,"|")[1];
            String[] split = temp.split("-");
            map.put("beginPrice", Integer.parseInt(StringUtils.getDigits(split[0])));
            map.put("maxPrice",Integer.parseInt(StringUtils.getDigits(split[1])));
        }
        map.put("destination",destination);
        map.put("location",location);
        map.put("name",name);

        int page= (int) pageCondition.get("page");
        int pageSize=(int )pageCondition.get("pageSize");
        map.put("page",page);
        map.put("pageSize",pageSize);

        if (sources.size()==0){
            sources.add("携程");
            sources.add("同程");
            sources.add("艺龙");
            map.put("sources",sources);
        }else {
            map.put("sources",sources);
        }

        if (degrees.size()==0||degrees.size()==2){
            map.put("degrees",null);
        }else {
            map.put("degrees",degrees.get(0));
        }

        PageInfo<Spot> SpotPageInfo = spotservice.selectSpotList(map);

        System.out.println("当前页码：" + SpotPageInfo.getPageNum());
        System.out.println("每页记录条数：" + SpotPageInfo.getPageSize());
        System.out.println("总记录数：" + SpotPageInfo.getTotal());
        System.out.println("总页数：" + SpotPageInfo.getPages());
        System.out.println();
        return SpotPageInfo;


    }
}

