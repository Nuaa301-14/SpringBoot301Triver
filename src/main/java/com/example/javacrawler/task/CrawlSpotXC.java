package com.example.javacrawler.task;

import com.example.javacrawler.entity.Spot;
import com.example.javacrawler.task.pipeline.CrawlSpotXCPipeline;
import com.example.javacrawler.task.pipeline.ELongAreaPipeline;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.ByteArrayInputStream;
import java.util.List;


public class CrawlSpotXC implements PageProcessor {

    String URL_LIST = "http://sec-m.ctrip.com/restapi/soa2/10220/json/activitySearch?_fxpcqlniredt=09031154211452163820";

    private int max=2;

    @Override
    public void process(Page page) {
//        System.out.println(page.getRawText());
//        String ss="{\"pageid\":103061,\n" +
//                "\"searchtype\":2,\n" +
//                "\"keyword\":\"beijing\",\n" +
//                "\"needfact\":false,\n" +
//                "\"sort\":1,\n" +
//                "\"pidx\":1,\n" +
//                "\"psize\":20,\n" +
//                "\"reltype\":1,\n" +
//                "\"excepts\":[],\n" +
//                "\"filters\":[],\n" +
//                "\"isneedf\":true,\n" +
//                "\"isintion\":true,\n" +
//                "\"imagesize\":\"C_200_130\",\n" +
//                "\"assistfilter\":{\"userChooseSite\":\"100065\"},\n" +
//                "\"contentType\":\"json\",\n" +
//                "\"head\":{\"appid\":\"100013776\",\"cid\":\"09031037411652485539\",\"ctok\":\"\",\"cver\":\"1.0\",\"lang\":\"01\",\"sid\":\"8888\",\"syscode\":\"09\",\"auth\":\"\",\"extension\":[]},\n" +
//                "\"ver\":\"7.10.3.0319180000\"}";
//        System.out.println(page.getHtml());
//        Request request = new Request(URL_LIST);
//        request.setMethod(HttpConstant.Method.POST);
//        request.setRequestBody(HttpRequestBody.json(
//                ss.toString(),
//                "utf-8")
//        );
//        page.addTargetRequest(request);
        //TODO
        List<Selectable> nodes = page.getHtml().css("div.spot-list div.view-spot").nodes();
        for (Selectable selectable :
                nodes){
            Spot spot = new Spot();
            Elements select = Jsoup.parse(selectable.toString()).select("div.spot-info h4");
            select.select("em").text();
            select.select("span").text();
            System.out.println(111);
        }



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



    public void craw(String url, int pageNum, CrawlSpotXCPipeline crawlSpotXCPipeline) {
        max=pageNum;
        Spider.create(new CrawlSpotXC()).addUrl(url)
                .setDownloader(new SeleniumDownloader("C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe").setSleepTime(2000))
                .thread(1)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .addPipeline(crawlSpotXCPipeline)
                .run();
    }


    public static void main(String[] args) {
        System.setProperty("selenuim_config", "C:\\Users\\Administrator\\IdeaProjects\\javacrawler\\src\\main\\resources\\config.ini");
        Spider.create(new CrawlSpotXC()).addUrl("https://piao.ctrip.com/dest/dc-beijing-1/s-tickets/")
                .setDownloader(new SeleniumDownloader("C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe").setSleepTime(2000))
                .thread(1)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
//                .addPipeline(crawlHotelXCPipeline)
                .run();
    }
}
