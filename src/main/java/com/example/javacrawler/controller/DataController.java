package com.example.javacrawler.controller;

import com.example.javacrawler.entity.Hotel;
import com.example.javacrawler.entity.Spot;
import com.example.javacrawler.entity.SpotAndHotel;
import com.example.javacrawler.service.HotelService;
import com.example.javacrawler.service.ScenicHotelService;
import com.example.javacrawler.service.SpotService;
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
        String s= String.valueOf(size);
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
        PageInfo<Spot> spotPageInfo = spotService.selectSpotList(map);
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
        return spotAndHotelPageInfo;
    }
}
