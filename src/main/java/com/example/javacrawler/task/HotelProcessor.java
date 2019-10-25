package com.example.javacrawler.task;

import com.example.javacrawler.entity.Hotel;
import com.example.javacrawler.service.HotelService;
import com.example.javacrawler.service.serviceImpl.HotelServiceImpl;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Selectable;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class HotelProcessor implements PageProcessor {

    @Autowired
    private HotelService hotelService;

    private int maxNumber = 5;
    private String url = "https://hotels.ctrip.com/hotel/shanghai2";

    private static HotelProcessor hotelProcessor;

    @PostConstruct
    public void init() {
        hotelProcessor = this;
        hotelProcessor.hotelService = this.hotelService;
    }

    @Override
    public void process(Page page) {
//        System.out.println(page.getUrl());
        List<Selectable> selectableList = page.getHtml().css("div#hotel_list div.hotel_new_list").nodes();
        if (selectableList.size() == 0) {
            //为空 ， 为详情页
            this.saveHotelDetailInfo(page);
        } else {
            //不为空 解析详情页的url到任务队列中
//            for (Selectable selectable :
//                    selectableList) {
//                //获取url地址
//                String s = selectable.links().toString();
//                //添加详情url 到任务队列
//                page.addTargetRequest(s);
//            }
            // 获取下一页的url
            String text = page.getHtml().css("div.c_page_list a.current", "text").toString();
            int current = Integer.parseInt(text);
            List<Selectable> nodes = page.getHtml().css("div.c_page_list a[data-dopost]").nodes();
            for (Selectable selectable :
                    nodes) {
                //获取url地址
                int num = Integer.parseInt(selectable.css("a[data-value]", "text").toString());
                if (num > current && num <= maxNumber) {
                    String s = selectable.links().toString();
                    page.addTargetRequest(selectable.links().toString());
                    System.out.println("添加 ： " + selectable.links().toString());
                }
            }
            this.saveHotelInfo(page, selectableList);
        }
    }

    /**
     * 分析酒店信息
     *
     * @param page
     */
    private void saveHotelInfo(Page page, List<Selectable> selectableList) {
        for (Selectable selectable :
                selectableList) {
            Hotel hotel = new Hotel();
            String html = selectable.toString();
//            hotel.setHotelId("123456");
            String hotelId = Jsoup.parse(html).select("div.hotel_new_list")
                    .first().attr("id");
            hotel.setHotelId(hotelId);


            String[] split = Jsoup.parse(html).select("li.hotel_item_name h2.hotel_name")
                    .first().getElementsByTag("a").text()
                    .split(" ");
            String hotelName = split[split.length - 1];
            hotel.setHotelName(this.copeName(hotelName));


            hotel.setHotelCountry("China");


            String[] s = Jsoup.parse(html).select("p.hotel_item_htladdress").
                    first().text().split(" ");
            StringBuilder hotelLocation = new StringBuilder();
            for (int i = 0; i < s.length - 1; i++) {
                hotelLocation.append(s[i]);
            }
            hotel.setHotelLocation(String.valueOf(hotelLocation));


            String text = Jsoup.parse(html).select("div.hotelitem_judge_box a span.hotel_value")
                    .first().text();
            float comprehensive = Float.parseFloat(text);
            hotel.setComprehensive(comprehensive);

            String comment = Jsoup.parse(html).select("div.hotelitem_judge_box a span.hotel_judgement")
                    .first().getElementsByAttribute("style")
                    .text();
            System.out.println(comment);
            hotel.setCommentNumber(Integer.parseInt(comment));


            //爬取用户推荐度
            String recommend = Jsoup.parse(html).select("div.hotelitem_judge_box a span.total_judgement_score")
                    .first().getElementsByAttribute("style").text();
//            System.out.println(recommend);

            //获取酒店的起价
            String priceStr = Jsoup.parse(html).select("li.hotel_price_icon div.hotel_price span").text();
            int price = Integer.parseInt(priceStr);

            //获得酒店的详情url
            String url = "";
            url = Jsoup.parse(html).select("li.hotel_item_name a").attr("href");
            if (!url.contains("https://")) {
                url = new StringBuilder().append("https://hotels.ctrip.com").append(url).toString();
            }
//            System.out.println(url);


            System.out.println(hotel.toString());

//            if (hotelProcessor.hotelService.isExist(hotel)) {
//                // 如果存在进行更新
//                hotelProcessor.hotelService.updateHotel(hotel);
//            } else {
//                // 不存在就添加到数据库中
//                hotelProcessor.hotelService.insertHotel(hotel);
//            }
        }
    }

    /**
     * 对酒店名称进行处理
     *
     * @param hotelName
     */
    private String copeName(String hotelName) {
        if (hotelName.length() < 2) {
            return hotelName;
        }
        char c0 = hotelName.charAt(0);
        char c1 = hotelName.charAt(1);
        int begin = 0;
        if (Character.isDigit(c0)) {
            begin += 1;
        }
        if (Character.isDigit(c1)) {
            begin += 1;
        }
        return hotelName.substring(begin, hotelName.length());
    }

    /**
     * 分解详细酒店信息
     *
     * @param page
     */
    private void saveHotelDetailInfo(Page page) {
        System.out.println("heere");
    }

    private Site site = Site.me()
            .setCharset("utf8")
            .setTimeOut(10000)
            .setRetrySleepTime(3000)
            .setRetryTimes(3);

    @Override
    public Site getSite() {
        return site;
    }

//    @Scheduled(initialDelay = 1000, fixedDelay = 10000)
    public void CrawlHotel() {
        System.setProperty("selenuim_config", "C:\\Users\\Administrator\\IdeaProjects\\javacrawler\\src\\main\\resources\\config.ini");
        Spider.create(new HotelProcessor())
                .addUrl(url)
                .setDownloader(new SeleniumDownloader("C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe").setSleepTime(1000))
                .thread(3)
//                .runAsync();
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
//                .thread(10)
                .run();
//        System.out.println("sdaf");

    }
}
