package com.example.javacrawler.controller;

import com.example.javacrawler.entity.GroupTravel;
import com.example.javacrawler.service.GroupTravelService;
import com.example.javacrawler.service.SpotService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/io")
public class InfomationController {

    @Autowired
    private GroupTravelService grouptravelservice;


    @RequestMapping("/informations.html")
    public String index(){
        return "io/informations.html";
    }


//    @RequestMapping("/load_data")
//    @ResponseBody
//    public List<GroupTravel> loaddata(){
//        Map map = new HashMap();
//        map.put("page",1);
//        map.put("pageSize",9);
//       PageInfo<GroupTravel> GroupTravellist = groupt
//       model.addAttribute("spotlist",Spotlist);
//
//        return Spotlist.getList();
//    }
}
