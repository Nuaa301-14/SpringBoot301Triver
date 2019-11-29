package com.example.javacrawler.task;


import com.example.javacrawler.entity.Hotel;
import com.example.javacrawler.task.pipeline.CrawlHotelXCPipeline;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

public class CrawlHotelXC implements PageProcessor {

    private String url;

    private int maxNumber = 3;

    public CrawlHotelXC(String url) {
        this.url = url;
    }

    public CrawlHotelXC() {
    }

    @Override
    public void process(Page page) {
        String area = Jsoup.parse(page.getHtml().css("div#searchForm input#txtCity").toString()).getElementsByTag("input").attr("value");
        List<Selectable> selectableList = page.getHtml().css("div#hotel_list div.hotel_new_list").nodes();
        if (selectableList.size() == 0) {
            //为空 ， 为详情页

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
            page.putField("hotelList", this.saveHotelInfo(page, selectableList, area));
        }
    }


    /**
     * 分析酒店信息
     *
     * @param page
     * @return
     */
    private List<Hotel> saveHotelInfo(Page page, List<Selectable> selectableList, String area) {
        List<Hotel> hotelList = new ArrayList<>();
        for (Selectable selectable :
                selectableList) {
            Hotel hotel = new Hotel();
            String html = selectable.toString();
            String hotelId = Jsoup.parse(html).select("div.hotel_new_list")
                    .first().attr("id");
            hotel.setHotelId(hotelId);


            String text1 = Jsoup.parse(html).select("li.hotel_item_name h2.hotel_name")
                    .first().getElementsByTag("a").text();
            String hotelName="";
            if (text1.contains("新店")||text1.contains("精选")){
                hotelName=text1.substring(text1.indexOf(" ")+1);
            }else {
                hotelName=text1;
            }
            hotel.setHotelName(this.copeName(hotelName));


            hotel.setHotelCountry("China");


            String[] s = Jsoup.parse(html).select("p.hotel_item_htladdress").
                    first().text().split(" ");
            StringBuilder hotelLocation = new StringBuilder();
            for (int i = 0; i < s.length - 1; i++) {
                hotelLocation.append(s[i]);
            }
            hotel.setHotelLocation(String.valueOf(hotelLocation));

            Elements select = Jsoup.parse(html).select("div.hotelitem_judge_box a span.hotel_value");
            if (select.size()==0){
                hotel.setComprehensive(0);
            }else {
                String text = select.first().text();
                float comprehensive = Float.parseFloat(text);
                hotel.setComprehensive(comprehensive);
            }

            Elements element=Jsoup.parse(html).select("div.hotelitem_judge_box a span.hotel_judgement");
            if (element.size()==0){
                hotel.setCommentNumber(0);
            }else {
                String comment =element.first().getElementsByAttribute("style")
                        .text();
                hotel.setCommentNumber(Integer.parseInt(comment));
            }

            //爬取用户推荐度
            Elements elements = Jsoup.parse(html).select("div.hotelitem_judge_box a span.total_judgement_score");
            if (elements.size()==0){
                hotel.setRecommend("0%");
            }else {
                String recommend = elements.first().getElementsByAttribute("style").text();
                hotel.setRecommend(recommend);
            }


            //获取酒店的起价
            String priceStr = Jsoup.parse(html).select("li.hotel_price_icon div.hotel_price span").text();
            int price = Integer.parseInt(priceStr);
            hotel.setPrice(price);
            System.out.println("price:" + price);
            //获得酒店的详情url
            String url = "";
            url = Jsoup.parse(html).select("li.hotel_item_name a").attr("href");
            if (!url.contains("https://")) {
                url = new StringBuilder().append("https://hotels.ctrip.com").append(url).toString();
            }
            hotel.setTargetUrl(url);
            System.out.println(hotel.toString());
            hotel.setSource("携程");
            hotel.setArea(area);
            hotelList.add(hotel);
        }
        return hotelList;
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

    @Override
    public Site getSite() {
        return site;
    }

    private Site site = Site.me()
            .setCharset("utf8")
            .setTimeOut(10000)
            .setRetrySleepTime(3000)
            .setRetryTimes(3);

    public void crawl(CrawlHotelXCPipeline crawlHotelXCPipeline,int pageNum) {
        Spider.create(new CrawlHotelXC())
                .addUrl(url)
                .setDownloader(new SeleniumDownloader("C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe").setSleepTime(2000))
                .thread(1)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .addPipeline(crawlHotelXCPipeline)
                .run();
    }
}
