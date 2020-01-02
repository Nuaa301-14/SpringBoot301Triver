package com.example.javacrawler.task;

import com.example.javacrawler.entity.Hotel;
import com.example.javacrawler.entity.Spot;
import com.example.javacrawler.task.pipeline.CrawlHotelXCPipeline;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

public class CrawlHotelELong implements PageProcessor {

    /**
     * 默认为两个
     */
    private int maxNumber = 2;

    private String hotelName = null;

    @Override
    public void process(Page page) {
        List<Selectable> selectableList = page.getHtml().css("div#hotelContainer div.h_list div.h_item").nodes();
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
            if (hotelName != null && !hotelName.equals("")) {
                // 索引单个酒店

            } else {
                // 获取下一页的url
                String text = page.getHtml().css("div#pageContainer a.on", "text").toString();
                int current = Integer.parseInt(text);
                List<Selectable> nodes = page.getHtml().css("div#pageContainer a[method]").nodes();
                for (Selectable selectable :
                        nodes) {
                    //获取url地址
                    String n = Jsoup.parse(selectable.toString()).getElementsByTag("a").attr("data-index");
                    int num = Integer.parseInt(n);
                    if (num > current && num <= maxNumber) {
                        String url = page.getUrl().toString();
                        if (url.contains("pageIndex")) {
                            url = url.split("pageIndex")[0];
                            url = url + "pageIndex=" + num;
                        } else {
                            url = url + "?pageIndex=" + num;
                        }
                        page.addTargetRequest(url);
                        System.out.println("添加 ： " + url);
                    }
                }
                String s = page.getHtml().css("div#m_searchBox div.search_item label").toString();
                String area = Jsoup.parse(s).getElementsByTag("input").attr("value");
                page.putField("hotelList", this.saveHotelInfo(page, selectableList, area));
            }

        }
    }

    private List<Hotel> saveHotelInfo(Page page, List<Selectable> selectableList, String area) {
        List<Hotel> hotelList = new ArrayList<>();
        for (Selectable selectable :
                selectableList) {
            Hotel hotel = new Hotel();
            hotel.setSource("艺龙");
            hotel.setArea(area);
            hotel.setHotelCountry("china");
            hotel.setRecommend("无");


            String html = selectable.toString();
            String hotelId = Jsoup.parse(html).select("div.h_info div.h_info_pic a img").get(0).attr("data-hotelid");
            hotel.setHotelId("EL" + hotelId);
            String hotelName = Jsoup.parse(html).select("div.h_info div.h_info_base p.h_info_b1 a").get(0).attr("title");
            hotel.setHotelName(hotelName);
            String text = Jsoup.parse(html).select("div.h_info div.h_info_base p.h_info_b2").get(0).text();
            String[] s = text.split(" ");
            String hotelLocation = "";
            if (s.length >= 2) {
                hotelLocation = s[0] + s[1];
            }
            hotel.setHotelLocation(hotelLocation);
            float comprehensive = 0;
            int commentNumber = 0;
            if (Jsoup.parse(html).select("div.h_info div.h_info_comt a") != null) {
                Elements select = Jsoup.parse(html).select("div.h_info div.h_info_comt a").get(0).select("span");
                for (int i = 0; i < select.size(); i++) {
                    if (i == 0) {
                        String i1 = select.get(i).select("i").get(0).text().trim();
                        i1 = i1.substring(0, i1.length() - 1);
                        comprehensive = Float.parseFloat(i1);
                    } else if (i == 1) {
                        commentNumber = Integer.parseInt(select.get(i).select("b").text());
                    }
                }
            }
            if (commentNumber == 0 || comprehensive == 0) {
                continue;
            }
            hotel.setComprehensive(comprehensive);
            hotel.setCommentNumber(commentNumber);
            int price = 0;
            Elements select1 = Jsoup.parse(html).select("div.h_info div.h_info_pri div.pricesquare a");
            if (select1 != null) {
                price = Integer.parseInt(StringUtils.getDigits(select1.select("span").text()));
            }
            if (price == 0) {
                continue;
            }
            hotel.setPrice(price);
            String url = "";
            url = "http://hotel.elong.com/" + hotelId + "/?issugtrace=2";
            hotel.setTargetUrl(url);
            hotelList.add(hotel);
        }
        return hotelList;
    }

    private void saveHotelDetailInfo(Page page) {

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

    public boolean crawl(String url, CrawlHotelXCPipeline crawlHotelXCPipeline, int pageNum, String hotelName) {
        maxNumber = pageNum;
        this.hotelName = hotelName;
        Spider.create(this)
                .addUrl(url)
                .setDownloader(new SeleniumDownloader("C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe").setSleepTime(1000))
                .thread(1)
                .addPipeline(crawlHotelXCPipeline)
                .run();
        return true;
    }
}
