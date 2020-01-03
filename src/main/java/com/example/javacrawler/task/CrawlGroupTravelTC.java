package com.example.javacrawler.task;

import com.example.javacrawler.entity.GroupTravel;
import com.example.javacrawler.entity.GroupTravelPrice;
import com.example.javacrawler.task.pipeline.CrawlGroupTravelTCPipeline;
import com.example.javacrawler.task.pipeline.CrawlGroupTravelXCPipeline;
import com.example.javacrawler.util.JsonCope;
import org.jsoup.Jsoup;
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

public class CrawlGroupTravelTC implements PageProcessor {
    private int max;

    private String name;

    @Override
    public void process(Page page) {
        //TODO
        List<GroupTravel> groupTravelList = new ArrayList<>();
        List<GroupTravelPrice> groupTravelPriceList = new ArrayList<>();

        List<Selectable> nodes = page.getHtml().css("div.route-content div.bd ul li").nodes();
        if (nodes.size()==0){

        }else {
            groupTravelList=saveGroupTrvInfo(page,nodes);
        }
    }

    private List<GroupTravel> saveGroupTrvInfo(Page page, List<Selectable> nodes) {
        List<GroupTravel> groupTravelList = new ArrayList<>();
        for (Selectable selectable :
                nodes) {
            GroupTravel groupTravel=new GroupTravel();
            String productId = Jsoup.parse(selectable.toString()).getElementsByTag("li").attr("date-lineid");

            groupTravel.setSource("同程");
            groupTravel.setGroupId("TC"+productId);

            Selectable a = selectable.css("a");
            String departure = Jsoup.parse(a.toString()).select("div.pro-img span.srcCity").text();

            groupTravel.setDeparture(departure);

        }
        return groupTravelList;
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

    public void craw(String url, int pageNum, CrawlGroupTravelTCPipeline crawlGroupTravelTCPipeline,String Name) {
        max=pageNum;
        this.name=Name;
        Spider.create(this).addUrl(url)
                .setDownloader(new SeleniumDownloader("C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe").setSleepTime(2000))
                .thread(1)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .addPipeline(crawlGroupTravelTCPipeline)
                .run();
    }
}
