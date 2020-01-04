package com.example.javacrawler.controller;

import com.example.javacrawler.entity.GroupTravel;
import com.example.javacrawler.entity.Spot;
import com.example.javacrawler.service.GroupTravelService;
import com.example.javacrawler.service.SpotService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class InfomationController {

    @Autowired
    private GroupTravelService grouptravelservice;


    @RequestMapping("/io/informations.html")
    public String index(){
        return "io/informations.html";
    }



    public List<GroupTravel> randomget(List<GroupTravel> list, int count){
        List<GroupTravel> newlist = new ArrayList<>();
        Random random = new Random();
        for(int i = 0;i< count;i++){
            int target = random.nextInt(list.size());
            newlist.add(list.get(target));
        }
        return newlist;
    }
    @RequestMapping("/load_group")
    @ResponseBody
    public List<GroupTravel> loadgroup(){
        Map map = new HashMap();
        map.put("page",1);
        map.put("pageSize",9);
       PageInfo<GroupTravel> GroupTravellist = grouptravelservice.selectGroupList(map);
       List<GroupTravel> spotlist = GroupTravellist.getList();
        return randomget(spotlist,9);
    }

    @RequestMapping("/onclick_searchgroup")
    @ResponseBody
    public PageInfo<GroupTravel> onclick_search(@RequestBody List<Map<String,Object>> searchList, HttpSession httpSession) {
        String text = "";
//        String type = "";
        int yehao = 1;
        Map<String, Object> searchifo = searchList.get(0);
        text = (String) searchifo.get("text");
//        type = (String) searchifo.get("type");
        yehao = (int) searchifo.get("yehao");
        Map map = new HashMap();
        map.put("page", yehao);
        map.put("pageSize", 9);
        map.put("input", text);
        map.put("size", "3");

//        if (type.equals("spot")) {
        PageInfo<GroupTravel> groupTravelPageInfo = grouptravelservice.selectGroupList(map);
        System.out.println("当前页码：" + groupTravelPageInfo.getPageNum());
        System.out.println("每页记录条数：" + groupTravelPageInfo.getPageSize());
        System.out.println("总记录数：" + groupTravelPageInfo.getTotal());
        System.out.println("总页数：" + groupTravelPageInfo.getPages());
        System.out.println();
        return groupTravelPageInfo;


    }
}
