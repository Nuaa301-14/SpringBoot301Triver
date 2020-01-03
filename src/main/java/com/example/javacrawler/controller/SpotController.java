package com.example.javacrawler.controller;

import com.example.javacrawler.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/spot")
public class SpotController {
    @Autowired
    private SpotService spotService;

    @RequestMapping(value = "/admin/delete")
    @ResponseBody
    public Map<String, Object> spotDelete(
            @RequestParam("spotId") String hotelId) {
        List<String> reply=new ArrayList<>();
        Map map=new HashMap();
        map.put("spotId",hotelId);
        int size=spotService.delete(map);
        if (size==1){
            // 删除成功
            reply.add("Success");
        }else {
            reply.add("Fail");
        }
        Map<String,Object> map1=new HashMap<>();
        map1.put("reply",reply);
        return map1;
    }

    @RequestMapping(value = "/admin/detail")
    public String spotDetail() {
        return "Admin/spotDetail";
    }
}
