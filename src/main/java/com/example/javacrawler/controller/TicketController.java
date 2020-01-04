package com.example.javacrawler.controller;

import com.example.javacrawler.entity.GroupTravel;
import com.example.javacrawler.entity.Hotel;
import com.example.javacrawler.entity.Spot;
import com.example.javacrawler.service.HotelService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class TicketController {

    @Autowired
    HotelService hotelService;

    @RequestMapping("/io/ticket.html")
    public String index(){
        return "io/ticket.html";
    }


    public List<Hotel> randomget(List<Hotel> list, int count){
        List<Hotel> newlist = new ArrayList<>();
        Random random = new Random();
        for(int i = 0;i< count;i++){
            int target = random.nextInt(list.size());
            newlist.add(list.get(target));
        }
        return newlist;
    }
    @RequestMapping("/load_hotel")
    @ResponseBody
    public List<Hotel> loadhotel(){
        Map map = new HashMap();
        map.put("page",1);
        map.put("pageSize",9);
        PageInfo<Hotel> Hotellist = hotelService.selectHotelList(map);
        List<Hotel> spotlist = Hotellist.getList();
        return randomget(spotlist,9);
    }

    @RequestMapping("/onclick_searchhotel")
    @ResponseBody
    public PageInfo<Hotel> onclick_search(@RequestBody List<Map<String,Object>> searchList, HttpSession httpSession) {
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
        PageInfo<Hotel> hotelPageInfo = hotelService.selectHotelList(map);
        System.out.println("当前页码：" + hotelPageInfo.getPageNum());
        System.out.println("每页记录条数：" + hotelPageInfo.getPageSize());
        System.out.println("总记录数：" + hotelPageInfo.getTotal());
        System.out.println("总页数：" + hotelPageInfo.getPages());
        System.out.println();
        return hotelPageInfo;


    }
}
