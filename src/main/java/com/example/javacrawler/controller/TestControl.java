package com.example.javacrawler.controller;

import com.example.javacrawler.entity.Area;
import com.example.javacrawler.entity.BookInfo;
import com.example.javacrawler.service.AreaService;
import com.example.javacrawler.service.HotelService;
import com.example.javacrawler.service.InternationalAreaService;
import com.example.javacrawler.task.CrawlHotelXC;
import com.example.javacrawler.task.CrawlInternationalArea;
import com.example.javacrawler.task.pipeline.CrawlHotelXCPipeline;
import com.example.javacrawler.task.pipeline.InternationalAreaPipeline;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class TestControl {

    @Autowired
    private InternationalAreaService internationalAreaService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private AreaService areaService;

    /**
     * @RequestMapping("/login.html") 就可以直接访问login.html了
     * Model ： 将传入下一个页面
     */
    @RequestMapping("/login.html")
    public String loginHtml(Model model) {

        model.addAttribute("result", "登陆页面");
        return "login";
    }

    @RequestMapping(value = "login.html", method = RequestMethod.POST)
    public String login(Model model,
                        @RequestParam("userName") String name,
                        @RequestParam("password") String psw) {

        /**
         * 使用shiro编写认证操作
         *
         */
        // 1 获取Subject对象
        Subject subject= SecurityUtils.getSubject();

        // 2 封装用户数据
        UsernamePasswordToken token=new UsernamePasswordToken(name,psw);
        try {
            // 3执行登陆方法
            subject.login(token);
            // 登陆成功
            List<BookInfo> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                BookInfo bookInfo = new BookInfo();
                bookInfo.title = "小王子";
                bookInfo.author = "老外";
                bookInfo.data = "2019/09/12";
                list.add(bookInfo);
            }
            model.addAttribute("list", list);
            model.addAttribute("name", name);
            return "list";
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户名不存在");
            return "login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "login";
        }
    }

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
        String s = a.toString();
        System.out.println(s);
//        String url="https://vacations.ctrip.com/list/scenichotel/sc.html/?";
//        url= new StringBuilder().append(url).append("sv=").append(a.getCity_name()).append("&st=").append(a.getCity_name()).append("&p=3").toString();
//        return Jsoup.connect(url).get().toString();

        CrawlHotelXC crawlHotelXC=new CrawlHotelXC(a.getUrl());
        crawlHotelXC.crawl(new CrawlHotelXCPipeline(hotelService));

//        String url="https://hotel.qunar.com/city/"+a.getPinyin();
//        if (name.equals("北京")||name.equals("上海")||name.equals("重庆")||name.equals("天津")
//                ||name.equals("beijing")||name.equals("shanghai")||name.equals("chongqing")||name.equals("tianjin")){
//            url= new StringBuilder().append(url).append("_city").toString();
//        }
//        System.out.println(url);
        return "sds";
    }

    @RequestMapping(value = "/login")
    public String loginin(){
        return "hello";
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
