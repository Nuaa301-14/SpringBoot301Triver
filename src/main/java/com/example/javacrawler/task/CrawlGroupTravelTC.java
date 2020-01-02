package com.example.javacrawler.task;

import com.example.javacrawler.task.pipeline.CrawlGroupTravelTCPipeline;
import com.example.javacrawler.task.pipeline.CrawlGroupTravelXCPipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

public class CrawlGroupTravelTC implements PageProcessor {
    private int max;

    @Override
    public void process(Page page) {
        //TODO
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

    public void craw(String url, int pageNum, CrawlGroupTravelTCPipeline crawlGroupTravelTCPipeline) {
        max=pageNum;
        Spider.create(this).addUrl(url)
                .setDownloader(new SeleniumDownloader("C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe").setSleepTime(2000))
                .thread(1)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .addPipeline(crawlGroupTravelTCPipeline)
                .run();
    }
}
