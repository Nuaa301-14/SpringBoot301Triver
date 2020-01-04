package com.example.javacrawler.controller;

import com.example.javacrawler.entity.*;
import com.example.javacrawler.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/data")
public class DataController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private SpotService spotService;

    @Autowired
    private ScenicHotelService scenicHotelService;

    @Autowired
    private GroupTravelService groupTravelService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/userInfo")
    @ResponseBody
    public Map<String,Object> userInfo(@RequestParam("id") String id){
        Map<String ,Object> map=new HashMap<>();
        User user=userService.seletebyId(Integer.parseInt(id));
        map.put("userInfo",user);
        return map;
    }

    @RequestMapping("/user")
    @ResponseBody
    public void userAll(@RequestParam(defaultValue = "1", required = false) int page,
                        @RequestParam(defaultValue = "13", required = false) int pageSize){
        Map map = new HashMap();
        map.put("page", page);
        map.put("pageSize", pageSize);
        List<User> userList= userService.seleteList(map);
        PageInfo<User> pageInfo=new PageInfo<>(userList);

    }


    @RequestMapping("/hotelSearch")
    @ResponseBody
    public PageInfo<Hotel> hotelSerarch(
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "13", required = false) int pageSize,
            @RequestParam(defaultValue = "", required = false) String input,
            @RequestParam(defaultValue = "", required = false) String source,
            @RequestParam(defaultValue = "1", required = false) int size) {
        Map map = new HashMap();
        map.put("page", page);
        map.put("pageSize", pageSize);
        map.put("input", input);
        map.put("source", source);
        String s = String.valueOf(size);
        map.put("size", s);
        PageInfo<Hotel> hotelPageInfo = hotelService.searchHotel(map);
        System.out.println("当前页码：" + hotelPageInfo.getPageNum());
        System.out.println("每页记录条数：" + hotelPageInfo.getPageSize());
        System.out.println("总记录数：" + hotelPageInfo.getTotal());
        System.out.println("总页数：" + hotelPageInfo.getPages());
        System.out.println();
        return hotelPageInfo;
    }


    @RequestMapping("/hotel")
    @ResponseBody
    public PageInfo<Hotel> HotelData(
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "13", required = false) int pageSize,
            @RequestParam(defaultValue = "", required = false) String area,
            @RequestParam(defaultValue = "", required = false) String source,
            @RequestParam(defaultValue = "", required = false) String order,
            Model model) {
        Map map = new HashMap();
        map.put("page", page);
        map.put("pageSize", pageSize);
        map.put("area", area);
        map.put("source", source);
        // seq desc
        map.put("order", order);


        PageInfo<Hotel> hotelPageInfo = hotelService.selectHotelList(map);
        System.out.println("当前页码：" + hotelPageInfo.getPageNum());
        System.out.println("每页记录条数：" + hotelPageInfo.getPageSize());
        System.out.println("总记录数：" + hotelPageInfo.getTotal());
        System.out.println("总页数：" + hotelPageInfo.getPages());

        return hotelPageInfo;
    }

    @RequestMapping("/spot")
    @ResponseBody
    public PageInfo<Spot> SpotData(@RequestParam(defaultValue = "1", required = false) int page,
                                   @RequestParam(defaultValue = "13", required = false) int pageSize,
                                   @RequestParam(defaultValue = "", required = false) String area,
                                   @RequestParam(defaultValue = "", required = false) String source,
                                   @RequestParam(defaultValue = "", required = false) String order,
                                   Model model) {

        Map map = new HashMap();
        map.put("page", page);
        map.put("pageSize", pageSize);
        map.put("area", area);
        map.put("source", source);
        map.put("order", order);
        PageInfo<Spot> spotPageInfo = spotService.selectSpotList(map);
        System.out.println("当前页码：" + spotPageInfo.getPageNum());
        System.out.println("每页记录条数：" + spotPageInfo.getPageSize());
        System.out.println("总记录数：" + spotPageInfo.getTotal());
        System.out.println("总页数：" + spotPageInfo.getPages());
        return spotPageInfo;
    }

    @RequestMapping("/spotSearch")
    @ResponseBody
    public PageInfo<Spot> spotSerarch(
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "13", required = false) int pageSize,
            @RequestParam(defaultValue = "", required = false) String input,
            @RequestParam(defaultValue = "", required = false) String source,
            @RequestParam(defaultValue = "1", required = false) int size) {
        Map map = new HashMap();
        map.put("page", page);
        map.put("pageSize", pageSize);
        map.put("input", input);
        map.put("source", source);
        String s = String.valueOf(size);
        map.put("size", s);
        PageInfo<Spot> spotPageInfo = spotService.searchSpot(map);
        System.out.println("当前页码：" + spotPageInfo.getPageNum());
        System.out.println("每页记录条数：" + spotPageInfo.getPageSize());
        System.out.println("总记录数：" + spotPageInfo.getTotal());
        System.out.println("总页数：" + spotPageInfo.getPages());
        System.out.println();
        return spotPageInfo;
    }


    @RequestMapping("/scenicHotel")
    @ResponseBody
    public PageInfo<SpotAndHotel> ScenicHotelData(@RequestParam(defaultValue = "1", required = false) int page,
                                                  @RequestParam(defaultValue = "13", required = false) int pageSize,
                                                  @RequestParam(defaultValue = "", required = false) String area,
                                                  @RequestParam(defaultValue = "", required = false) String source,
                                                  Model model) {
        Map map = new HashMap();
        map.put("page", page);
        map.put("pageSize", pageSize);
        if (area == null || area.equals("")) {
            area = null;
        }
        map.put("area", area);
        if (source == null || source.equals("")) {
            source = null;
        }
        map.put("source", source);
        PageInfo<SpotAndHotel> spotAndHotelPageInfo = scenicHotelService.selectScenicHotelList(map);
        System.out.println("当前页码：" + spotAndHotelPageInfo.getPageNum());
        System.out.println("每页记录条数：" + spotAndHotelPageInfo.getPageSize());
        System.out.println("总记录数：" + spotAndHotelPageInfo.getTotal());
        System.out.println("总页数：" + spotAndHotelPageInfo.getPages());
        return spotAndHotelPageInfo;
    }


    @RequestMapping("/group_travel")
    @ResponseBody
    public PageInfo<GroupTravel> groupTravel(
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "13", required = false) int pageSize,
            @RequestParam(defaultValue = "", required = false) String area,
            @RequestParam(defaultValue = "", required = false) String source,
            @RequestParam(defaultValue = "", required = false) String order) {
        Map map = new HashMap();
        map.put("page", page);
        map.put("pageSize", pageSize);
        map.put("area", area);
        map.put("source", source);
        // seq desc
        map.put("order", order);
        PageInfo<GroupTravel>  groupTravelPageInfo= groupTravelService.selectGroupList(map);
        System.out.println("当前页码：" + groupTravelPageInfo.getPageNum());
        System.out.println("每页记录条数：" + groupTravelPageInfo.getPageSize());
        System.out.println("总记录数：" + groupTravelPageInfo.getTotal());
        System.out.println("总页数：" + groupTravelPageInfo.getPages());
        System.out.println();
        return groupTravelPageInfo;
    }

    @RequestMapping("/groupSearch")
    @ResponseBody
    public PageInfo<GroupTravel> groupTravelSerarch(
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "13", required = false) int pageSize,
            @RequestParam(defaultValue = "", required = false) String input,
            @RequestParam(defaultValue = "", required = false) String source,
            @RequestParam(defaultValue = "1", required = false) int size) {
        Map map = new HashMap();
        map.put("page", page);
        map.put("pageSize", pageSize);
        map.put("input", input);
        map.put("source", source);
        String s = String.valueOf(size);
        map.put("size", s);
        PageInfo<GroupTravel> groupTravelPageInfo=groupTravelService.searchGroup(map);
        System.out.println("当前页码：" + groupTravelPageInfo.getPageNum());
        System.out.println("每页记录条数：" + groupTravelPageInfo.getPageSize());
        System.out.println("总记录数：" + groupTravelPageInfo.getTotal());
        System.out.println("总页数：" + groupTravelPageInfo.getPages());
        System.out.println();
        return groupTravelPageInfo;
    }

}
