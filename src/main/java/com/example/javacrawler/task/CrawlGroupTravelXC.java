package com.example.javacrawler.task;

import com.example.javacrawler.entity.GroupTravel;
import com.example.javacrawler.task.pipeline.CrawlGroupTravelXCPipeline;
import org.apache.commons.lang3.StringUtils;
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
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

public class CrawlGroupTravelXC implements PageProcessor {
    private int max;
    private String GroupName = null;

    @Override
    public void process(Page page) {
        List<GroupTravel> groupTravelList = new ArrayList<>();
        List<Selectable> nodes = page.getHtml().css("div.main_col div.list_produce_box").nodes();
        if (nodes.size() == 0) {
            System.out.println("here");
        } else {
            String area = "";
            groupTravelList = savaGroupTrvInfo(page, nodes);
            Document parse = Jsoup.parse(page.getHtml().css("div.list_paging_box").toString());
            Element span = parse.select("span.list_paging_text").get(0);
            int current = Integer.parseInt(span.select("em").text().trim());
            String[] split = span.text().trim().split("/");
            int total = Integer.parseInt(split[split.length - 1]);
            if (current <= total && current < max) {
                current += 1;
                String url=page.getUrl().toString();
                if (url.contains("?")){
                    String[] split1 = StringUtils.split(url, "?");
                    url=split1[0]+"?p="+current;
                }else {
                    url+="?p="+current;
                }
                page.addTargetRequest(url);
            }
        }
        page.putField("groupTravelList",groupTravelList);

    }

    private List<GroupTravel> savaGroupTrvInfo(Page page, List<Selectable> nodes) {
        List<GroupTravel> groupTravelList = new ArrayList<>();
        for (Selectable selectable :
                nodes) {
            GroupTravel groupTravel = new GroupTravel();
            String groupId = Jsoup.parse(selectable.toString()).getElementsByTag("div").attr("data-track-product-id");
            String departure = Jsoup.parse(selectable.css("div.list_product_left span.list_product_place").toString()).text();
            String GroupTrvName = selectable.css("div.list_product_right p.list_product_title", "text").toString();
            groupTravel.setDeparture(departure);
            groupTravel.setGroupName(GroupTrvName);

            String offer = Jsoup.parse(selectable.css("div.list_product_content div.list_content_left").toString()).select("p.list_product_retail").text();
            String supply = offer.split("：")[1];
            groupTravel.setSupply(supply);

            int price = -1;
            Element element = Jsoup.parse(selectable.css("div.list_product_content div.list_content_right").toString()).select("div.list_sr_price_box").get(0);
            if (element.select("span.list_sr_price strong") != null) {
                price = Integer.parseInt(element.select("span.list_sr_price strong").text().trim());
            }

            float GroupScore = 0;
            int CommentNumber = 0;
            int SoldNumber = 0;
            Elements select = Jsoup.parse(selectable.css("div.list_product_content div.list_content_right").toString()).select("div.list_change_box");
            if (select != null) {
                Element div = select.get(0);
                GroupScore = Float.parseFloat(div.select("div.list_change_left p strong").text().trim());
                div = div.select("div.list_change_right").get(0);
                SoldNumber = Integer.parseInt(StringUtils.getDigits(div.select("div.list_change_one").text().trim()));
                CommentNumber = Integer.parseInt(StringUtils.getDigits(div.select("div.list_change_two a").text()));
            }
            groupTravel.setGroupScore(GroupScore);
            groupTravel.setSoldNumber(SoldNumber);
            groupTravel.setCommentNumber(CommentNumber);
            String url = "https://vacations.ctrip.com/tour/detail/";
            groupTravel.setGroupId("XC" + groupId);
            url += "p" + groupId + "s12.html";
            groupTravel.setGroupUrl(url);
            groupTravelList.add(groupTravel);
            page.addTargetRequest(url);
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

    public void craw(String url, int pageNum, CrawlGroupTravelXCPipeline crawlGroupTravelXCPipeline, String GroupName) {
        max = pageNum;
        this.GroupName = GroupName;
        Spider.create(this).addUrl(url)
                .setDownloader(new SeleniumDownloader("C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe").setSleepTime(2000))
                .thread(1)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .addPipeline(crawlGroupTravelXCPipeline)
                .run();
    }
}
