package com.example.javacrawler.task;

import com.example.javacrawler.entity.Spot;
import com.example.javacrawler.task.pipeline.CrawlSpotTCPipeline;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrawlSpotTC implements PageProcessor {

    private String url;

    private int maxNumber = 1;

    private String SpotName=null;

    @Override
    public void process(Page page) {
        //TODO
        List<Selectable> nodes = page.getHtml().css("div.list_l ").nodes();
        List<Spot> spotList=new ArrayList<>();
        if (nodes.size() == 0) {
            //为空 ， 为详情页
            this.saveSpotDetailInfo(page);
        } else {
            if (this.SpotName!=null){
                // 爬取单个景区信息
                spotList=saveSpotInfo(page,nodes);
            }else {
                String text = Jsoup.parse(page.getHtml().css("div.page_link span.on_page").toString()).text();
                int current= Integer.parseInt(text);
                List<Selectable> nodes1 = page.getHtml().css("div.page_link a.choose_page ").nodes();
                for (Selectable selectable :
                        nodes1) {
                    int num= Integer.parseInt(selectable.css("a.choose_page ","text").toString());
                    if (num > current && num <= maxNumber) {
                        String url = page.getUrl().toString();
                        if (url.contains("page")) {
                            url = url.split("page")[0];
                            url = url + "page=" + num;
                        } else {
                            url = url + "&page=" + num;
                        }
                        page.addTargetRequest(url);
                        System.out.println("添加 ： " + url);
                    }
                }
                spotList = saveSpotInfo(page, nodes);
            }
        }
        page.putField("spotList",spotList);

    }

    private List<Spot> saveSpotInfo(Page page, List<Selectable> nodes) {
        List<Spot> spotList=new ArrayList<>();
        for (Selectable selectable :
                nodes) {
            Spot spot = new Spot();
            Element element = Jsoup.parse(selectable.toString()).select("div.info_c dl").get(0);
            Element dt = element.select("dt").get(0);
            String SpotName = dt.select("a").get(0).text();
            String SpotUrl="https:"+dt.select("a").attr("href");
            Elements span_a = dt.select("span a");
            String SpotArea=span_a.get(span_a.size()-1).text();
            String SpotCountry="中国";
            String recommend="无";
            Elements dd = element.select("dd");
            int i=0;
            if (dd.size()==3) {
                String SpotGender = dd.get(i).select("span.s_level").text();
                i++;
            }
            String address = dd.get(i).select("p").text();
            String[] split = address.split("：");
            address=split[1];
            String introduce = dd.get(i + 1).select("p").text();
            float score=0;
            float price= Float.parseFloat(Jsoup.parse(selectable.toString()).select("div.detail_c div span b").text());


            Connection connect;
            connect = Jsoup.connect(SpotUrl);
            try {
                Document document = connect.get();
                Element div = document.select("div.info_r").get(0);
                String name = div.select("h3.s_name").text();
                SpotName=name.split(" ")[0];
            } catch (IOException e) {
                e.printStackTrace();
            }

            spot.setSpotName(SpotName);
            spot.setSpotLocation(address);
            spot.setSpotCountry(SpotCountry);
            spot.setRecommend(recommend);
            spot.setSpotScore(score);
            spot.setIntroduce(introduce);
            spot.setSpotPrice(price);
            spot.setSpotUrl(SpotUrl);
            spot.setSource("同程");
            spot.setSpotArea(SpotArea);
            spot.setSpotId("TC"+StringUtils.getDigits(SpotUrl));
            System.out.println(spot.toString());
            spotList.add(spot);
        }
        return spotList;
    }

    private void saveSpotDetailInfo(Page page) {

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


    public void craw(String url, int pageNum, CrawlSpotTCPipeline crawlSpotTCPipeline,String spotName) {
        this.maxNumber=pageNum;
        this.SpotName=spotName;
        Spider.create(this).addUrl(url)
                .setDownloader(new SeleniumDownloader("C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe").setSleepTime(2000))
                .thread(1)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .addPipeline(crawlSpotTCPipeline)
                .run();
    }

    public static void main(String[] args) {
        Spider.create(new CrawlSpotTC()).addUrl("https://so.ly.com/scenery?q=上海")
                .setDownloader(new SeleniumDownloader("C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe").setSleepTime(2000))
                .thread(1)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
//                .addPipeline(crawlHotelXCPipeline)
                .run();
    }
}
