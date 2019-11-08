package com.example.javacrawler.task;

import com.example.javacrawler.entity.Area;
import com.example.javacrawler.task.pipeline.MybatisPipeline;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CrawlArea implements PageProcessor {

    @Override
    public void process(Page page) {

        Selectable xpath = page.getHtml().xpath("//dl[@class='pinyin_filter_detail']");
        List<Selectable> dt = xpath.css("dt").nodes();
        List<Selectable> dd = xpath.css("dd").nodes();

        if (dt.size() != dd.size()) {
            return;
        } else {
            List<Area> areaList = new ArrayList<>();
            for (int i = 0; i < dt.size(); i++) {
                //获得dd中的值
                String headPinyin = Jsoup.parse(dt.get(i).toString()).text();
                List<Selectable> a = dd.get(i).css("a").nodes();
                for (Selectable selectable :
                        a) {
                    Area area = new Area();

                    Document document = Jsoup.parse(selectable.toString());
                    String city_name = document.text();
                    String href = document.select("a").attr("href");
                    String city = href.replace("/hotel/", "");

                    //hotel/macau59
                    //获得上面url中的数字
                    String city_id = StringUtils.getDigits(href);
                    String pinyin = city.replace(city_id, "");
                    String url = new StringBuilder().append("https://hotels.ctrip.com").append(href).toString();

                    area.setCity_id(Integer.parseInt(city_id));
                    area.setCity_name(city_name);
                    area.setHead_pinyin(headPinyin);
                    area.setPinyin(pinyin);
                    area.setUrl(url);

                    areaList.add(area);

                    System.out.println(area.toString());
                }

            }
            page.putField("areaList", areaList);
        }

        System.out.println(12);


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

    @Autowired
    private MybatisPipeline mybatisPipeline;

//    @Scheduled(initialDelay = 1000, fixedDelay = 1000 * 60 * 10)
    public void crawlArea() {
        System.setProperty("selenuim_config", "C:\\Users\\Administrator\\IdeaProjects\\javacrawler\\src\\main\\resources\\config.ini");
        Spider.create(new CrawlArea())
                .addUrl("https://hotels.ctrip.com/domestic-city-hotel.html")
//                .setDownloader(new SeleniumDownloader("C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe").setSleepTime(1000))
                .thread(3)
//                .runAsync();
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
////                .thread(10)
                .addPipeline(this.mybatisPipeline)
                .run();
    }
}
