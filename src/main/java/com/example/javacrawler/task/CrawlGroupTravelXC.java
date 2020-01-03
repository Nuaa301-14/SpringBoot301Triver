package com.example.javacrawler.task;

import com.example.javacrawler.entity.GroupTravel;
import com.example.javacrawler.entity.GroupTravelPrice;
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

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CrawlGroupTravelXC implements PageProcessor {
    private int max;
    private String GroupName = null;

    @Override
    public void process(Page page) {
        List<GroupTravel> groupTravelList = new ArrayList<>();
        List<GroupTravelPrice> groupTravelPriceList = new ArrayList<>();
        List<Selectable> nodes = page.getHtml().css("div.main_col div.list_product_box").nodes();
        if (nodes.size() == 0) {
            List<Selectable> nodes1 = page.getHtml().css("div.d_calendar_boxbg table.d_calendar_table").nodes();
            if (nodes1.size() > 0) {
                Selectable selectable = nodes1.get(0);
//                System.out.println(selectable.toString());

                Elements select1 = Jsoup.parse(page.getHtml().toString()).select("div.prd_num");
                String text=select1.get(select1.size()-1).text();
                String productId = StringUtils.getDigits(text);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                String year = calendar.get(Calendar.YEAR) + "";
                int month = calendar.get(Calendar.MONTH) + 1;


                Elements trs = Jsoup.parse(selectable.toString()).select("tbody tr");
                for (int i = 0; i < trs.size(); i++) {
                    Elements select = trs.get(i).select("td div");
                    for (int j = 0; j < select.size(); j++) {
                        String day = select.get(j).select("span.date").text();
                        String price = StringUtils.getDigits(select.get(j).select("span.calendar_price01").text());
                        String time = year + "-" + month + "-" + day;
                        Date date = strToDate(time);
                        GroupTravelPrice groupTravelPrice=new GroupTravelPrice();
                        groupTravelPrice.setPrice(Integer.parseInt(price));
                        groupTravelPrice.setTime(date);
                        groupTravelPrice.setGroupId("XC"+productId);

                        groupTravelPriceList.add(groupTravelPrice);
                    }
                }
            }
            System.out.println("here");
        } else {
            if (GroupName != null) {
                //爬取单个跟团游
            } else {
                groupTravelList = savaGroupTrvInfo(page, nodes);
                Document parse = Jsoup.parse(page.getHtml().css("div.list_paging_box").toString());
                Element span = parse.select("span.list_paging_text").get(0);
                int current = Integer.parseInt(StringUtils.getDigits(span.select("em").text().trim()));
                String[] split = span.text().trim().split("/");
                int total = Integer.parseInt(StringUtils.getDigits(split[split.length - 1]));
                if (current <= total && current < max) {
                    current += 1;
                    String url = page.getUrl().toString();
                    if (url.contains("?")) {
                        String[] split1 = StringUtils.split(url, "?");
                        url = split1[0] + "?p=" + current;
                    } else {
                        url += "?p=" + current;
                    }
                    page.addTargetRequest(url);
                }
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

    private List<GroupTravel> savaGroupTrvInfo(Page page, List<Selectable> nodes) {
        List<GroupTravel> groupTravelList = new ArrayList<>();
        String destination = Jsoup.parse(page.getHtml().toString()).select("div.search_wrap div.new_search_box input").get(0).getElementsByTag("input").attr("value");
        for (Selectable selectable :
                nodes) {
            GroupTravel groupTravel = new GroupTravel();
            String groupId = Jsoup.parse(selectable.toString()).getElementsByTag("div").attr("data-track-product-id");
            String departure = Jsoup.parse(selectable.css("div.list_product_left span.list_product_place").toString()).text();
            String GroupTrvName = Jsoup.parse(selectable.css("div.list_product_right p.list_product_title").toString()).select("span").text().trim();
            groupTravel.setDeparture(departure);
            groupTravel.setGroupName(GroupTrvName);

            String offer = Jsoup.parse(selectable.css("div.list_product_content div.list_content_left").toString()).select("p.list_product_retail").text();
            String supply = offer.split("：")[1];
            groupTravel.setSupply(supply);

            int price = -1;
            Element element = Jsoup.parse(selectable.css("div.list_product_content div.list_content_right").toString()).select("div.list_sr_price_box").get(0);
            if (element.select("span.list_sr_price strong") != null) {
                String priceStr = element.select("span.list_sr_price strong").text().trim();
                if (priceStr.contains("万")) {
                    float p = Float.parseFloat(priceStr.substring(2, priceStr.indexOf("万")));
                    price = (int) (p * 10000);
                } else {
                    price = Integer.parseInt(priceStr);
                }
            }
            groupTravel.setGroupPrice(price);

            float GroupScore = 0;
            int CommentNumber = 0;
            int SoldNumber = 0;
            Elements select = Jsoup.parse(selectable.css("div.list_product_content div.list_content_right").toString()).select("div.list_change_box");
            if (select != null && select.size() > 0) {
                Element div = select.get(0);
                Elements strong = div.select("div.list_change_left p strong");
                if (strong != null && strong.size() > 0) {
                    GroupScore = Float.parseFloat(strong.text().trim());
                }
                div = div.select("div.list_change_right").get(0);
                System.out.println(div.select("div.list_change_one").get(0).text());
                if (div.select("div.list_change_one").get(0).text().equals("")) {
                    if (!div.select("div.list_change_two").get(0).text().equals("")) {
                        SoldNumber = Integer.parseInt(StringUtils.getDigits(div.select("div.list_change_two").text()));
                    }
                } else {
                    SoldNumber = Integer.parseInt(StringUtils.getDigits(div.select("div.list_change_one").text().trim()));
                    CommentNumber = Integer.parseInt(StringUtils.getDigits(div.select("div.list_change_two a").text()));
                }
            }
            groupTravel.setGroupScore(GroupScore);
            groupTravel.setSoldNumber(SoldNumber);
            groupTravel.setCommentNumber(CommentNumber);
            String url = "https://vacations.ctrip.com/tour/detail/";
            groupTravel.setGroupId("XC" + groupId);
            url += "p" + groupId + "s12.html";
            groupTravel.setGroupUrl(url);
            groupTravel.setSource("携程");
            groupTravel.setDestination(destination);
            groupTravelList.add(groupTravel);
            page.addTargetRequest(url);
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

    public void craw(String url, int pageNum, CrawlGroupTravelXCPipeline crawlGroupTravelXCPipeline, String
            GroupName) {
        max = pageNum;
        this.GroupName = GroupName;
        Spider.create(this).addUrl(url)
                .setDownloader(new SeleniumDownloader("C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe").setSleepTime(2000))
                .thread(5)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .addPipeline(crawlGroupTravelXCPipeline)
                .run();
    }
}
