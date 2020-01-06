package com.example.javacrawler.task;

import com.example.javacrawler.entity.InternationalArea;
import com.example.javacrawler.task.pipeline.InternationalAreaPipeline;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;


/**
 * 爬取携程 城市名称 分类 酒店数据
 */

@Component
public class CrawlInternationalArea implements PageProcessor {

    @Override
    public void process(Page page) {

        Selectable xpath = page.getHtml().xpath("//div[@class='filter_detail']");
        List<Selectable> div = xpath.css("div").nodes();
        List<InternationalArea> areaList = new ArrayList<>();
        for (int i = 1; i < div.size(); i++) {
            String head = Jsoup.parse(div.get(i).css("h3").toString()).text();
            List<Selectable> nodes = div.get(i).css("ul.nation_list li").nodes();
            for (Selectable selectable :
                    nodes) {
                InternationalArea internationalArea = new InternationalArea();

                internationalArea.setHead(head);

                String country = Jsoup.parse(selectable.toString()).select("strong").text();

                internationalArea.setCountry(country);

                String city_name = Jsoup.parse(selectable.toString()).select("a").get(1).text();
                String url = Jsoup.parse(selectable.toString()).select("a").get(1).attr("href");
                url = new StringBuilder().append("https:").append(url).toString();

                internationalArea.setCity_name(city_name);
                internationalArea.setUrl(url);

                String city_id = StringUtils.getDigits(url);
                url=url.replace(city_id, "");
                String english_name = url.substring(url.lastIndexOf("/")+1, url.length());

                internationalArea.setEnglish_name(english_name);
                internationalArea.setCity_id(Integer.parseInt(city_id));

                areaList.add(internationalArea);
            }
        }
        page.putField("areaList",areaList);
        System.out.println(areaList.size());
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


//    @Scheduled(initialDelay = 1000, fixedDelay = 1000 * 60 * 10)
    public void crawlArea(InternationalAreaPipeline internationalAreaPipeline) {
        Spider.create(new CrawlInternationalArea())
                .addUrl("https://hotels.ctrip.com/international/landmarks/")
//                .setDownloader(new SeleniumDownloader("C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe").setSleepTime(1000))
                .thread(1)
//                .runAsync();
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
////                .thread(10)
                .addPipeline(internationalAreaPipeline)
                .run();
    }
}
