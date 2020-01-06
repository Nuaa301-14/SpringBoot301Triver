package com.example.javacrawler.task;

import com.example.javacrawler.entity.Spot;
import com.example.javacrawler.task.pipeline.CrawlHotelXCPipeline;
import com.example.javacrawler.task.pipeline.CrawlSpotXCPipeline;
import com.example.javacrawler.task.pipeline.ELongAreaPipeline;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CrawlSpotXC implements PageProcessor {

    private int max = 2;

    private String spotName=null;

    @Override
    public void process(Page page) {
        String url = page.getUrl().toString();
        String[] split = url.split("-");
        List<Spot> spotList=new ArrayList<>();
        List<Spot> spotList1=new ArrayList<>();
        List<Selectable> nodes = page.getHtml().css("div.spot-list div.view-spot").nodes();
        for (Selectable selectable :
                nodes) {
            Spot spot = new Spot();
            spot.setSpotArea(split[1]);
            Elements select = Jsoup.parse(selectable.toString()).select("div.spot-info h4");
            String SpotName = select.select("em").text().trim();   //景点名称

            String SpotLocation = select.select("span").get(0).text(); //景点地点
            String area=SpotLocation.substring(1,SpotLocation.length()-1).split("·")[0];
            spot.setSpotArea(area);
            Connection connect;
            connect = Jsoup.connect("https://you.ctrip.com/searchsite/?query=" + SpotName);
            try {
                Document document = connect.get();
//                System.out.println(document.select("div.result").size());
                Element element = document.select("div.result").get(0);
                Elements select1 = element.select("ul li");
//                System.out.println(select1.size());
                int i;
                for (i=0;i<select1.size();i++){
                    String href = select1.get(i).select("a.pic").attr("href");
                    Elements dl_dt_a = select1.get(i).select("dl dt a");
                    if (href.contains(split[1])){
                        spot.setSpotUrl("https://you.ctrip.com"+href);
                        String[] split1 = href.split("/");
                        String digits = StringUtils.getDigits(split1[split1.length - 1]);
                        spot.setSpotId("XC"+digits);
                        break;
                    }else {
                        if (spotName!=null){
                            if (dl_dt_a.get(0).text().equals(spotName.trim())&&dl_dt_a.get(1).text().equals(area)){
                                spot.setSpotUrl("https://you.ctrip.com"+href);
                                String[] split1 = href.split("/");
                                String digits = StringUtils.getDigits(split1[split1.length - 1]);
                                spot.setSpotId("XC"+digits);
                                break;
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            String SpotCountry = "中国";
            String[] s = SpotLocation.split(" ");
            String SpotGender = "1A";   //景点星级
            if (s.length > 1) {
                SpotLocation = s[0];
                SpotGender = "";
                for (int i = 1; i < s.length; i++) {
                    SpotGender += s[i];
                }
            }

            Elements select1 = Jsoup.parse(selectable.toString()).select("div.spot-comment span");
            String recommend = "0";
            float score = 0;
            if (select1.size() == 3) {
                String text = select1.get(2).text();
                score = Float.parseFloat(select1.get(2).text().substring(0, text.indexOf(" ")));
            } else if (select1.size() == 4) {
                String text = select1.get(2).text();
                score = Float.parseFloat(select1.get(2).text().substring(0, text.indexOf(" ")));
                recommend = select1.get(3).text();
                int indexOf = recommend.indexOf("%");
                recommend = recommend.substring(0, indexOf);
            }

            String introduce=Jsoup.parse(selectable.toString()).select("div.spot-info p.desc").text();


            Elements select2 = Jsoup.parse(selectable.toString()).select("div.price-box div.price-num");
            float price;
            if (select2.select("span").size()==0){
                price=-1;
            }else {
                String priceStr=select2.select("span").text();
                String[] s1 = priceStr.split(" ");
                price= Float.parseFloat((s1[1]));
            }

            int saleNum=0;
            Elements select3 = Jsoup.parse(selectable.toString()).select("div.price-box");
            if (select3.select("p.sale").size()!=0){
                String[] s1 = select3.select("p.sale").text().split(" ");
                String sale=s1[1];
                if (sale.contains("万")){
                    float num= Float.parseFloat(sale.substring(0,sale.indexOf("万")));
                    saleNum= (int) (num*10000);
                }else {
                    saleNum= Integer.parseInt(sale);
                }
            }

            spot.setSpotName(SpotName);
            spot.setSpotLocation(SpotLocation.substring(1,SpotLocation.length()-1));
            spot.setSpotCountry(SpotCountry);
            spot.setRecommend(recommend);
            spot.setSpotScore(score);
            spot.setIntroduce(introduce);
            spot.setSpotPrice(price);
            spot.setSoldNumber(saleNum);
            spot.setSource("携程");
            System.out.println(spot.toString());
            if (spot.getSpotUrl()==null)
                continue;
            else
                spotList.add(spot);
            if (spotName!=null){
                if (spot.getSpotName().equals(spotName.trim())){
                    spotList1.add(spot);
                    break;
                }
            }
        }
        if (spotName!=null&&spotList1.size()==1){
            page.putField("spotList",spotList1);
        }else {
            page.putField("spotList",spotList);
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
        max = pageNum;
        Spider.create(this).addUrl(url)
                .setDownloader(new SeleniumDownloader("C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe").setSleepTime(2000))
                .thread(1)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .addPipeline(crawlSpotXCPipeline)
                .run();
    }

    public void crawlSpeSpot(String url, String spotName, CrawlHotelXCPipeline crawlHotelXCPipeline){
        this.spotName=spotName;
        Spider.create(this).addUrl(url)
                .setDownloader(new SeleniumDownloader("C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe").setSleepTime(2000))
                .thread(1)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .addPipeline(crawlHotelXCPipeline)
                .run();
    }


    public static void main(String[] args) {
        Spider.create(new CrawlSpotXC()).addUrl("https://piao.ctrip.com/dest/dc-beijing-1/s-tickets/")
                .setDownloader(new SeleniumDownloader("C:\\Users\\Administrator\\Downloads\\chromedriver_win32\\chromedriver.exe").setSleepTime(2000))
                .thread(1)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
//                .addPipeline(crawlHotelXCPipeline)
                .run();
    }
}
