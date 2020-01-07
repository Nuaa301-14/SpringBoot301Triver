package com.example.javacrawler.controller;

import com.example.javacrawler.entity.GroupTravel;
import com.example.javacrawler.entity.Hotel;
import com.example.javacrawler.entity.Spot;
import com.example.javacrawler.service.HotelService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
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

        Map<String, Object> inputCondition = searchList.get(0);
        Map<String, Object> pageCondition = searchList.get(1);

        Map map = new HashMap();
        String destination= (String) inputCondition.get("destination");
        String address= (String) inputCondition.get("address");
        String name= (String) inputCondition.get("name");
        String price= (String) inputCondition.get("price");

        List<String> condition= (List<String>) inputCondition.get("condition");
        List<String> sources=new ArrayList<>();
        List<String> degrees=new ArrayList<>();
        for (int i=0;i<condition.size();i++){
            String s = condition.get(i);
            String[] split = StringUtils.split(s,"|");
            if (split[0].equals("酒店星级")){
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
        map.put("address",address);
        map.put("name",name);

        int page= (int) pageCondition.get("page");
        int pageSize=(int )pageCondition.get("pageSize");
        int flag= (int) inputCondition.get("price_sort");
        map.put("page",page);
        map.put("pageSize",pageSize);
        map.put("price_sort",String.valueOf(flag));


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

//        if (type.equals("spot")) {
        PageInfo<Hotel> hotelPageInfo = hotelService.selectHotelList(map);
//        if(flag == 1){
//            Collections.sort(hotelPageInfo.getList(),new Comparator<Hotel>(){
//                @Override
//                public int compare(Hotel h1,Hotel h2){
//                    return  h1.getPrice()-h2.getPrice();
//                }
//            });
//        }

        System.out.println("当前页码：" + hotelPageInfo.getPageNum());
        System.out.println("每页记录条数：" + hotelPageInfo.getPageSize());
        System.out.println("总记录数：" + hotelPageInfo.getTotal());
        System.out.println("总页数：" + hotelPageInfo.getPages());
        System.out.println();
        return hotelPageInfo;


    }
}
