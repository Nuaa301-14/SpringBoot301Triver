package com.example.javacrawler.controller;

import com.example.javacrawler.entity.Area;
import com.example.javacrawler.service.AreaService;
import com.example.javacrawler.service.HotelService;
import com.example.javacrawler.service.InternationalAreaService;
import com.example.javacrawler.task.CrawlHotelXC;
import com.example.javacrawler.task.CrawlInternationalArea;
import com.example.javacrawler.task.pipeline.CrawlHotelXCPipeline;
import com.example.javacrawler.task.pipeline.InternationalAreaPipeline;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Controller
public class TestControl {

    @Autowired
    private InternationalAreaService internationalAreaService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private AreaService areaService;



    @RequestMapping(value = "/crawlInternationalArea", method = RequestMethod.GET)
    @ResponseBody
    public void crawlInternationalArea(){
        CrawlInternationalArea crawlInternationalArea = new CrawlInternationalArea();
        crawlInternationalArea.crawlArea(new InternationalAreaPipeline(internationalAreaService));
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public String get() throws IOException {
//        return "hdsdfsdf";
        return Jsoup.connect("https://vacations.ctrip.com/list/scenichotel/sc.html/?sv=北京&st=北京").get().toString();
    }

    @RequestMapping(value = "crawl",method = RequestMethod.POST)
    @ResponseBody
    public String crawl(Model model,@RequestParam("name") String name) throws IOException {
        System.out.println(name);
        Area a = areaService.getByCity_nameOrPinyin(name);
        if (a==null){
            return "请输入完整的地区名称或者字母";
        }
//        String url="https://vacations.ctrip.com/list/scenichotel/sc.html/?";
//        url= new StringBuilder().append(url).append("sv=").append(a.getCity_name()).append("&st=").append(a.getCity_name()).append("&p=3").toString();
//        return Jsoup.connect(url).get().toString();

        CrawlHotelXC crawlHotelXC=new CrawlHotelXC(a.getUrl());
        crawlHotelXC.crawl(new CrawlHotelXCPipeline(hotelService),3);

//        String url="https://hotel.qunar.com/city/"+a.getPinyin();
//        if (name.equals("北京")||name.equals("上海")||name.equals("重庆")||name.equals("天津")
//                ||name.equals("beijing")||name.equals("shanghai")||name.equals("chongqing")||name.equals("tianjin")){
//            url= new StringBuilder().append(url).append("_city").toString();
//        }
//        System.out.println(url);
        return "sds";
    }

//    @RequestMapping(value = "/update")
//    public String updateAll(){
//        areaService.updateAll("携程");
//        return "hello";
//    }

    @RequestMapping(value = "/index.html")
    public String index(){
        return "index";
    }

}
