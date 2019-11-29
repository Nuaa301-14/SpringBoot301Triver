package com.example.javacrawler.task;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

public class CrawlHotelELong implements PageProcessor {

    /**
     * 默认为两个
     */
    private int maxNumber=2;

    @Override
    public void process(Page page) {
        List<Selectable> selectableList = page.getHtml().css("div#hotel_list div.hotel_new_list").nodes();
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
            // 获取下一页的url
            String text = page.getHtml().css("div.c_page_list a.current", "text").toString();
            int current = Integer.parseInt(text);
            List<Selectable> nodes = page.getHtml().css("div.c_page_list a[data-dopost]").nodes();
            for (Selectable selectable :
                    nodes) {
                //获取url地址
                int num = Integer.parseInt(selectable.css("a[data-value]", "text").toString());
                if (num > current && num <= maxNumber) {
                    String s = selectable.links().toString();
                    page.addTargetRequest(selectable.links().toString());
                    System.out.println("添加 ： " + selectable.links().toString());
                }
            }
        }
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

    public boolean crawl(String url,int pageNum){
        maxNumber=pageNum;
        Spider.create(new CrawlHotelELong())
                .addUrl(url)
                .setDownloader(new SeleniumDownloader("C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe").setSleepTime(1000))
                .thread(1)
                .run();
        return true;
    }
}
