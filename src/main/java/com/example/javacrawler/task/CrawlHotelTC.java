package com.example.javacrawler.task;

import com.example.javacrawler.entity.Hotel;
import com.example.javacrawler.task.pipeline.CrawlHotelXCPipeline;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

public class CrawlHotelTC implements PageProcessor {

    /**
     * 默认为两个
     */
    private int maxNumber=2;

    private String hotelName=null;

    @Override
    public void process(Page page) {
        List<Selectable> selectableList = page.getHtml().css("div#hotel-list ol.list li.list-li").nodes();
        if (selectableList.size() == 0) {
            //为空 ， 为详情页
            this.saveHotelDetailInfo(page);
        } else {
            if (hotelName!=null&& !hotelName.equals("")){
                // 索引单个酒店

            }else{
                String area="";
                area=Jsoup.parse(page.getHtml().css("div#searchBox dl.destination dd input").toString()).getElementsByTag("input").attr("value");
                List<Hotel> hotelList = this.saveHotelInfo(page, selectableList, area);
                page.putField("hotelList", hotelList);
            }
        }
    }

    private void saveHotelDetailInfo(Page page) {

    }

    private List<Hotel> saveHotelInfo(Page page, List<Selectable> selectableList, String area){
        List<Hotel> hotelList=new ArrayList<>();
        for (Selectable selectable :
                selectableList) {
            Hotel hotel = new Hotel();
            hotel.setSource("同程");
            hotel.setArea(area);
            hotel.setHotelCountry("china");
            hotel.setRecommend("无");

            String html = selectable.toString();
            Element element = Jsoup.parse(html).select("div.clearfix div.info h3 p a").get(0);
            String href = element.attr("href");
            String hotelId = StringUtils.getDigits(href);
            hotel.setHotelId("TC"+hotelId);
            String hotelName = element.select("span.title").text().trim();
            hotel.setHotelName(hotelName);

            Element p = Jsoup.parse(html).select("div.clearfix div.info p.detail-info").get(0);
            String str1="";
            if (p.select("a")!=null){
                str1 = p.select("a i").text();
                str1="【"+str1+"】";
            }
            String str2 = p.select("i.add").text();
            String hotelLocation=str1+str2;
            hotel.setHotelLocation(hotelLocation);

            float comprehensive = 0;
            int commentNumber = 0;
            int price = 0;

            Element div = Jsoup.parse(html).select("div.clearfix div.comment a").get(0);
            if (div==null){
                continue;
            }
            if (div.select("dl")!=null){
                Element dl=div.select("dl").get(0);
                comprehensive= Float.parseFloat(dl.select("dt.bg span.recent-grade").text().trim());
                commentNumber= Integer.parseInt(dl.select("dd.bg span").text().trim());
            }else {
                continue;
            }
            hotel.setComprehensive(comprehensive);
            hotel.setCommentNumber(commentNumber);

            Element a = Jsoup.parse(html).select("div.clearfix div.price-info a").get(0);
            String tex = a.select("p.my-price").text();
            String digits = StringUtils.getDigits(tex);
            if (digits.equals("")){
                continue;
            }else {
                price= Integer.parseInt(digits);
            }
            hotel.setPrice(price);
            String url="";
            url="https://www.ly.com"+href;
            hotel.setTargetUrl(url);
            hotelList.add(hotel);
        }
        return hotelList;
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

    public boolean crawl(String url, CrawlHotelXCPipeline crawlHotelXCPipeline, int pageNum, String hotelName){
        maxNumber=pageNum;
        this.hotelName=hotelName;
        Spider.create(this)
                .addUrl(url)
                .setDownloader(new SeleniumDownloader("C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe").setSleepTime(1000))
                .thread(1)
                .addPipeline(crawlHotelXCPipeline)
                .run();
        return true;
    }

}
