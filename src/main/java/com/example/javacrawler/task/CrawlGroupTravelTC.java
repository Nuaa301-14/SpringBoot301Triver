package com.example.javacrawler.task;

import com.example.javacrawler.entity.GroupTravel;
import com.example.javacrawler.entity.GroupTravelPrice;
import com.example.javacrawler.task.pipeline.CrawlGroupTravelTCPipeline;
import com.example.javacrawler.task.pipeline.CrawlGroupTravelXCPipeline;
import com.example.javacrawler.util.JsonCope;
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

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CrawlGroupTravelTC implements PageProcessor {
    private int max;

    private String name;

    private String area;

    @Override
    public void process(Page page) {
        //TODO
        List<GroupTravel> groupTravelList = new ArrayList<>();
        List<GroupTravelPrice> groupTravelPriceList = new ArrayList<>();

        List<Selectable> nodes = page.getHtml().css("div.route-content div.bd ul li").nodes();
        if (nodes.size() == 0) {
            List<Selectable> nodes1 = page.getHtml().css("div.mCal div.calendar-panel div.calendar-container table").nodes();
            if (nodes1.size() > 0) {
                Selectable selectable = nodes1.get(0);

                List<Selectable> tds = selectable.css("tbody tr td[data-date]").nodes();
                String productId = StringUtils.getDigits(Jsoup.parse(page.getHtml().toString()).select("div.float-left").text());
                for (int i = 0; i < tds.size(); i++) {
                    String string = tds.get(i).toString();
                    int i1 = string.indexOf("data-date");
                    String time = string.substring(i1 + 11, i1 + 21);
//                    String time=tds.get(i).css("td[data-date]","text").toString();
//                    String time = Jsoup.parse(string).getElementsByTag("td").attr("data-date");
                    Element select = Jsoup.parse(string).select("div.info").get(0);
                    String priceStr = select.select("span.dataprice").text();
                    int price = 0;
                    if (!priceStr.equals("")) {
                        String s = StringUtils.getDigits(priceStr);
                        if (!s.equals("")) {
                            price = Integer.parseInt(StringUtils.getDigits(priceStr));
                        }
                    } else {
                        continue;
                    }
                    Date date = strToDate(time);
                    GroupTravelPrice groupTravelPrice = new GroupTravelPrice();
                    groupTravelPrice.setPrice(price);
                    groupTravelPrice.setTime(date);
                    groupTravelPrice.setGroupId("TC" + productId);
                    groupTravelPriceList.add(groupTravelPrice);
                }
            }
        } else {
            groupTravelList = saveGroupTrvInfo(page, nodes);
            String url = page.getUrl().toString();
            int current = Integer.parseInt(StringUtils.getDigits(url.substring(url.indexOf("&start="), url.length())));
            if (current < max) {
                current++;
                url = url.substring(0, url.indexOf("&start=")) + "&start=" + current;
                page.addTargetRequest(url);
                System.out.println("添加 ： " + url);
            }

        }
        page.putField("groupTravelList", groupTravelList);
        page.putField("groupTravelPriceList", groupTravelPriceList);
    }


    public Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    private List<GroupTravel> saveGroupTrvInfo(Page page, List<Selectable> nodes) {
        List<GroupTravel> groupTravelList = new ArrayList<>();
        for (Selectable selectable :
                nodes) {
            GroupTravel groupTravel = new GroupTravel();
            String productId = Jsoup.parse(selectable.toString()).getElementsByTag("li").attr("data-lineid");

            groupTravel.setSource("同程");
            groupTravel.setSupply("同程专线");
            groupTravel.setGroupId("TC" + productId);

            Selectable a = selectable.css("a");
            String departure = Jsoup.parse(a.toString()).select("div.pro-img span.srcCity").text();
            String url = Jsoup.parse(a.toString()).getElementsByTag("a").attr("href");
            groupTravel.setGroupUrl("https:" + url);

            groupTravel.setDeparture(departure);
            String groupName = Jsoup.parse(a.css("div.pro-content h3.main-title").toString()).getElementsByTag("em").attr("title");
            groupTravel.setGroupName(groupName);
            Selectable div = a.css("div.details");
            int price = Integer.parseInt(Jsoup.parse(div.css("div.dr").toString()).select("p span strong").text());

            groupTravel.setGroupPrice(price);

            float GroupScore = 0;
            int CommentNumber = 0;
            int SoldNumber = 0;

            Elements divs = Jsoup.parse(div.css("div.dm").toString()).select("div");
            if (divs.size() > 0) {
                if (divs.size() == 2) {
                    Element element = divs.get(0);
                    if (element.select("p.trav-num").size() > 0) {
                        SoldNumber = Integer.parseInt(StringUtils.getDigits(element.select("p.trav-num").get(0).text()));
                    }
                } else if (divs.size() == 3) {
                    String comment = StringUtils.getDigits(Jsoup.parse(div.css("div.dm").toString()).select("div.comment-satNum").text());
                    GroupScore = (Float.parseFloat(comment) / 100) * 5;
                    Element element = Jsoup.parse(div.css("div.dm").toString()).select("div.trav-person").get(0);
                    SoldNumber = Integer.parseInt(StringUtils.getDigits(element.select("p.person-num").text()));
                    CommentNumber = Integer.parseInt(StringUtils.getDigits(element.select("p.person-comment").text()));
                }
            }

            groupTravel.setGroupScore(GroupScore);
            groupTravel.setSoldNumber(SoldNumber);
            groupTravel.setCommentNumber(CommentNumber);
            groupTravel.setDestination(area);
            groupTravel.setTime(new Date());
            groupTravelList.add(groupTravel);
            page.addTargetRequest(groupTravel.getGroupUrl());
            System.out.println(groupTravel.toString());

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

    public void craw(String url, int pageNum, CrawlGroupTravelTCPipeline crawlGroupTravelTCPipeline, String Name, String area) {
        max = pageNum;
        this.name = Name;
        this.area = area;
        Spider.create(this).addUrl(url)
                .setDownloader(new SeleniumDownloader("C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe").setSleepTime(2000))
                .thread(6)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .addPipeline(crawlGroupTravelTCPipeline)
                .run();
    }
}
