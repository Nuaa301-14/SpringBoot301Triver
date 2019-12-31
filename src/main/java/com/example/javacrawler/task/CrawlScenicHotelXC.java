package com.example.javacrawler.task;

import com.example.javacrawler.entity.Hotel;
import com.example.javacrawler.entity.Spot;
import com.example.javacrawler.entity.SpotAndHotel;
import com.example.javacrawler.service.HotelService;
import com.example.javacrawler.service.SpotService;
import com.example.javacrawler.task.pipeline.CrawlHotelXCPipeline;
import com.example.javacrawler.task.pipeline.CrawlScenicHotelXCPipeline;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CrawlScenicHotelXC implements PageProcessor {
    private int max;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private SpotService spotService;
//
//    public static CrawlScenicHotelXC scenicHotelXC;
//
//    @PostConstruct
//    public void init(){
//        scenicHotelXC=this;
//        scenicHotelXC.hotelService=this.hotelService;
//    }

    @Override
    public void process(Page page) {
        //TODO
        List<Selectable> nodes = page.getHtml().css("div.main_col div.list_product_box").nodes();
        List<SpotAndHotel> spotAndHotelList = new ArrayList<>();
        for (Selectable selectable :
                nodes) {
            SpotAndHotel spotAndHotel = new SpotAndHotel();
            String id = Jsoup.parse(selectable.toString()).select("div.list_product_box").attr("data-track-product-id");
            Element select = Jsoup.parse(selectable.toString()).select("div.list_product_right").get(0);
            String name = select.select("p.list_product_title span").text();

            Element element = select.select("div.list_content_right").get(0);
            String text = element.select("div.list_change_right div.list_change_one").text();
            float score = 0;
            String recommend = "";
            if (text.equals("")) {
                //只有价格
                score = Float.parseFloat(element.select("div.list_change_right div.list_change_two p.list_change_grade strong").text());
            } else {
                // 有推荐语
                score = Float.parseFloat(element.select("div.list_change_right div.list_change_one p.list_change_grade strong").text());
                recommend = element.select("div.list_change_right div.list_change_two").text();
            }
            float price = Float.parseFloat(element.select("div.list_sr_price_box span strong").text());

            Element element1 = select.select("div.list_content_left").get(0);
            String hotels = element1.select("div.scenic_lable_box span").text();
            String[] split = hotels.split("：");
            String hotelName=split[1];
            String hotelsId="";
            //TODO
            Hotel hotel = hotelService.getHotelByNameAndSource(hotelName, "携程");
//            Hotel hotel=null;
            if (hotel==null){
                // 爬取酒店信息
                crawHotel(hotelName);
                hotel=hotelService.getHotelByNameAndSource(hotelName,"携程");
                if (hotel!=null){
                    hotelsId+=hotel.getHotelId()+" ";
                }
            }
            String spots = element1.select("div.scenic_recommend_text").text();
            String[] split1 = spots.split("：");
            String[] spotss = split1[1].split("，");
            String spotsId= "";
            for (int i=0;i<spotss.length;i++){
                Spot spot = spotService.getSpotByNameAndSource(spotss[i].trim(), "携程");
                if (spot==null){
                    crawSpot(spotss[i].trim());
                    spot = spotService.getSpotByNameAndSource(spotss[i].trim(), "携程");
                }
                if (spot!=null){
                    spotsId+=(spot.getSpotId()+" ");
                }
            }
            spotAndHotel.setProductId("XC" + id);
            spotAndHotel.setProductName(name);
            spotAndHotel.setSource("携程");
            spotAndHotel.setScore(score);
            spotAndHotel.setPrice(price);
            spotAndHotel.setRecommend(recommend);
            spotAndHotel.setSpotsId(spotsId);
            spotAndHotel.setHotelsId(hotelsId);
            System.out.println(spotAndHotel.toString());
            spotAndHotelList.add(spotAndHotel);
        }
        page.putField("spotAndHotelList",spotAndHotelList);
    }

    private void crawHotel(String hotelName){
        new CrawlHotelXC("https://hotels.ctrip.com/hotel/beijing1/k1" + hotelName+"#ctm_ref=hod_hp_sb_lst")
                .crawlSpeHotel(hotelName);
    }

    private void crawSpot(String spotName){
        String url="https://piao.ctrip.com/dest/u-"+spotName+"/s-tickets/";
        new CrawlSpotXC().crawlSpeSpot(url,spotName,new CrawlHotelXCPipeline(hotelService));
    }

    @Override
    public Site getSite() {
        return site;
}

    private Site site = Site.me()
            .setCharset("utf8")
            .setTimeOut(10000)
            .setRetrySleepTime(3000)
            .setRetryTimes(3)
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.70");

    public void craw(String url, int pageNum, CrawlScenicHotelXCPipeline crawlScenicHotelXCPipeline) {
        max = pageNum;
        Spider.create(new CrawlScenicHotelXC()).addUrl(url)
                .setDownloader(new SeleniumDownloader("C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe").setSleepTime(2000))
                .thread(1)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .addPipeline(crawlScenicHotelXCPipeline)
                .run();
    }

    public static void main(String[] args) {
        System.setProperty("selenuim_config", "C:\\Users\\Administrator\\IdeaProjects\\javacrawler\\src\\main\\resources\\config.ini");

        Spider.create(new CrawlScenicHotelXC()).addUrl("https://vacations.ctrip.com/list/scenichotel/d-shanghai-2.html?st=%E4%B8%8A%E6%B5%B7&sv=%E4%B8%8A%E6%B5%B7")
                .setDownloader(new SeleniumDownloader("C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe").setSleepTime(2000))
                .thread(1)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
//                .addPipeline(crawlScenicHotelXCPipeline)
                .run();
    }
}
