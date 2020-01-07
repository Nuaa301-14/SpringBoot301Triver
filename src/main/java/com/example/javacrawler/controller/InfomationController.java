package com.example.javacrawler.controller;

import com.example.javacrawler.entity.GroupTravel;
import com.example.javacrawler.entity.Hotel;
import com.example.javacrawler.entity.Spot;
import com.example.javacrawler.service.GroupTravelService;
import com.example.javacrawler.service.SpotService;
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
        Map<String, Object> inputCondition = searchList.get(0);
        Map<String, Object> pageCondition = searchList.get(1);

        Map map = new HashMap();
        String destination= (String) inputCondition.get("destination");
        String departure= (String) inputCondition.get("departure");
        String name= (String) inputCondition.get("name");
        String price= (String) inputCondition.get("price");

        List<String> condition= (List<String>) inputCondition.get("condition");
        List<String> sources=new ArrayList<>();
        List<String> degrees=new ArrayList<>();
        for (int i=0;i<condition.size();i++){
            String s = condition.get(i);
            String[] split = StringUtils.split(s,"|");
            if (split[0].equals("跟团游品质")){
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
        map.put("departure",departure);
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

        PageInfo<GroupTravel> grouptravelPageInfo = grouptravelservice.selectGroupList(map);

        System.out.println("当前页码：" + grouptravelPageInfo.getPageNum());
        System.out.println("每页记录条数：" + grouptravelPageInfo.getPageSize());
        System.out.println("总记录数：" + grouptravelPageInfo.getTotal());
        System.out.println("总页数：" + grouptravelPageInfo.getPages());
        System.out.println();
        return grouptravelPageInfo;


    }
}
