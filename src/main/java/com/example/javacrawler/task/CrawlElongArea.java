package com.example.javacrawler.task;


import com.example.javacrawler.task.pipeline.ELongAreaPipeline;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.List;

/**
 * 爬取 艺龙 的地区 信息（序号）
 */

@Component
public class CrawlElongArea implements PageProcessor {

    private String url="http://hotel.elong.com/ajax/search/stayincity/?_=1574234709256";

    private String URL_LIST="http://hotel.elong.com/ajax/tmapilist/asyncsearch";

    @Override
    public void process(Page page) {
//        int max=4;
//        int pageNumber=1;
//        if (page.getUrl().regex(URL_LIST).match() && pageNumber<max){
//            System.out.println(page.getUrl());
//            System.out.println(page.getHtml());
//        }else {
//            // 主页
////            System.out.println(page.getHtml());
//            pageNumber++;
//            System.out.println(page.getUrl());
//            Request request=new Request(URL_LIST);
//            request.setMethod(HttpConstant.Method.POST);
//
//            request.setRequestBody(HttpRequestBody.json(
//                    "{listRequest.aBTestNewVersion:k,listRequest.cityName:北京," +
//                            "listRequest.pageIndex:5,listRequest.pageSize:20}",
//                    "utf-8")
//            );
//
//
//            page.addTargetRequest(request);
//        }

        System.out.println(page.getRawText());
        String json=page.getRawText();
        List<String> list = new JsonPathSelector("$.hotCityList.cityId").selectList(json);
        for (int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
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

    public void crawl(){
        Spider.create(new CrawlElongArea()).addUrl(url)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .addPipeline(new ELongAreaPipeline())
                .run();
    }

}
