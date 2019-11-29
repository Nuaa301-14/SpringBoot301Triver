package com.example.javacrawler.controller;

import com.example.javacrawler.entity.Area;
import com.example.javacrawler.entity.ElongArea;
import com.example.javacrawler.service.*;
import com.example.javacrawler.task.*;
import com.example.javacrawler.task.pipeline.*;
import com.example.javacrawler.util.JsonCope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/crawl")
public class CrawlController {

    @Autowired
    private ElongAreaService elongAreaService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private SpotService spotService;

    @Autowired
    private ScenicHotelService scenicHotelService;

    @Autowired
    private GroupTravelService groupTravelService;

    @RequestMapping(value = "/elongArea")
    public String just() throws IOException {
//        new CrawlElongArea().crawl();
        if (elongAreaService.getTotal() == 0) {
            // 处理对应的json数据
            List<ElongArea> data = new JsonCope().getData();
            for (int i = 0; i < data.size(); i++) {
                ElongArea elongArea = data.get(i);
                elongAreaService.insertArea(elongArea);
            }
        }
        System.out.println(elongAreaService.getTotal());
        System.out.println("success");
        return "index";
    }

    @RequestMapping(value = "/hotelCrawl", method = RequestMethod.POST)
    public String crawlelonghotel(Model model,
                                  @RequestParam("input") String input,
                                  @RequestParam("pageNum") int pageNum,
                                  @RequestParam("source") String source) {
        // 分析需要爬取那个地方的支援
        //TODO
        if (source.equals("携程")) {
            Area a = areaService.getByCity_nameOrPinyin(input);
            if (a != null) {
                CrawlHotelXC crawlHotelXC = new CrawlHotelXC(a.getUrl());
                crawlHotelXC.crawl(new CrawlHotelXCPipeline(hotelService), pageNum);
            } else {
                model.addAttribute("inputUnreasonable", "输入的地区不合理(请输入准确的中文或者拼音！)");
            }
            return "craw";
        } else if (source.equals("艺龙")) {
            /**
             * 1.分析处理input信息，判断是否合理
             * 2.当前只能输入中文城市名称或者拼音
             */
            ElongArea elongArea = elongAreaService.getByCity_nameOrPinyin(input);
            /**
             * 3.进行url的组合
             * 4.调用接口进行爬取
             */
            // 说明input合理
            if (elongArea != null) {
                // 进行url拼接
                String url = "http://hotel.elong.com/search/list_cn_" + String.valueOf(elongArea.getCityId()) + ".html";
                boolean crawl = new CrawlHotelELong().crawl(url, pageNum);
                return "craw";
            } else {
                // 返回信息不合理
                model.addAttribute("inputUnreasonable", "输入的地区不合理(请输入准确的中文或者拼音！)");
                return "craw";
            }
        }
        return "craw";
    }


    /**
     * 管理员进行 景点的爬取
     * @param model
     * @param input
     * @param pageNum
     * @param source
     * @return
     */
    @RequestMapping(value = "/crawlSpot", method = RequestMethod.POST)
    public String crawSpot(Model model,
                           @RequestParam("input") String input,
                           @RequestParam("pageNum") int pageNum,
                           @RequestParam("source") String source) {
        if (source.equals("携程")) {
            Area a = areaService.getByCity_nameOrPinyin(input);
            String url = "";
            if (a != null) {
                url = "https://piao.ctrip.com/dest/" + "u-" + a.getPinyin() + "-" + a.getCity_id() + "/s-tickets/";
            } else {
                url = "https://piao.ctrip.com/dest/" + "u-" + input + "/s-tickets/" + "#ctm_ref=vat_hp_sb_lst";
            }
            new CrawlSpotXC().craw(url, pageNum, new CrawlSpotXCPipeline(spotService));
            return "craw";
        } else if (source.equals("艺龙")) {
            /**
             * 1.分析处理input信息，判断是否合理
             * 2.当前只能输入中文城市名称或者拼音
             */


            /**
             * 3.进行url的组合
             * 4.调用接口进行爬取
             */
            // 说明input合理

        }else if (source.equals("同程")){
            String url="";
            url="https://so.ly.com/scenery?q="+input;
            new CrawlSpotTC().craw(url,pageNum,new CrawlSpotTCPipeline(spotService));
            return "craw";
        }
        return "craw";
    }

    /**
     * 进行酒店和景区的组合爬取
     *
     * @param model
     * @param input
     * input 可以是地区
     * 也可以是主题等。。。
     * @param pageNum
     * @param source
     * @return
     */
    @RequestMapping(value = "/crawlSpotAndHotel",method = RequestMethod.POST)
    public String crawlSpotAndHotel(Model model,
                                    @RequestParam("input") String input,
                                    @RequestParam("pageNum") int pageNum,
                                    @RequestParam("source") String source){
        if (source.equals("携程")) {
            String url="https://vacations.ctrip.com/list/scenichotel/";
            Area area = areaService.getByCity_nameOrPinyin(input);
            if (area!=null){
                // 不为空 说明输入的是地区
                url= new StringBuilder().append(url).append("d-").append(area.getPinyin()).append(area.getCity_id()).append(".html").append("?st=").append(area.getCity_name()).append("&sv=").append(area.getCity_name()).toString();

            }else{
                // 为空说明输入的是其他 ，比如 主题等 之后在爬取时
                // 需要验证是否有内容，
                // 没有就直接结束
                url= new StringBuilder().append(url).append("sc1.html?st=").append(input).append("&sv=").append(input).toString();
            }
            // 进行爬取
            new CrawlScenicHotelXC().craw(url,pageNum,new CrawlScenicHotelXCPipeline(scenicHotelService));

        }else if(source.equals("同程")){
            String url="http://so.ly.com/zby-zizhu?q=";
            new CrawlScenicHotelTC().craw(url,pageNum,new CrawlScenicHotelTCPipeline(scenicHotelService));
        }
        return "craw";
    }

    @RequestMapping(value = "/crawlGroupTravel")
    public String CrawlGroupTravel(Model model,
                                   @RequestParam("input") String input,
                                   @RequestParam("pageNum") int pageNum,
                                   @RequestParam("source") String source){
        if (source.equals("携程")) {
            String url="https://vacations.ctrip.com/list/grouptravel/";
            Area area = areaService.getByCity_nameOrPinyin(input);
            if (area!=null){
                // 不为空 说明输入的是地区
                url= new StringBuilder().append(url).append("d-").append(area.getPinyin()).append(area.getCity_id()).append(".html").toString();

            }else{
                // 为空说明输入的是其他 ，比如 主题等 之后在爬取时
                // 需要验证是否有内容，
                // 没有就直接结束
                url= new StringBuilder().append(url).append("sc1.html?st=").append(input).append("&sv=").append(input).toString();
            }
            // 进行爬取
            new CrawlGroupTravelXC().craw(url,pageNum,new CrawlGroupTravelXCPipeline(groupTravelService));
        }else if(source.equals("同程")){
            String url="https://so.ly.com/zby-gentuan?q="+input+"&c=224";
            new CrawlGroupTravelTC().craw(url,pageNum,new CrawlGroupTravelTCPipeline(groupTravelService));
        }
        return "craw";
    }

}
