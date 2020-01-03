package com.example.javacrawler.controller;

import com.example.javacrawler.entity.Hotel;
import com.example.javacrawler.service.AreaService;
import com.example.javacrawler.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/hotel")
public class HotelControl {
    @Autowired
    private HotelService hotelService;

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/hello")
    public String hello() {
        Hotel hotel = hotelService.getHotel("123456");
        System.out.println(hotel.getComprehensive());
        System.out.println(hotel.getHotelLocation());
        System.out.println("springboot");
        return "Hello";
    }

    @RequestMapping(value = "/hello1")
    public String hello1() {
        return "ddd";
    }

    @RequestMapping(value = "/admin/detail")
    public String hotelDetail() {
        return "Admin/hotelDetail";
    }

    @PostMapping(value = "/admin/delete")
    @ResponseBody
    public Map<String, Object> hotelDelete(
            @RequestParam("hotelId") String hotelId) {
        List<String> reply=new ArrayList<>();
        Map map=new HashMap();
        map.put("hotelId",hotelId);
        int size=hotelService.delete(map);
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


    // 处理酒店 search 框


}
